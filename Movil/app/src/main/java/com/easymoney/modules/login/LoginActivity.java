package com.easymoney.modules.login;

import android.Manifest;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.easymoney.R;
import com.easymoney.data.repositories.LoginRepository;
import com.easymoney.utils.activities.ActivityUtils;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.activities.Funcion;
import com.easymoney.utils.bluetoothPrinterUtilities.UtilsPrinter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    LoginPresenter presenter;
    Context context;
    private static final int MY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        //guardar el contexto
        UtilsPreferences.setContext(this.getApplicationContext());

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        LoginRepository loginRepository = new LoginRepository();
        presenter = new LoginPresenter(loginFragment, loginRepository);

        try{
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
                testImpresion();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void testImpresion(){
        try{
            Bitmap bm = BitmapFactory.decodeResource( getResources(), R.drawable.easy);
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File file = new File(extStorageDirectory, "easy.jpg");
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

            String macAddress = UtilsPreferences.loadMacPrinter();
            if (macAddress == null || macAddress.isEmpty()) {
                Log.d("debug","sin impresora guardada");
            }else{
                UtilsPrinter.imprimirRecibo(
                        null,
                        macAddress,
                        LoginActivity.this,
                        new Funcion<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                Log.d("debug","error imprimir");
                            }
                        });
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    testImpresion();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}

