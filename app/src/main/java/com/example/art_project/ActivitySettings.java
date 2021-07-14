package com.example.art_project;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ActivitySettings extends AppCompatActivity {
    ArrayList<Fragment> fragmentArrayList;

    public void setFragment(int fragmentID) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_settings_fragment_frame, fragmentArrayList.get(fragmentID))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PrefsHelper.setTheme(this);
        PrefsHelper.setLocale(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(new FragmentSettingList());
        fragmentArrayList.add(new FragmentLanguageList());
        fragmentArrayList.add(new FragmentThemeList());
        fragmentArrayList.add(new FragmentInformation());

        setFragment(0);
    }
}
