package com.easymoney.modules.detallePrestamo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.easymoney.R;
import com.easymoney.entities.Prestamo;

public class DetallePrestamoActivity extends AppCompatActivity {

    private DetallePrestamoPresenter presenter;
    private AbonoFragment abonoFragment;
    private ConsultaFragment consultaFragment;
    private CobroFragment cobroFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_prestamo);
        //asegurar primero el presentador para poder enlazar los fragmentos
        Prestamo prestamo = (Prestamo) getIntent().getSerializableExtra("Prestamo");
        boolean enRenovacion = getIntent().getBooleanExtra("renovacion", false);

        presenter = new DetallePrestamoPresenter(prestamo);
        consultaFragment = new ConsultaFragment(enRenovacion);
        abonoFragment = new AbonoFragment();
        cobroFragment = new CobroFragment();

        FragmentController fragmentController = new FragmentController(this, abonoFragment, consultaFragment, cobroFragment);
        presenter.setFragment(fragmentController);
        fragmentController.setPresenter(presenter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager); //bind with view pager
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.unsubscribe();
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return consultaFragment;
                case 1:
                    return abonoFragment;
                case 2:
                    return cobroFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Detalle";
                case 1:
                    return "Abonos";
                case 2:
                    return "Cobros";
                default:
                    return "";
            }
        }
    }
}
