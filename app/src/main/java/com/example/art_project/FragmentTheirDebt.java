package com.example.art_project;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentTheirDebt extends Fragment implements RecyclerViewItemClickListener {
    View view;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton floatingActionButton;

    SqlDatabaseHelper sqlDatabaseHelper;
    ArrayList<Person> personArrayList = new ArrayList<>();

    private String tablePersons = SqlDatabaseHelper.getPersonsTableName();

    @Override
    public void onItemClickListener(int pos) {

    }

    private FragmentActivity getNonNullActivity() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("Null returned from getActivity() method");
        }
    }

    private void onFloatingActionButtonClicked() {
        final Dialog dialog = new Dialog(getNonNullActivity());
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
                    boolean result = sqlDatabaseHelper.insertPerson(tablePersons, newPerson);
                    updateList();

                    dialog.cancel();
                }
            }
        };

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_i_owe.setTextColor(getResources().getColor(R.color.colorDarkText));
                    tv_owe_me.setTextColor(getResources().getColor(R.color.colorLightText));
                } else {
                    tv_i_owe.setTextColor(getResources().getColor(R.color.colorLightText));
                    tv_owe_me.setTextColor(getResources().getColor(R.color.colorDarkText));
                }
            }
        };

        sw.setOnCheckedChangeListener(onCheckedChangeListener);
        btn_add.setOnClickListener(onButtonAddClickListener);

        dialog.show();
    }

    private void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateList() {
        AsynchronousTask task = new AsynchronousTask(recyclerViewAdapter, personArrayList, sqlDatabaseHelper, tablePersons);
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_their_debt, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), personArrayList, "0"); // getDebtsArrayList() is temporary
        recyclerView.setAdapter(recyclerViewAdapter);

        //Creating a FAB
        floatingActionButton = view.findViewById(R.id.fab);

        //Setup the onClick event for FAB
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatingActionButtonClicked();
            }
        };

        floatingActionButton.setOnClickListener(onClickListener);

        return view;
    }
}

