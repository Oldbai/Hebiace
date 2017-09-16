package com.oldbai.android.app.hebiace;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.oldbai.android.app.hebiace.data.source.UserPersistenceContract;
import com.oldbai.android.app.hebiace.data.source.UserRepository;
import com.oldbai.android.app.hebiace.login.LoginActivity;
import com.oldbai.android.app.hebiace.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private boolean isFirstLogin = true;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.splash_activity);

        isFirstLogin = UserRepository.getInstance(getApplicationContext()).getSettingIsFirstLogin();

        if (isFirstLogin){
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
