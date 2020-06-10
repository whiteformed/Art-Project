package com.example.art_project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DialogHelper {
    Context context;

    OnPersonArrayListUpdateListener onPersonArrayListUpdateListener;
    OnEntryArrayListUpdateListener onEntryArrayListUpdateListener;

    DialogHelper(Context context) {
        this.context = context;
    }

    public void setOnPersonArrayListUpdateListener(OnPersonArrayListUpdateListener onPersonArrayListUpdateListener) {
        this.onPersonArrayListUpdateListener = onPersonArrayListUpdateListener;
    }

    public void setOnEntryArrayListUpdateListener(OnEntryArrayListUpdateListener onEntryArrayListUpdateListener) {
        this.onEntryArrayListUpdateListener = onEntryArrayListUpdateListener;
    }

    public void createAddPersonDialog(boolean defaultSwitchState) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_person_add);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText et_name = dialog.findViewById(R.id.dialog_person_add_et_name);

        final Switch sw = dialog.findViewById(R.id.dialog_person_add_sw);
        sw.setChecked(defaultSwitchState);

        final TextView tv_i_owe = dialog.findViewById(R.id.dialog_person_add_tv_i_owe);
        final TextView tv_owe_me = dialog.findViewById(R.id.dialog_person_add_tv_owe_me);

        Button btn_add = dialog.findViewById(R.id.dialog_person_add_btn_add);

        View.OnClickListener onButtonAddClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = -1;

                if (sw.isChecked()) {
                    status = 1;
                }
                else if (!sw.isChecked()) {
                    status = 0;
                }

                if (Objects.requireNonNull(et_name.getText().toString().trim().equals(""))) {
                    Toaster.makeToast(context, "No empty fields allowed!");
                } else {
                    Person newPerson = new Person(status, et_name.getText().toString());

                    onPersonArrayListUpdateListener.onAddPerson(newPerson);

                    dialog.cancel();
                }
            }
        };

        final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(isChecked);

                if (isChecked) {
                    tv_i_owe.setTextColor(ContextCompat.getColor(context, R.color.colorDarkText));
                    tv_owe_me.setTextColor(ContextCompat.getColor(context, R.color.colorLightText));
                } else {
                    tv_i_owe.setTextColor(ContextCompat.getColor(context, R.color.colorLightText));
                    tv_owe_me.setTextColor(ContextCompat.getColor(context, R.color.colorDarkText));
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

    public void createAddEntryDialog(final int personID, final int status) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_entry_add);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText et_amount = dialog.findViewById(R.id.dialog_entry_add_et_amount);
        final EditText et_comment = dialog.findViewById(R.id.dialog_entry_add_et_comment);

        Button btn_add = dialog.findViewById(R.id.dialog_entry_add_btn_add);

        View.OnClickListener onButtonAddClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(et_amount.getText().toString().trim().equals("")) || Objects.requireNonNull(et_comment.getText().toString().trim().equals(""))) {
                    Toaster.makeToast(context, "No empty fields allowed!");
                } else {
                    int amount = Integer.parseInt(et_amount.getText().toString());
                    String comment = et_comment.getText().toString();
                    String currentDate = getCurrentDate();

                    Entry newEntry = new Entry(status, personID, amount, comment, currentDate);

                    onEntryArrayListUpdateListener.onAddEntry(newEntry);

                    dialog.cancel();
                }
            }
        };

        btn_add.setOnClickListener(onButtonAddClickListener);

        dialog.show();
    }

    String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");

        return simpleDate.format(date);
    }
}