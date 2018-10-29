package com.easymoney.modules.renovacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.easymoney.R;
import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.utils.activities.ActivityUtils;

public class RenovacionActivity extends AppCompatActivity {

    RenovacionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovacion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RenovacionFragment renovacionFragment = (RenovacionFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (renovacionFragment == null) {
            renovacionFragment = new RenovacionFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), renovacionFragment, R.id.contentFrame);
        }

        PrestamoRepository prestamoRepository = PrestamoRepository.getInstance();
        presenter = new RenovacionPresenter(prestamoRepository, renovacionFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
