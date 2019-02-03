package com.easymoney.modules.login;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.easymoney.R;
import com.easymoney.data.repositories.LoginRepository;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.activities.ActivityUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 1;
    LoginPresenter presenter;
    Context context;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();

    }

    @Override
    protected void onResume() {
        super.onResume();
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            solicitarPermisoEscritura();
        } else {
            escribirImagen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    escribirImagen();

                } else {
                    //Permiso negado, volver a preguntar.
                    ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST);
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    /**
     * Metodo para validar y solicitar el permiso de escritura en el celular.
     */
    private void solicitarPermisoEscritura() {
        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted

                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST);
                }
            } else {
                // Permission has already been granted
                escribirImagen();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metod para escribir en la memoria del telefono la imagen a utilizar de la impresora bixolon
     */
    private void escribirImagen() {
        try {
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            String imageUri = Environment.getExternalStorageDirectory() + "/" + "easy.jpg";
            File f = new File(imageUri);
            if (!f.exists()) {
                //Si no existe el archivo, escribir imagen:
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.easy);
                File file = new File(extStorageDirectory, "easy.jpg");
                FileOutputStream outStream = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

