package com.example.art_project;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.RecyclerViewHolder> {
    private final Context context;
    private final ArrayList<Entry> entryArrayList;
    private OnEntryItemViewClickListener onEntryItemViewClickListener;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    EntryListAdapter(Context context, ArrayList<Entry> entryArrayList) {
        this.context = context;
        this.entryArrayList = entryArrayList;
    }

    public void setOnEntryItemViewClickListener(OnEntryItemViewClickListener onEntryItemViewClickListener) {
        this.onEntryItemViewClickListener = onEntryItemViewClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_view_entry, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(entryArrayList.get(position).getID()));
        viewBinderHelper.closeLayout(String.valueOf(entryArrayList.get(position).getID()));


        if (entryArrayList.get(position).getStatus() == 0) {
            holder.iv_status.setImageResource(R.drawable.ic_debt_dec);
        } else if (entryArrayList.get(position).getStatus() == 1) {
            holder.iv_status.setImageResource(R.drawable.ic_debt_inc);
        }

        String amount = (entryArrayList.get(position).getAmount()) + context.getResources().getString(R.string.value);
        holder.tv_amount.setText(amount);
        holder.tv_comment.setText(entryArrayList.get(position).getComment());
        holder.tv_date.setText(entryArrayList.get(position).getDate());

        holder.iv_edt.setOnClickListener(v -> onEntryItemViewClickListener.onEntryItemEditClick(entryArrayList.get(position)));

        holder.iv_del.setOnClickListener(v -> onEntryItemViewClickListener.onEntryItemDeleteClick(entryArrayList.get(position)));

        holder.swipeRevealLayout.setOnClickListener(v -> viewBinderHelper.closeLayout(String.valueOf(entryArrayList.get(position).getID())));
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

        SwipeRevealLayout swipeRevealLayout;
        ImageView iv_edt;
        ImageView iv_del;

        RecyclerViewHolder(View view) {
            super(view);

            tv_amount = view.findViewById(R.id.item_view_entry_tv_amount);
            tv_comment = view.findViewById(R.id.item_view_entry_tv_comment);
            tv_date = view.findViewById(R.id.item_view_entry_tv_date);
            iv_status = view.findViewById(R.id.item_view_entry_iv_status);

            swipeRevealLayout = view.findViewById(R.id.item_view_entry_srl);
            iv_edt = view.findViewById(R.id.item_entry_iv_edt);
            iv_del = view.findViewById(R.id.item_entry_iv_del);
        }
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }
}