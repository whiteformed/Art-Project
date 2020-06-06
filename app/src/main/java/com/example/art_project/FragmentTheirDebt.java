package com.example.art_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentTheirDebt extends Fragment {

    private ArrayList<Debt> debtsArrayList = new ArrayList<>();

    private ArrayList<Debt> getDebtsArrayList() {
        Debt debt = new Debt(0, "0", "Anton", "3000");
        debtsArrayList.add(debt);
        debt = new Debt(1, "1", "Aleksey", "1650");
        debtsArrayList.add(debt);
        debt = new Debt(0, "2", "Anatoliy", "890");
        debtsArrayList.add(debt);
        debt = new Debt(1, "3", "Alina", "410");
        debtsArrayList.add(debt);
        return debtsArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_their_debt, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), getDebtsArrayList(), 1); // getDebtsArrayList() is temporary
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}
