package com.example.art_project;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class ActivityMain extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    FloatingActionButton floatingActionButton;
    AddButtonClickListener fragmentMyDebtListener;
    AddButtonClickListener fragmentTheirDebtListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup View Pager
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add fragments
        viewPagerAdapter.addFragment(new FragmentMyDebt(), "My Debt");
        viewPagerAdapter.addFragment(new FragmentTheirDebt(), "Their Debt");

        //Setup the adapter
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Creating a FAB
        floatingActionButton = findViewById(R.id.fab);

        //onClick event handler for FAB
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        };

        floatingActionButton.setOnClickListener(onClickListener);
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_insert_person);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv_message = dialog.findViewById(R.id.message_tv);
        tv_message.setText(R.string.tv_message_add);

        final EditText et_name = dialog.findViewById(R.id.et_name);
        final TextView tv_i_owe = dialog.findViewById(R.id.tv_i_owe);
        final TextView tv_owe_me = dialog.findViewById(R.id.tv_owe_me);
        final Switch sw = dialog.findViewById(R.id.sw);

        Button btn_add = dialog.findViewById(R.id.button_add);
        btn_add.setText(R.string.btn_text_add);

        View.OnClickListener onButtonAddClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status;

                if (sw.isChecked())
                    status = "1";
                else {
                    status = "0";
                }
                Person newPerson = new Person(status, et_name.getText().toString(), "0");

                if (newPerson.getName().trim().equals("")) {
                    makeToast("No empty fields allowed!");
                } else {
                    if (status.equals("0"))
                        fragmentMyDebtListener.onAddButtonClicked(newPerson);

                    if (status.equals("1"))
                        fragmentTheirDebtListener.onAddButtonClicked(newPerson);

                    dialog.cancel();
                }
            }
        };

        final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(isChecked);

                if (isChecked) {
                    tv_i_owe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkText));
                    tv_owe_me.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightText));
                } else {
                    tv_i_owe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightText));
                    tv_owe_me.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkText));
                }
            }
        };

        View.OnClickListener onIOweClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedChangeListener.onCheckedChanged(sw, false);
            }
        };

        View.OnClickListener onOweMeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedChangeListener.onCheckedChanged(sw, true);
            }
        };

        sw.setOnCheckedChangeListener(onCheckedChangeListener);

        tv_i_owe.setOnClickListener(onIOweClickListener);
        tv_owe_me.setOnClickListener(onOweMeClickListener);

        btn_add.setOnClickListener(onButtonAddClickListener);

        dialog.show();
    }

    public void setListener(AddButtonClickListener addButtonClickListener) {
        if (addButtonClickListener instanceof FragmentMyDebt)
            this.fragmentMyDebtListener = addButtonClickListener;

        if (addButtonClickListener instanceof FragmentTheirDebt)
            this.fragmentTheirDebtListener = addButtonClickListener;
    }
}