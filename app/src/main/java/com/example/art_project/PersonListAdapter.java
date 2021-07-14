package com.example.art_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.RecyclerViewHolder> {
    private final Context context;
    private final ArrayList<Person> personArrayList;
    private final int status;

    private final OnPersonItemViewActionListener onPersonItemViewActionListener;

    PersonListAdapter(Context context, ArrayList<Person> personArrayList, int status, OnPersonItemViewActionListener onPersonItemViewActionListener) {
        this.context = context;
        this.personArrayList = personArrayList;
        this.status = status;
        this.onPersonItemViewActionListener = onPersonItemViewActionListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_view_person, parent, false);

        return new RecyclerViewHolder(view);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        SqlDatabaseHelper sqlDatabaseHelper = new SqlDatabaseHelper(context);
        String totalAmount = sqlDatabaseHelper.getPersonTotalAmount(personArrayList.get(position).getID()) + context.getResources().getString(R.string.value);

        if (status == 0) {
            holder.iv_status.setImageResource(R.drawable.ic_trending_down);
        } else if (status == 1) {
            holder.iv_status.setImageResource(R.drawable.ic_trending_up);
        }

        holder.tv_name.setText(personArrayList.get(position).getName());
        holder.tv_amount.setText(totalAmount);

        holder.iv_options.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), holder.iv_options);
            popup.inflate(R.menu.options_menu);
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_btn_delete) {
                    onPersonItemViewActionListener.onPersonItemViewOptionDeleteClick(personArrayList.get(position).getID());
                }

                return false;
            });

            popup.show();
        });

        holder.itemView.setOnClickListener(v -> onPersonItemViewActionListener.onPersonItemViewClick(personArrayList.get(position).getID()));
    }

    @Override
    public int getItemCount() {
        return personArrayList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_name;
        TextView tv_amount;
        ImageView iv_status;
        ImageView iv_options;

        RecyclerViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.item_view_person_tv_name);
            tv_amount = view.findViewById(R.id.item_view_person_tv_amount);
            iv_status = view.findViewById(R.id.item_view_person_iv_status);
            iv_options = view.findViewById(R.id.item_view_person_iv_options);
        }
    }
}