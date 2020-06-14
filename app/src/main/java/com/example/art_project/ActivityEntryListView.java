package com.example.art_project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityEntryListView extends AppCompatActivity implements OnEntryItemViewClickListener, OnEntryArrayListUpdateListener {
    TextView tv_name;
    TextView tv_total_amount;
    TextView tv_status;
    TextView tv_msg_empty_list;

    ImageView iv_person_del;
    ImageView iv_debt_dec;
    ImageView iv_debt_inc;

    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    EntryListAdapter entryArrayListAdapter;
    ArrayList<Entry> entryArrayList;

    SqlDatabaseHelper sqlDatabaseHelper;

    DialogHelper dialogHelper;

    Person person;

    @Override
    public void onEntryItemViewClick(Entry entry) {
        dialogHelper.createUpdEntryDialog(entry);
    }

    @Override
    public void onAddEntry(Entry newEntry) {
        boolean result = sqlDatabaseHelper.addEntry(newEntry);
        Informant.makeLogEntry(0, result);

        updateEntryArrayList();
    }

    @Override
    public void onUpdEntry(Entry updEntry) {
        boolean result = sqlDatabaseHelper.updEntry(updEntry);
        Informant.makeLogEntry(1, result);

        updateEntryArrayList();
    }

    @Override
    public void onDelEntry(int entryID) {
        boolean result = sqlDatabaseHelper.delEntry(entryID);
        Informant.makeLogEntry(2, result);

        updateEntryArrayList();
    }

    @Override
    public void onDelPerson(int personID) {
        boolean result = sqlDatabaseHelper.delPerson(personID);
        Informant.makeLogEntry(2, result);

        setResult(RESULT_OK);
        finish();
    }

    void updateEntryArrayList() {
        entryArrayList.clear();
        entryArrayList.addAll(sqlDatabaseHelper.getEntryArrayList(person.getID()));
        entryArrayListAdapter.notifyDataSetChanged();

        String totalAmount = getTotalAmount();
        tv_total_amount.setText(totalAmount);

        int visibility = entryArrayList.isEmpty() ? View.VISIBLE : View.INVISIBLE;
        tv_msg_empty_list.setVisibility(visibility);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list_view);

        LocaleHelper.setLocale(this);

        dialogHelper = new DialogHelper(this);
        dialogHelper.setOnEntryArrayListUpdateListener(this);

        sqlDatabaseHelper = new SqlDatabaseHelper(this);
        person = sqlDatabaseHelper.getPerson(getIntent().getIntExtra("id", 0));

        tv_msg_empty_list = findViewById(R.id.activity_entry_list_tv_msg_empty_list);

        if (person.getID() != 0) {
            rv = findViewById(R.id.activity_entry_list_rv);

            tv_name = findViewById(R.id.activity_entry_list_tv_name);
            tv_total_amount = findViewById(R.id.activity_entry_list_tv_total_amount);
            tv_status = findViewById(R.id.activity_entry_list_tv_status);

            iv_person_del = findViewById(R.id.activity_entry_list_iv_person_del);
            iv_debt_dec = findViewById(R.id.activity_entry_list_iv_debt_dec);
            iv_debt_inc = findViewById(R.id.activity_entry_list_iv_debt_inc);

            View.OnClickListener onButtonPersonDelClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogHelper.createDelPersonDialog(person.getID());
                }
            };

            View.OnClickListener onButtonDebtDecClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogHelper.createAddEntryDialog(person.getID(), 0);
                }
            };

            View.OnClickListener onButtonDebtIncClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogHelper.createAddEntryDialog(person.getID(), 1);
                }
            };

            tv_name.setText(person.getName());
            getTotalAmount();
            tv_status.setText(person.getStatus() == 0 ? getString(R.string.tv_i_owe) : getString(R.string.tv_owes_me));

            iv_person_del.setOnClickListener(onButtonPersonDelClickListener);
            iv_debt_dec.setOnClickListener(onButtonDebtDecClickListener);
            iv_debt_inc.setOnClickListener(onButtonDebtIncClickListener);

            linearLayoutManager = new LinearLayoutManager(this);
            entryArrayList = new ArrayList<>();
            entryArrayListAdapter = new EntryListAdapter(this, entryArrayList);
            entryArrayListAdapter.setOnEntryItemViewClickListener(this);

            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(entryArrayListAdapter);

            DividerItemDecoration line = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            line.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.sp_line)));
            rv.addItemDecoration(line);

            updateEntryArrayList();
        }
    }

    public String getTotalAmount() {
        int total = sqlDatabaseHelper.getPersonTotalAmount(person.getID());

        return total + getResources().getString(R.string.value);
    }
}
