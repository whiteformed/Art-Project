package com.example.art_project;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Objects;

public class DialogHelper {
    Context context;

    AddButtonClickListener fragmentMyDebtListener;
    AddButtonClickListener fragmentTheirDebtListener;

    DialogHelper(Context context) {
        this.context = context;
    }

    public void setListener(AddButtonClickListener addButtonClickListener) {
        if (addButtonClickListener instanceof FragmentMyDebt)
            this.fragmentMyDebtListener = addButtonClickListener;

        if (addButtonClickListener instanceof FragmentTheirDebt)
            this.fragmentTheirDebtListener = addButtonClickListener;
    }

    public Dialog createAddDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_insert_person);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setLayout(context.getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

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
                int status = -1;

                if (sw.isChecked())
                    status = 1;
                else if (!sw.isChecked()) {
                    status = 0;
                }

                if (et_name.getText().toString().trim().equals("")) {
                    Toaster.makeToast(context, "No empty fields allowed!");
                } else {
                    Person newPerson = new Person(status, et_name.getText().toString(), 0);

                    if (status == 0) {
                        fragmentMyDebtListener.onAddButtonClick(newPerson);
                    }
                    else if (status == 1) {
                        fragmentTheirDebtListener.onAddButtonClick(newPerson);
                    }
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

        return dialog;
    }
}