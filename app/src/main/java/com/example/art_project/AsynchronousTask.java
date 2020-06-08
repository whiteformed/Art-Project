package com.example.art_project;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class AsynchronousTask extends AsyncTask<Void, Void, Void> {
    private PersonListAdapter personListAdapter;
    private SqlDatabaseHelper sqlDatabaseHelper;
    private String table;
    private ArrayList<Person> personArrayList;
    private int status;

    private static final String TAG = "AsynchronousTask";

    AsynchronousTask(PersonListAdapter personListAdapter, SqlDatabaseHelper sqlDatabaseHelper, ArrayList<Person> personArrayList, int status) {
        this.personListAdapter = personListAdapter;
        this.personArrayList = personArrayList;
        this.sqlDatabaseHelper = sqlDatabaseHelper;
        this.table = table;
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
        Log.i(TAG, table + " has items " + personArrayList.size());

        if (!personArrayList.isEmpty()) {
            Log.i(TAG, "Successfully read " + table + " table from DB");
        } else {
            Log.i(TAG, "Failed while reading " + table + " table from DB");
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        personListAdapter.notifyDataSetChanged();

        Log.i(TAG, "Person List Updated!");
    }
}
