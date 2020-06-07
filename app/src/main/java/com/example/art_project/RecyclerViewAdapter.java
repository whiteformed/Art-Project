package com.example.art_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Person> personArrayList;
    private int Status;

    RecyclerViewAdapter(Context context, ArrayList<Person> personArrayList, int status) {
        this.context = context;
        this.personArrayList = personArrayList;
        this.Status = status;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_view_person, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        String fullAmount = personArrayList.get(position).getAmount() + " RUB";

        if (Status == 0) {
            holder.iv.setImageResource(R.drawable.ic_trending_down);
        } else if (Status == 1) {
            holder.iv.setImageResource(R.drawable.ic_trending_up);
        }

        holder.tv_name.setText(personArrayList.get(position).getName());
        holder.tv_amount.setText(fullAmount);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        };

        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return personArrayList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tv_name;
        TextView tv_amount;
        ImageView iv;

        RecyclerViewHolder(View view) {
            super(view);

            cv = view.findViewById(R.id.cv_person);
            tv_name = view.findViewById(R.id.tv_person_name);
            tv_amount = view.findViewById(R.id.tv_person_amount);
            iv = view.findViewById(R.id.iv_person_status);
        }
    }
}