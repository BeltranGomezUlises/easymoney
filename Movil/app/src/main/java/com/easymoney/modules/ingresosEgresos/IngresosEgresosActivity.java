package com.easymoney.modules.ingresosEgresos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.easymoney.R;
import com.easymoney.data.repositories.MovimientoRepository;
import com.easymoney.utils.activities.ActivityUtils;

public class IngresosEgresosActivity extends AppCompatActivity {

    private IngresosEgresosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos_egresos);
        setTitle("Ingresos y Egresos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MovimientoRepository movimientoRepository = new MovimientoRepository();
        presenter = new IngresosEgresosPresenter(movimientoRepository);
        IngresosEgresosFragment ingresosEgresosFragment = (IngresosEgresosFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (ingresosEgresosFragment == null) {
            ingresosEgresosFragment = IngresosEgresosFragment.getInstance(presenter);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ingresosEgresosFragment, R.id.contentFrame);
        }
        presenter.setView(ingresosEgresosFragment);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarModalMovimiento();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_filter:
                lanzarModalFiltro();
                break;
        }
        return true;
    }

    private void lanzarModalFiltro(){
        IngresoEgresoFiltroDialogFragment dialog = new IngresoEgresoFiltroDialogFragment(presenter, IngresosEgresosActivity.this.getApplicationContext());
        dialog.show(getFragmentManager(), "filtro");
    }

    private void lanzarModalMovimiento() {
        IngresoEgresoDialogFragment dialog = new IngresoEgresoDialogFragment(presenter);
        dialog.show(getFragmentManager(), "Movimiento");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ingresos_egresos, menu);
        return true;
    }
}
