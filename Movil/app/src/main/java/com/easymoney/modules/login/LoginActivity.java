package com.easymoney.modules.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.easymoney.R;
import com.easymoney.data.repositories.LoginRepository;
import com.easymoney.utils.activities.ActivityUtils;
import com.easymoney.utils.UtilsPreferences;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //guardar el contexto
        UtilsPreferences.setContext(this.getApplicationContext());

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        LoginRepository loginRepository = new LoginRepository();
        LoginPresenter loginPresenter = new LoginPresenter(loginFragment, loginRepository);
    }

}

