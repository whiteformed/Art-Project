package com.example.art_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Entry> entryArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    EntryListAdapter(Context context, ArrayList<Entry> entryArrayList) {
        this.context = context;
        this.entryArrayList = entryArrayList;
    }

    public void setListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
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
        String amount = entryArrayList.get(position).getAmount() + " RUB";

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo show message when list is empty
                //onRecyclerViewItemClickListener.onItemClick(entryArrayList.get(position));
            }
        };

        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return entryArrayList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {


        RecyclerViewHolder(View view) {
            super(view);


        }
    }
}