package com.easymoney.modules.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.easymoney.R;
import com.easymoney.data.repositories.LoginRepository;
import com.easymoney.utils.activities.ActivityUtils;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null){
            loginFragment = new LoginFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        LoginRepository loginRepository = new LoginRepository();
        LoginPresenter loginPresenter = new LoginPresenter(loginFragment, loginRepository);

    }

}

