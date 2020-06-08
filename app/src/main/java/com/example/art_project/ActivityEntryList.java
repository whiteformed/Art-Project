package com.example.art_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityEntryList extends AppCompatActivity {
    TextView tv_name;
    TextView tv_amount;
    TextView tv_status;

    ImageView iv_del;
    ImageView iv_dec;
    ImageView iv_inc;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    EntryListAdapter entryListAdapter;
    ArrayList<Entry> entryArrayList;

    SqlDatabaseHelper sqlDatabaseHelper;

    DialogHelper dialogHelper;

    Person person;

    void updateEntryArrayList() {
        entryArrayList.clear();
        entryArrayList.addAll(sqlDatabaseHelper.getEntryArrayList(person.getID()));
        entryListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        dialogHelper = new DialogHelper(this);
        //dialogHelper.setListener(this);

        sqlDatabaseHelper = new SqlDatabaseHelper(this);
        person = sqlDatabaseHelper.getPerson(getIntent().getIntExtra("id", 0));

        if (person.getID() != 0) {
            recyclerView = findViewById(R.id.activity_entry_list_rv);

            tv_name = findViewById(R.id.activity_entry_list_tv_name);
            tv_amount = findViewById(R.id.activity_entry_list_tv_amount);
            tv_status = findViewById(R.id.activity_entry_list_tv_status);

            iv_del = findViewById(R.id.activity_entry_list_iv_del);
            iv_dec = findViewById(R.id.activity_entry_list_iv_dec);
            iv_inc = findViewById(R.id.activity_entry_list_iv_inc);

            View.OnClickListener onButtonDelClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toaster.makeToast(v.getContext(), "del");
                }
            };

            View.OnClickListener onButtonDecClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toaster.makeToast(v.getContext(), "dec");
                }
            };

            View.OnClickListener onButtonIncClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toaster.makeToast(v.getContext(), "inc");
                }
            };

            tv_name.setText(person.getName());
            String amount = String.valueOf(person.getAmount()) + " RUB";
            tv_amount.setText(amount);
            tv_status.setText(person.getStatus() == 0 ? getString(R.string.tv_i_owe) : getString(R.string.tv_owe_me));

            iv_del.setOnClickListener(onButtonDelClickListener);
            iv_dec.setOnClickListener(onButtonDecClickListener);
            iv_inc.setOnClickListener(onButtonIncClickListener);

            linearLayoutManager = new LinearLayoutManager(this);
            entryArrayList = new ArrayList<>();
            entryListAdapter = new EntryListAdapter(this, entryArrayList);
            //entryListAdapter.setListener(this);

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(entryListAdapter);

            updateEntryArrayList();
        }
    }
}
