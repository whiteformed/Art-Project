package com.example.art_project;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class ActivityMain extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    FloatingActionButton floatingActionButton;
    DialogHelper dialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogHelper = new DialogHelper(this);

        //Setup View Pager
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add fragments
        viewPagerAdapter.addFragment(new FragmentMyDebt(), getString(R.string.ttl_my_debt));
        viewPagerAdapter.addFragment(new FragmentTheirDebt(), getString(R.string.ttl_their_debt));

        //Setup the adapter
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Creating a FAB
        floatingActionButton = findViewById(R.id.fab);

        //onClick event handler for FAB
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean defaultSwitchState = tabLayout.getSelectedTabPosition() != 0;
                dialogHelper.createAddDialog((defaultSwitchState));
            }
        };

        floatingActionButton.setOnClickListener(onClickListener);
    }

    public DialogHelper getDialogHelper() {
        return dialogHelper;
    }
}