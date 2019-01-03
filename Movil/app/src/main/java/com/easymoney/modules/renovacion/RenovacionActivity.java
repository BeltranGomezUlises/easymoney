package com.easymoney.modules.renovacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.easymoney.R;
import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.utils.activities.ActivityUtils;

import static com.easymoney.modules.renovacion.RenovacionPresenter.RESULT_RENOVACION;

public class RenovacionActivity extends AppCompatActivity {

    RenovacionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovacion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RenovacionFragment fragment = (RenovacionFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = new RenovacionFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        PrestamoRepository prestamoRepository = PrestamoRepository.getInstance();
        presenter = new RenovacionPresenter(prestamoRepository, this);
        ActivityUtils.contract(fragment, presenter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_RENOVACION){
            this.finish();
        }
    }
}
