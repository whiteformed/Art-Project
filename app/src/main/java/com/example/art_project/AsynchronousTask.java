package com.example.art_project;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class AsynchronousTask extends AsyncTask<Integer, Void, Void> {
    private StringBuilder buffer = new StringBuilder();
    private String data = "";
    private RecyclerViewAdapter adapter;
    private ArrayList<Person> personArrayList;
    private SqlDatabaseHelper sqlDatabaseHelper;
    private String table;

    private static final String TAG = "AsynchronousTask";

    AsynchronousTask(RecyclerViewAdapter adapter, ArrayList<Person> personArrayList, SqlDatabaseHelper sqlDatabaseHelper, String table) {
        this.adapter = adapter;
        this.personArrayList = personArrayList;
        this.sqlDatabaseHelper = sqlDatabaseHelper;
        this.table = table;
    }

    @Override
    protected Void doInBackground(Integer... op) {
        getDataFromDatabase();

        return null;
    }

    private void getDataFromDatabase() {
        personArrayList.clear();
        personArrayList.addAll(sqlDatabaseHelper.getPersonList(table));

        if (!personArrayList.isEmpty()) {
            Log.i(TAG, "Successfully read " + table + " table from DB");
        } else {
            Log.i(TAG, "Failed while reading " + table + " table from DB");
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.notifyDataSetChanged();

        Log.i(TAG, "Person List Updated!");
    }
}
