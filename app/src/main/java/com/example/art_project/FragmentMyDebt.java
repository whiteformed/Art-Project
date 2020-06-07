package com.example.art_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentMyDebt extends Fragment implements AddButtonClickListener, RecyclerViewItemClickListener {
    View view;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    SqlDatabaseHelper sqlDatabaseHelper;
    ArrayList<Person> personArrayList = new ArrayList<>();
    int status = 0;

    private String tablePersons = SqlDatabaseHelper.getPersonsTableName();

    @Override
    public void onItemClick(int pos) {

    }

    private FragmentActivity getNonNullActivity() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("Null returned from getActivity() method");
        }
    }

    @Override
    public void onAddButtonClick(Person person) {
        boolean result = sqlDatabaseHelper.addPerson(person);
        updateList();
    }

    private void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateList() {
        AsynchronousTask task = new AsynchronousTask(recyclerViewAdapter, sqlDatabaseHelper, tablePersons, personArrayList, status);
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person_list, container, false);

        DialogHelper dialogHelper = ((ActivityMain) getNonNullActivity()).getDialog();
        dialogHelper.setListener(this);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sqlDatabaseHelper = new SqlDatabaseHelper(getActivity());
        personArrayList = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), personArrayList, status);
        recyclerView.setAdapter(recyclerViewAdapter);

        updateList();

        return view;
    }
}
