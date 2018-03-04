package com.easymoney.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Prestamo;
import com.easymoney.modules.cambiarContra.CambiarContraActivity;
import com.easymoney.modules.detallePrestamo.DetallePrestamoActivity;
import com.easymoney.modules.ingresosEgresos.IngresosEgresosActivity;
import com.easymoney.modules.login.LoginActivity;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvInfo;
    private ListView listaPrestamos;
    private PrestamoAdapter adapterPrestamo;
    private PrestamoRepository prestamoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Prestamos");
        setSupportActionBar(toolbar);

        //drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView txtNombreUsuario = navigationView.getHeaderView(0).findViewById(R.id.nombreUsuario);
        TextView txtTipoUsuario = navigationView.getHeaderView(0).findViewById(R.id.tipoUsuario);

        txtNombreUsuario.setText(getIntent().getStringExtra("userName"));
        txtTipoUsuario.setText(getIntent().getStringExtra("userType"));

        tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setText("Cargando prestamos...");

        //crear la lista de prestamos
        listaPrestamos = findViewById(R.id.prestamoList);
        adapterPrestamo = new PrestamoAdapter(new ArrayList<>());
        listaPrestamos.setAdapter(adapterPrestamo);
        listaPrestamos.setOnItemClickListener((adapterView, view, i, l) -> {
            Prestamo p = (Prestamo) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(MainActivity.this, DetallePrestamoActivity.class);
            intent.putExtra("Prestamo", p);
            startActivity(intent);
        });
        prestamoRepository = PrestamoRepository.getINSTANCE();
        prestamoRepository.forceRemoteUpdate();
        prestamoRepository.findAll()
                .subscribeOn(SchedulerProvider.ioT())
                .observeOn(SchedulerProvider.uiT())
                .subscribe(
                        prestamos -> {
                            if (!prestamos.isEmpty()) {
                                tvInfo.setVisibility(View.GONE);
                                adapterPrestamo.replaceData(prestamos);
                            } else {
                                tvInfo.setText("Sin préstamos por cobrar");
                            }
                        },
                        ex -> ex.printStackTrace()
                );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.cobros:
                break;
            case R.id.IE:
                intent = new Intent(MainActivity.this, IngresosEgresosActivity.class);
                startActivity(intent);
                break;
            case R.id.cerrarSesion:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
                break;
            case R.id.cambiarContra:
                intent = new Intent(MainActivity.this, CambiarContraActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class PrestamoAdapter extends BaseAdapter {

        private List<Prestamo> prestamos;

        private TextView tvNombre;
        private TextView tvDireccion;
        private TextView tvTelefono;
        private TextView tvFechaPrestamo;

        public PrestamoAdapter(List<Prestamo> prestamos) {
            this.prestamos = prestamos;
        }

        public void replaceData(List<Prestamo> prestamos) {
            this.prestamos = prestamos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return prestamos.size();
        }

        @Override
        public Prestamo getItem(int i) {
            return prestamos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.adapter_prestamo, viewGroup, false);
            }

            final Prestamo prestamo = getItem(i);
            tvNombre = rowView.findViewById(R.id.tvNombre);
            tvDireccion = rowView.findViewById(R.id.tvDireccion);
            tvTelefono = rowView.findViewById(R.id.tvTelefono);
            tvFechaPrestamo = rowView.findViewById(R.id.tvFechaPrestamo);

            tvNombre.setText(prestamo.getCliente().getNombre());
            tvDireccion.setText(prestamo.getCliente().getDireccion());
            tvTelefono.setText(prestamo.getCliente().getTelefono());
            tvFechaPrestamo.setText(UtilsDate.format_D_MM_YYYY(prestamo.getFecha()));

            return rowView;
        }
    }
}
