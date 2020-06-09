package com.example.art_project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

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

    String totalAmount;
    TextView tv_total_amount;

    FloatingActionButton fab;

    @Override
    public void onAddPerson(Person newPerson) {
        boolean result = sqlDatabaseHelper.addPerson(newPerson);
        updateFragments();
    }

    @Override
    public void onItemClick(int personID) {
        Intent intent = new Intent(this, ActivityEntryList.class);
        intent.putExtra("id", personID);

        startActivity(intent);
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

        TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getTotalAmount(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

        getTotalAmount(0);
    }

    public void updateFragments() {
        fragmentMyDebt.updatePersonArrayList();
        fragmentTheirDebt.updatePersonArrayList();

        getTotalAmount(0);
    }

    public void getTotalAmount(int tabId) {
        int total = 0;

        ArrayList<Person> personArrayList = tabId == 0 ? fragmentMyDebt.personArrayList : fragmentTheirDebt.personArrayList;

        for (Person person : personArrayList) {
            total += person.getAmount();
        }

        totalAmount = total + " RUB";
        tv_total_amount.setText(totalAmount);
    }
}