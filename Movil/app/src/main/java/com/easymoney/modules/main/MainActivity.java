package com.easymoney.modules.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.EnumPrestamos;
import com.easymoney.models.services.Response;
import com.easymoney.modules.cambiarContra.CambiarContraActivity;
import com.easymoney.modules.configuracionImpresoras.DispositivosBTActivity;
import com.easymoney.modules.detallePrestamo.DetallePrestamoActivity;
import com.easymoney.modules.ingresosEgresos.IngresosEgresosActivity;
import com.easymoney.modules.login.LoginActivity;
import com.easymoney.modules.renovacion.RenovacionActivity;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvInfo;
    private ListView listaPrestamos;
    private PrestamoAdapter adapterPrestamo;
    private PrestamoRepository prestamoRepository;
    private List<Prestamo> prestamos;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

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

        //si no es administrador, remover del menu
        if (!UtilsPreferences.loadLogedUser().isTipo()){
            navigationView.getMenu().findItem(R.id.renovacion).setVisible(false);
        }

        txtNombreUsuario.setText(getIntent().getStringExtra("userName"));
        txtTipoUsuario.setText(getIntent().getStringExtra("userType"));

        tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setText("Cargando prestamos...");

        //crear la lista de prestamos
        listaPrestamos = findViewById(R.id.prestamoList);
        adapterPrestamo = new PrestamoAdapter(new ArrayList<Prestamo>());
        listaPrestamos.setAdapter(adapterPrestamo);
        listaPrestamos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prestamo p = (Prestamo) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, DetallePrestamoActivity.class);
                intent.putExtra("Prestamo", p);
                startActivity(intent);
            }
        });

        prestamoRepository = PrestamoRepository.getInstance();
        this.cargarPrestamos(EnumPrestamos.POR_COBRAR_HOY);
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.prestamos != null) {
            if (!this.prestamos.isEmpty()) {
                setPrestamos(filtrarPorCobrarHoy(this.prestamos));
                adapterPrestamo.replaceData(this.prestamos);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            case R.id.renovacion:
                intent = new Intent(MainActivity.this, RenovacionActivity.class);
                startActivity(intent);
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
            case R.id.configuracionImpresora:
                intent = new Intent(MainActivity.this, DispositivosBTActivity.class);
                startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cargarPrestamos(final EnumPrestamos enumPrestamos) {
        compositeDisposable.add(
                prestamoRepository.findAll(enumPrestamos)
                        .subscribeOn(SchedulerProvider.ioT())
                        .observeOn(SchedulerProvider.uiT())
                        .subscribe(new Consumer<Response<List<Prestamo>, Object>>() {
                                       @Override
                                       public void accept(Response<List<Prestamo>, Object> r) throws Exception {
                                           if (!r.getData().isEmpty()) {
                                               tvInfo.setVisibility(View.GONE);
                                               if (enumPrestamos == EnumPrestamos.POR_COBRAR_HOY) {
                                                   setPrestamos(filtrarPorCobrarHoy(r.getData()));
                                               } else {
                                                   setPrestamos(r.getData());
                                               }

                                               adapterPrestamo.replaceData(getPrestamos());
                                           } else {
                                               tvInfo.setText("Sin préstamos por cobrar");
                                           }
                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           throwable.printStackTrace();
                                       }
                                   }
                        )
        );
    }

    /**
     * Filtra la lista de prestamos y reemplaza los datos del adaptador segun el nombre de empleado buscado
     *
     * @param nombreCliente nombre del cliente buscado
     */
    private void filtrarPrestamos(String nombreCliente) {
        if (this.prestamos != null) {
            List<Prestamo> prestamosBuscados = new ArrayList<>();
            for (Prestamo prestamo : this.prestamos) {
                if (prestamo.getCliente().getNombre().toLowerCase().contains(nombreCliente.toLowerCase())
                        || prestamo.getCliente().getApodo().toLowerCase().contains(nombreCliente.toLowerCase())) {
                    prestamosBuscados.add(prestamo);
                }
            }
            adapterPrestamo.replaceData(prestamosBuscados);
        }
    }

    /**
     * filtra los prestamos que ya fueron cobrados, dejando como resultado los prestamos por cobrar el dia de hoy
     *
     * @param prestamos prestamos cargados
     * @return lista de prestamos por cobrar hoy
     */
    private List<Prestamo> filtrarPorCobrarHoy(List<Prestamo> prestamos) {
        List<Prestamo> prestamosAMostrar = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            if (!UtilsPreferences.prestamoCobradoHoy(prestamo.getId())) {
                prestamosAMostrar.add(prestamo);
            }
        }
        return prestamosAMostrar;
    }

    /**
     * resetea los prestamos mostrados con todos los existentes
     */
    private void desfiltrarPrestamos() {
        if (this.prestamos != null) adapterPrestamo.replaceData(this.prestamos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_options, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                MainActivity.this.desfiltrarPrestamos();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                MainActivity.this.filtrarPrestamos(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                lanzarModalFiltro();
                break;
        }
        return true;
    }

    private void lanzarModalFiltro() {
        MainFiltroDialogFragment dialog = new MainFiltroDialogFragment(this);
        dialog.show(getFragmentManager(), "Filtro");
    }

    private static class PrestamoAdapter extends BaseAdapter {

        private List<Prestamo> prestamos;

        private TextView tvNombre;
        private TextView tvApodo;
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
            tvApodo = rowView.findViewById(R.id.tvApodo);

            View barrita = rowView.findViewById(R.id.barrita);
            if (UtilsPreferences.prestamoCobradoHoy(prestamo.getId())) {
                barrita.setBackgroundResource(R.color.positive);
            } else {
                barrita.setBackgroundResource(R.color.warning);
            }

            tvNombre.setText(prestamo.getCliente().getNombre());
            tvDireccion.setText(prestamo.getCliente().getDireccion());
            tvTelefono.setText(prestamo.getCliente().getTelefono());
            tvFechaPrestamo.setText(UtilsDate.format_D_MM_YYYY(prestamo.getFecha()));
            tvApodo.setText(prestamo.getCliente().getApodo());

            return rowView;
        }
    }
}
