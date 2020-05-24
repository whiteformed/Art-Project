package com.example.art_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Debt> debts;
    private int Status;

    RecyclerViewAdapter(Context context, ArrayList<Debt> debts, int status) {
        this.context = context;
        this.Status = status;
        this.debts = getItemsWithStatus(debts, Status);
    }

    private ArrayList<Debt> getItemsWithStatus(ArrayList<Debt> debts, int status) {
        ArrayList<Debt> resultArray = new ArrayList<>();

        for (Debt debt:debts) {
            if (debt.getStatus() == status)
                resultArray.add(debt);
        }

        return resultArray;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {

        if (debts.get(position).getStatus() == Status) {
            holder.imageView.setImageResource(R.drawable.ic_trending_down);

            holder.textViewName.setText(debts.get(position).getName());

            String amount = debts.get(position).getAmount() + " RUB";
            holder.textViewAmount.setText(amount);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: clicked: " + holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return debts.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewAmount;
        ImageView imageView;

        RecyclerViewHolder(View view) {

            super(view);

            textViewName = view.findViewById(R.id.text_view_name);
            textViewAmount = view.findViewById(R.id.text_view_amount);
            imageView = view.findViewById(R.id.image_view);
        }
    }
}