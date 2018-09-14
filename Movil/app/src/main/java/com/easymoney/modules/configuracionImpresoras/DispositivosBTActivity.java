package com.easymoney.modules.configuracionImpresoras;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.Set;

import io.reactivex.Flowable;
import retrofit2.Response;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by Jorge on 1/08/2018.
 */
public class DispositivosBTActivity extends AppCompatActivity {

    private static final int BLUETOOTH_ENABLED = 1;
    private DevicesListAdapter devicesAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private ListView listDevices;
    private MenuItem refreshMenuItem;
    private TextView emptySector;
    private Context context;
    private DevicesListAdapter.BluetoothItem item;
    private BluetoothDevice device;
    private String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = DispositivosBTActivity.this;
        listDevices = (ListView)findViewById(R.id.listBluetooth);

        emptySector = (TextView) findViewById(R.id.emptyList);
        listDevices.setEmptyView(emptySector);

        devicesAdapter = new DevicesListAdapter();
        listDevices.setAdapter(devicesAdapter);
        listDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListViewClick(position);
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, BLUETOOTH_ENABLED);
                enableBtIntent = null;
            } else {
                bluetoothAdapter.startDiscovery();
                initializeReceiver();
            }
        } else {
            showMessage("Bluetooth no disponible...");
            finish();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_impresora, menu);
        refreshMenuItem = menu.findItem(R.id.menu_actualizar_dispositivos);
        setRefreshActionButtonState(true);
        inflater = null;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.menu_actualizar_dispositivos:{
                if (!bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.startDiscovery();
                    setRefreshActionButtonState(true);
                }
                break;
            }
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    /**
     * Metodo para refrescar la lista de dispositivos disponibles.
     * @param refreshing
     */
    public void setRefreshActionButtonState(final boolean refreshing) {
        if (refreshMenuItem != null) {
            if (refreshing) {
                // set the progress bar view
                //MenuItemCompat.setActionView(refreshMenuItem, R.layout.actionbar_progress);
                MenuItemCompat.expandActionView(refreshMenuItem);
            } else {
                MenuItemCompat.setActionView(refreshMenuItem, null);
                MenuItemCompat.collapseActionView(refreshMenuItem);
            }
        }
    }

    /**
     * Metodo onClick para seleccionar el dispositivo bluetooth y guardar su MAC address.
     * @param position posición de la lista del dispositivo seleccionado.
     */
    protected void onListViewClick(int position) {
        try {
            item = (DevicesListAdapter.BluetoothItem) devicesAdapter.getItem(position);
            macAddress = item.macAddress;
            UtilsPreferences.saveMacPrinter(macAddress);
            pairPrintDevice(context)
                    .subscribeOn(SchedulerProvider.ioT())
                    .observeOn(SchedulerProvider.uiT())
                    .subscribe();
            finish();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException error) {}
        if (bluetoothAdapter != null) bluetoothAdapter.cancelDiscovery();

        devicesAdapter = null;
        bluetoothAdapter = null;
        listDevices = null;
        refreshMenuItem = null;
        emptySector = null;
        context = null;
        item = null;
        device = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BLUETOOTH_ENABLED && resultCode != 0) {
            bluetoothAdapter.startDiscovery();
            initializeReceiver();
        } else
            finish();
    }

    private void initializeReceiver() {
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_NAME_CHANGED));
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));

        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device: devices) {
            devicesAdapter.addItem(device.getName(), device.getAddress(), false);
        }
        devices = null;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                setRefreshActionButtonState(true);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setRefreshActionButtonState(false);
            } else {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesAdapter.addItem(device.getName(), device.getAddress(), true);
            }
            action = null;
        }
    };

    /**
     * Metodo para emparejar el dispositivo Bluetooth seleccionado.
     * @param context contexto necesario.
     * @throws Exception
     */
    private Flowable<Response> pairPrintDevice(final Context context) throws Exception {
        BTPrinterDevice.getInstance().connectToClient(macAddress);
        Thread.sleep(1000);
        BTPrinterDevice.getInstance().disconnectFromClient();
        return null;
    }

    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, LENGTH_LONG).show();
    }
}
