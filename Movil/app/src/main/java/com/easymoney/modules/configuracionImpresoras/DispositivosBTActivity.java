package com.easymoney.modules.configuracionImpresoras;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.modules.configuracionImpresoras.Adapters.ImpresorasAdapter;
import com.easymoney.utils.UtilsPreferences;

import java.io.IOException;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * Created by Jorge on 1/08/2018.
 */
public class DispositivosBTActivity extends AppCompatActivity {

    private static final int BLUETOOTH_ENABLED = 1;
    private DevicesListAdapter devicesAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private ProgressBar progressBar;
    private String macAddress;
    private Dialog dialog;
    private Button btnCancelarModelo;
    private Context context;
    private ListView listTipoImpresora;
    private ImpresorasAdapter adapter;
    private String modelo;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //mostrar termino de buscar
                //showMessage("Fin de busqueda");
                progressBar.setVisibility(View.GONE);
                showMessage("Búsqueda finalizada");
            } else {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesAdapter.addItem(device.getName(), device.getAddress(), true);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = DispositivosBTActivity.this;

        progressBar = findViewById(R.id.indeterminateBar);

        ListView listDevices = findViewById(R.id.listBluetooth);
        TextView emptySector = findViewById(R.id.emptyList);

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
            } else {
                bluetoothAdapter.startDiscovery();
                initializeReceiver();
            }
        } else {
            showMessage("Bluetooth no disponible...");
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_actualizar_dispositivos: {
                if (!bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.startDiscovery();
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
     * Metodo onClick para seleccionar el dispositivo bluetooth y guardar su MAC address.
     *
     * @param position posición de la lista del dispositivo seleccionado.
     */
    protected void onListViewClick(int position) {
        try {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_modelos_impresoras);
            dialog.setTitle("Seleccione modelo de impresora");
            dialog.setCancelable(true);
            DevicesListAdapter.BluetoothItem item = (DevicesListAdapter.BluetoothItem) devicesAdapter.getItem(position);
            macAddress = item.getMacAddress();
            UtilsPreferences.saveMacPrinter(macAddress);

            listTipoImpresora = (ListView) dialog.findViewById(R.id.listTipoImpresora);
            btnCancelarModelo = dialog.findViewById(R.id.btnCancelar);
            adapter = new ImpresorasAdapter(context);
            listTipoImpresora.setAdapter(adapter);
            listTipoImpresora.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
                    modelo = (String) adapter.getItem(position);
                    UtilsPreferences.savePrinterModel(modelo);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BTPrinterDevice btPrinterDevice = BTPrinterDevice.getInstance();
                            try {
                                btPrinterDevice.connectToClient(macAddress);
                                Thread.sleep(150);
                                btPrinterDevice.disconnectFromClient();
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    }).start();
                }
            });
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        btnCancelarModelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException error) {
        }
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BLUETOOTH_ENABLED && resultCode != 0) {
            bluetoothAdapter.startDiscovery();
            initializeReceiver();
        } else {
            finish();
        }
    }

    private void initializeReceiver() {
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_NAME_CHANGED));
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    }

    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, LENGTH_LONG).show();
    }
}
