package com.example.debt_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityLaunch extends AppCompatActivity {
    private final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PrefsHelper.setLocale(this);
        PrefsHelper.setTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Runnable runnable = () -> {
            Intent activityLogin = new Intent(context, ActivityPersons.class);
            startActivity(activityLogin);
            finish();
        };

        int delay = 600;
        new Handler().postDelayed(runnable, delay);
    }
}
