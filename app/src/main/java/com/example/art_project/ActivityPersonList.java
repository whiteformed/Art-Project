package com.example.art_project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityPersonList extends AppCompatActivity implements OnPersonArrayListUpdateListener, OnPersonItemClickListener {
    SqlDatabaseHelper sqlDatabaseHelper;

    DialogHelper dialogHelper;

    FragmentMyDebt fragmentMyDebt;
    FragmentTheirDebt fragmentTheirDebt;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    TextView tv_total_amount;

    FloatingActionButton fab;

    @Override
    public void onAddPerson(Person newPerson) {
        boolean result = sqlDatabaseHelper.addPerson(newPerson);
        updateFragments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateFragments();
    }

    @Override
    public void onItemClick(int personID) {
        Intent intent = new Intent(this, ActivityEntryList.class);
        intent.putExtra("id", personID);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        sqlDatabaseHelper = new SqlDatabaseHelper(this);

        dialogHelper = new DialogHelper(this);
        dialogHelper.setOnPersonArrayListUpdateListener(this);

        tabLayout = findViewById(R.id.activity_person_list_tl);
        viewPager = findViewById(R.id.activity_person_list_vp);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add fragments
        fragmentMyDebt = new FragmentMyDebt();
        fragmentTheirDebt = new FragmentTheirDebt();

        fragmentMyDebt.status = 0;
        fragmentMyDebt.onPersonItemClickListener = this;

        fragmentTheirDebt.status = 1;
        fragmentTheirDebt.onPersonItemClickListener = this;

        viewPagerAdapter.addFragment(fragmentMyDebt, getString(R.string.ttl_my_debt));
        viewPagerAdapter.addFragment(fragmentTheirDebt, getString(R.string.ttl_their_debt));

        //Setup the adapter
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tv_total_amount = findViewById(R.id.activity_person_list_tv_total_amount);

        TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                super.onTabSelected(tab);

                setTotalAmount();
            }
        };

        tabLayout.addOnTabSelectedListener(onTabSelectedListener);

        //Creating a FAB
        fab = findViewById(R.id.activity_person_list_fab);

        //onClick event handler for FAB
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean defaultSwitchState = tabLayout.getSelectedTabPosition() != 0;
                dialogHelper.createAddPersonDialog((defaultSwitchState));
            }
        };

        fab.setOnClickListener(onClickListener);

        setTotalAmount();
    }

    public void updateFragments() {
        fragmentMyDebt.updatePersonArrayList();
        fragmentTheirDebt.updatePersonArrayList();

        setTotalAmount();
    }

    public String getTotalAmount() {
        int status = tabLayout.getSelectedTabPosition();
        int total = sqlDatabaseHelper.getPersonArrayListTotalAmount(status);

        return total + getResources().getString(R.string.value);
    }

    public void setTotalAmount() {
        String totalAmount = getTotalAmount();
        tv_total_amount.setText(totalAmount);
    }
}