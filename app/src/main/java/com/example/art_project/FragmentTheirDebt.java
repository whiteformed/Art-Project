package com.example.art_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentTheirDebt extends Fragment {
    View view;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PersonListAdapter personListAdapter;
    ArrayList<Person> personArrayList = new ArrayList<>();

    int status = 1;

    public void updatePersonArrayList() {
        SqlDatabaseHelper sqlDatabaseHelper = new SqlDatabaseHelper(getActivity());

        AsynchronousTask task = new AsynchronousTask(personListAdapter, sqlDatabaseHelper, personArrayList, status);
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person_list, container, false);

        recyclerView = view.findViewById(R.id.fragment_person_list_rv);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        personListAdapter = new PersonListAdapter(getActivity(), personArrayList, status);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(personListAdapter);

        updatePersonArrayList();

        return view;
    }
}
