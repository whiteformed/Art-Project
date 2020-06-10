package com.example.art_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityInfo extends AppCompatActivity {
    TextView tv_github;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tv_github = findViewById(R.id.activity_info_tv_github);

        View.OnClickListener onLinkClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/whiteformed/Debt-Manager"));
                startActivity(intent);
            }
        };

        tv_github.setOnClickListener(onLinkClickListener);
    }
}
