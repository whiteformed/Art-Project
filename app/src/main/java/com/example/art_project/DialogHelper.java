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

    public void createAddPersonDialog(boolean personStatus) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_person_add);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText et_name = dialog.findViewById(R.id.dialog_person_add_et_name);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        final Switch sw = dialog.findViewById(R.id.dialog_person_add_sw);

        final TextView tv_i_owe = dialog.findViewById(R.id.dialog_person_add_tv_i_owe);
        final TextView tv_owe_me = dialog.findViewById(R.id.dialog_person_add_tv_owe_me);

        sw.setChecked(personStatus);
        if (sw.isChecked()) {
            tv_i_owe.setTextColor(ContextCompat.getColor(context, R.color.colorInactiveText));
            tv_owe_me.setTextColor(ContextCompat.getColor(context, R.color.colorActiveText));
        } else {
            tv_i_owe.setTextColor(ContextCompat.getColor(context, R.color.colorActiveText));
            tv_owe_me.setTextColor(ContextCompat.getColor(context, R.color.colorInactiveText));
        }

        Button btn_add = dialog.findViewById(R.id.dialog_person_add_btn_add);

        View.OnClickListener onButtonAddClickListener = v -> {
            int status = -1;

            if (sw.isChecked()) {
                status = 1;
            } else if (!sw.isChecked()) {
                status = 0;
            }

            if (Objects.requireNonNull(et_name.getText().toString().trim().equals(""))) {
                Informant.makeToast(context, "No empty fields allowed!");
            } else {
                Person newPerson = new Person(status, et_name.getText().toString());

                onPersonArrayListUpdateListener.onAddPerson(newPerson);

                dialog.cancel();
            }
        };

        final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
            buttonView.setChecked(isChecked);

            if (isChecked) {
                tv_i_owe.setTextColor(ContextCompat.getColor(context, R.color.colorInactiveText));
                tv_owe_me.setTextColor(ContextCompat.getColor(context, R.color.colorActiveText));
            } else {
                tv_i_owe.setTextColor(ContextCompat.getColor(context, R.color.colorActiveText));
                tv_owe_me.setTextColor(ContextCompat.getColor(context, R.color.colorInactiveText));
            }
        };

        View.OnClickListener onIOweClickListener = v -> onCheckedChangeListener.onCheckedChanged(sw, false);

        View.OnClickListener onOweMeClickListener = v -> onCheckedChangeListener.onCheckedChanged(sw, true);

        sw.setOnCheckedChangeListener(onCheckedChangeListener);

        tv_i_owe.setOnClickListener(onIOweClickListener);
        tv_owe_me.setOnClickListener(onOweMeClickListener);

        btn_add.setOnClickListener(onButtonAddClickListener);

        dialog.show();
    }

    public void createDelPersonDialog(final int personID) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_person_del);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_yes = dialog.findViewById(R.id.dialog_person_del_btn_yes);
        Button btn_no = dialog.findViewById(R.id.dialog_person_del_btn_no);

        View.OnClickListener onButtonYesClickListener = v -> {
            onEntryArrayListUpdateListener.onDelPerson(personID);

            dialog.cancel();
        };

        View.OnClickListener onButtonNoClickListener = v -> dialog.cancel();

        btn_yes.setOnClickListener(onButtonYesClickListener);
        btn_no.setOnClickListener(onButtonNoClickListener);

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

        View.OnClickListener onButtonAddClickListener = v -> {
            if (Objects.requireNonNull(et_amount.getText().toString().trim().equals("")) || Objects.requireNonNull(et_comment.getText().toString().trim().equals(""))) {
                Informant.makeToast(context, "No empty fields allowed!");
            } else {
                int amount = Integer.parseInt(et_amount.getText().toString());
                String comment = et_comment.getText().toString();
                String currentDate = getCurrentDate();

                Entry newEntry = new Entry(status, personID, amount, comment, currentDate);

                onEntryArrayListUpdateListener.onAddEntry(newEntry);

                dialog.cancel();
            }
        };

        btn_add.setOnClickListener(onButtonAddClickListener);

        dialog.show();
    }

    public void createUpdEntryDialog(final Entry entry) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_entry_upd);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText et_amount = dialog.findViewById(R.id.dialog_entry_upd_et_amount);
        final EditText et_comment = dialog.findViewById(R.id.dialog_entry_upd_et_comment);

        et_amount.setText(String.valueOf(entry.getAmount()));
        et_comment.setText(entry.getComment());

        Button btn_del = dialog.findViewById(R.id.dialog_entry_upd_btn_del);
        Button btn_upd = dialog.findViewById(R.id.dialog_entry_upd_btn_upd);

        View.OnClickListener onButtonDelClickListener = v -> {
            createDelEntryDialog(entry);

            dialog.cancel();
        };

        View.OnClickListener onButtonUpdClickListener = v -> {
            if (Objects.requireNonNull(et_amount.getText().toString().trim().equals("")) || Objects.requireNonNull(et_comment.getText().toString().trim().equals(""))) {
                Informant.makeToast(context, "No empty fields allowed!");
            } else {
                int amount = Integer.parseInt(et_amount.getText().toString());
                String comment = et_comment.getText().toString();

                entry.setAmount(amount);
                entry.setComment(comment);

                onEntryArrayListUpdateListener.onUpdEntry(entry);

                dialog.cancel();
            }
        };

        btn_del.setOnClickListener(onButtonDelClickListener);
        btn_upd.setOnClickListener(onButtonUpdClickListener);

        dialog.show();
    }

    public void createDelEntryDialog(final Entry entry) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_entry_del);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_yes = dialog.findViewById(R.id.dialog_entry_del_btn_yes);
        Button btn_no = dialog.findViewById(R.id.dialog_entry_del_btn_no);

        View.OnClickListener onButtonYesClickListener = v -> {
            onEntryArrayListUpdateListener.onDelEntry(entry.getID());

            dialog.cancel();
        };

        View.OnClickListener onButtonNoClickListener = v -> dialog.cancel();

        btn_yes.setOnClickListener(onButtonYesClickListener);
        btn_no.setOnClickListener(onButtonNoClickListener);

        dialog.show();
    }

    String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDate.format(date);
    }
}