package com.oldbai.android.app.hebiace.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oldbai.android.app.hebiace.data.source.UserRepository;
import com.oldbai.android.app.hebiace.util.ActivityUtils;
import com.oldbai.android.app.hebiace.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //设置fragment
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (loginFragment == null) {
            //创建新fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.fragment_container);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
