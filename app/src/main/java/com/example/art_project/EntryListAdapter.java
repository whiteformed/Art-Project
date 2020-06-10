package com.example.art_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Entry> entryArrayList;
    private OnEntryItemClickListener onEntryItemClickListener;

    EntryListAdapter(Context context, ArrayList<Entry> entryArrayList) {
        this.context = context;
        this.entryArrayList = entryArrayList;
    }

    public void setOnEntryItemClickListener(OnEntryItemClickListener onEntryItemClickListener) {
        this.onEntryItemClickListener = onEntryItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_entry, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        if (entryArrayList.get(position).getStatus() == 0) {
            holder.iv_status.setImageResource(R.drawable.ic_debt_dec);
        } else if (entryArrayList.get(position).getStatus() == 1) {
            holder.iv_status.setImageResource(R.drawable.ic_debt_inc);
        }

        String amount = (entryArrayList.get(position).getAmount()) + context.getResources().getString(R.string.value);
        holder.tv_amount.setText(amount);
        holder.tv_comment.setText(entryArrayList.get(position).getComment());
        holder.tv_date.setText(entryArrayList.get(position).getDate());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEntryItemClickListener.onItemClick();
            }
        };

        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return entryArrayList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_amount;
        TextView tv_comment;
        TextView tv_date;
        ImageView iv_status;

        RecyclerViewHolder(View view) {
            super(view);

            tv_amount = view.findViewById(R.id.item_entry_tv_amount);
            tv_comment = view.findViewById(R.id.item_entry_tv_comment);
            tv_date = view.findViewById(R.id.item_entry_tv_date);
            iv_status = view.findViewById(R.id.item_entry_iv_status);
        }
    }
}