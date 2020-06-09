package com.example.art_project;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class AsynchronousTask extends AsyncTask<Void, Void, Void> {
    private SqlDatabaseHelper sqlDatabaseHelper;

    private PersonListAdapter personListAdapter;
    private ArrayList<Person> personArrayList;
    private int status;

    private static final String TAG = "AsynchronousTask";

    AsynchronousTask(SqlDatabaseHelper sqlDatabaseHelper, PersonListAdapter personListAdapter, ArrayList<Person> personArrayList, int status) {
        this.sqlDatabaseHelper = sqlDatabaseHelper;
        this.personListAdapter = personListAdapter;
        this.personArrayList = personArrayList;
        this.status = status;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getDataFromDatabase();

        return null;
    }

    private void getDataFromDatabase() {
        personArrayList.clear();
        personArrayList.addAll(sqlDatabaseHelper.getPersonArrayList(status));

        if (!personArrayList.isEmpty()) {
            Log.i(TAG, "Successfully read data from DB");
        } else {
            Log.i(TAG, "Failed while reading data from DB");
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        personListAdapter.notifyDataSetChanged();

        Log.i(TAG, "Person List Updated!");
    }
}
