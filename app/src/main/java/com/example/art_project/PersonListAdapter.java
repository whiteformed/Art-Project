package com.example.art_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Person> personArrayList;
    private int status;
    private OnPersonItemClickListener onPersonItemClickListener;

    PersonListAdapter(Context context, ArrayList<Person> personArrayList, int status, OnPersonItemClickListener onPersonItemClickListener) {
        this.context = context;
        this.personArrayList = personArrayList;
        this.status = status;
        this.onPersonItemClickListener = onPersonItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_person, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        SqlDatabaseHelper sqlDatabaseHelper = new SqlDatabaseHelper(context);
        String totalAmount = sqlDatabaseHelper.getPersonTotalAmount(personArrayList.get(position).getID()) + " RUB";

        if (status == 0) {
            holder.iv_status.setImageResource(R.drawable.ic_trending_down);
        } else if (status == 1) {
            holder.iv_status.setImageResource(R.drawable.ic_trending_up);
        }

        holder.tv_name.setText(personArrayList.get(position).getName());
        holder.tv_amount.setText(totalAmount);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPersonItemClickListener.onItemClick(personArrayList.get(position).getID());
            }
        };

        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return personArrayList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_amount;
        ImageView iv_status;

        RecyclerViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.item_person_tv_name);
            tv_amount = view.findViewById(R.id.item_person_tv_amount);
            iv_status = view.findViewById(R.id.item_person_iv_status);
        }
    }
}