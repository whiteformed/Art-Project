package com.example.art_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FragmentTheirDebt extends Fragment {
    View view;

    SqlDatabaseHelper sqlDatabaseHelper;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PersonListAdapter personArrayListAdapter;
    ArrayList<Person> personArrayList = new ArrayList<>();
    OnPersonItemViewActionListener onPersonItemViewActionListener;
    int status;

    TextView tv_msg_empty_list;

//    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView,
//                              @NonNull RecyclerView.ViewHolder viewHolder,
//                              @NonNull RecyclerView.ViewHolder target) {
//            int fromPosition = viewHolder.getAdapterPosition();
//            int toPosition = target.getAdapterPosition();
//
//            Collections.swap(personArrayList, fromPosition, toPosition);
//            Objects.requireNonNull(recyclerView.getAdapter()).notifyItemMoved(fromPosition, toPosition);
//
//            sqlDatabaseHelper.swpPerson(personArrayList.get(fromPosition).getID(), personArrayList.get(toPosition).getID());
//
//            return false;
//        }
//
//        @Override
//        public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
//            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
//        }
//
//        @Override
//        public boolean isLongPressDragEnabled() {
//            return true;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//        }
//    };

    public void updatePersonArrayList() {
        personArrayList.clear();
        personArrayList.addAll(new SqlDatabaseHelper(getActivity()).getPersonArrayList(status));
        personArrayListAdapter.notifyDataSetChanged();

        int visibility = personArrayList.isEmpty() ? View.VISIBLE : View.INVISIBLE;
        tv_msg_empty_list.setVisibility(visibility);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person_list, container, false);

        tv_msg_empty_list = view.findViewById(R.id.fragment_person_list_tv_msg_empty_list);

        recyclerView = view.findViewById(R.id.fragment_person_list_rv);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        personArrayListAdapter = new PersonListAdapter(getActivity(), personArrayList, status, onPersonItemViewActionListener);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(personArrayListAdapter);

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        updatePersonArrayList();

        return view;
    }
}
