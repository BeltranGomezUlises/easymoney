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
import com.easymoney.modules.login.LoginActivity;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listaPrestamos;
    private PrestamoAdapter adapterPrestamo;
    private PrestamoRepository prestamoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        //crear la lista de prestamos
        listaPrestamos = findViewById(R.id.prestamoList);
        adapterPrestamo = new PrestamoAdapter(new ArrayList<>());
        listaPrestamos.setAdapter(adapterPrestamo);

        prestamoRepository = PrestamoRepository.getINSTANCE();
        prestamoRepository.findAll()
                .subscribeOn(SchedulerProvider.ioT())
                .observeOn(SchedulerProvider.uiT())
                .subscribe(
                        prestamos -> adapterPrestamo.replaceData(prestamos),
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
        if (id == R.id.cobros) {

        } else if (id == R.id.cerrarSesion) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
