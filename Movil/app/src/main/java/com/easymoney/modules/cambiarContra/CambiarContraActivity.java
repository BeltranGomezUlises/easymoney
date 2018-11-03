package com.easymoney.modules.cambiarContra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.easymoney.R;
import com.easymoney.data.repositories.UsuarioRepository;
import com.easymoney.utils.activities.ActivityUtils;

public class CambiarContraActivity extends AppCompatActivity {

    CambiarContraPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contra);

        setTitle("Cambiar Contraseña");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CambiarContraFragment cambiarContraFragment = (CambiarContraFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (cambiarContraFragment == null) {
            cambiarContraFragment = new CambiarContraFragment ();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), cambiarContraFragment, R.id.contentFrame);
        }

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        presenter= new CambiarContraPresenter(cambiarContraFragment, usuarioRepository);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
