package com.example.art_project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DialogHelper {
    Context context;

    OnActivityPersonsUpdateListener onActivityPersonsUpdateListener;
    OnActivityEntriesUpdateListener onActivityEntriesUpdateListener;

    DialogHelper(Context context) {
        this.context = context;
    }

    public void setOnActivityPersonsUpdateListener(OnActivityPersonsUpdateListener onActivityPersonsUpdateListener) {
        this.onActivityPersonsUpdateListener = onActivityPersonsUpdateListener;
    }

    public void setOnActivityEntriesUpdateListener(OnActivityEntriesUpdateListener onActivityEntriesUpdateListener) {
        this.onActivityEntriesUpdateListener = onActivityEntriesUpdateListener;
    }

    public void createAddPersonDialog(boolean personStatus) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_person_add);
        dialog.setCancelable(true);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow())
                .setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final MaterialEditText et_name = dialog.findViewById(R.id.dialog_person_add_et_name);

        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch sw = dialog.findViewById(R.id.dialog_person_add_sw);

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

            if (Objects.requireNonNull(et_name.getText()).toString().trim().equals("")) {
                et_name.setError(context.getString(R.string.err_empty_field));
            } else {
                Person newPerson = new Person(status, et_name.getText().toString());

                onActivityPersonsUpdateListener.onAddPerson(newPerson);

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

    public void createUpdPersonDialog(int caller, Person person) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_person_upd);
        dialog.setCancelable(true);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow())
                .setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final MaterialEditText et_name = dialog.findViewById(R.id.dialog_person_upd_et_name);

        et_name.setText(String.valueOf(person.getName()));

        Button btn_upd = dialog.findViewById(R.id.dialog_person_upd_btn_upd);

        View.OnClickListener onButtonUpdClickListener = v -> {
            if (Objects.requireNonNull(et_name.getText()).toString().trim().equals("")) {
                et_name.setError(context.getString(R.string.err_empty_field));
            } else {
                String name = et_name.getText().toString();

                person.setName(name);

                if (caller == 0) {
                    onActivityPersonsUpdateListener.onUpdPerson(person);
                } else if (caller == 1) {
                    onActivityEntriesUpdateListener.onUpdPerson(person);
                }

                dialog.cancel();
            }
        };

        btn_upd.setOnClickListener(onButtonUpdClickListener);

        dialog.show();
    }

    public void createDelPersonDialog(int caller, final int personID) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_person_del);
        dialog.setCancelable(true);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow())
                .setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_yes = dialog.findViewById(R.id.dialog_person_del_btn_yes);
        Button btn_no = dialog.findViewById(R.id.dialog_person_del_btn_no);

        View.OnClickListener onButtonYesClickListener = v -> {
            if (caller == 0) {
                onActivityPersonsUpdateListener.onDelPerson(personID);
            } else if (caller == 1) {
                onActivityEntriesUpdateListener.onDelPerson(personID);
            }

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

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow())
                .setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final MaterialEditText et_amount = dialog.findViewById(R.id.dialog_entry_add_et_amount);
        final MaterialEditText et_comment = dialog.findViewById(R.id.dialog_entry_add_et_comment);

        Button btn_add = dialog.findViewById(R.id.dialog_entry_add_btn_add);

        View.OnClickListener onButtonAddClickListener = v -> {
            if ((Objects.requireNonNull(et_amount.getText()).toString().trim().equals("")) ||
                    Objects.requireNonNull(et_comment.getText()).toString().trim().equals("")) {
                if (Objects.requireNonNull(et_amount.getText()).toString().trim().equals("")) {
                    et_amount.setError(context.getString(R.string.err_empty_field));
                }
                if (Objects.requireNonNull(et_comment.getText()).toString().trim().equals("")) {
                    et_comment.setError(context.getString(R.string.err_empty_field));
                }
            } else {
                int amount = Integer.parseInt(et_amount.getText().toString());
                String comment = et_comment.getText().toString();
                String currentDate = getCurrentDate();

                Entry newEntry = new Entry(status, personID, amount, comment, currentDate);

                onActivityEntriesUpdateListener.onAddEntry(newEntry);

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

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow())
                .setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        final MaterialEditText et_amount = dialog.findViewById(R.id.dialog_entry_upd_et_amount);
        final MaterialEditText et_comment = dialog.findViewById(R.id.dialog_entry_upd_et_comment);

        et_amount.setText(String.valueOf(entry.getAmount()));
        et_comment.setText(entry.getComment());

        Button btn_upd = dialog.findViewById(R.id.dialog_entry_upd_btn_upd);

        View.OnClickListener onButtonUpdClickListener = v -> {
            if (Objects.requireNonNull(et_amount.getText()).toString().trim().equals("") ||
                    Objects.requireNonNull(et_comment.getText()).toString().trim().equals("")) {
                if (Objects.requireNonNull(et_amount.getText()).toString().trim().equals("")) {
                    et_amount.setError(context.getString(R.string.err_empty_field));
                }
                if (Objects.requireNonNull(et_comment.getText()).toString().trim().equals("")) {
                    et_comment.setError(context.getString(R.string.err_empty_field));
                }
            } else {
                int amount = Integer.parseInt(et_amount.getText().toString());
                String comment = et_comment.getText().toString();

                entry.setAmount(amount);
                entry.setComment(comment);

                onActivityEntriesUpdateListener.onUpdEntry(entry);

                dialog.cancel();
            }
        };

        btn_upd.setOnClickListener(onButtonUpdClickListener);

        dialog.show();
    }

    String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDate.format(date);
    }
}