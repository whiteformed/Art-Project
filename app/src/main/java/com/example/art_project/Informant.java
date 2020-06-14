package com.example.art_project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Informant {
    public static void makeToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void makeLogEntry(int op, boolean result) {
        String appendix = result ? "Success" : "Failure";

        switch (op) {
            case 0:
                Log.i("Database Log", "Adding an item to DB: " + appendix);
                break;
            case 1:
                Log.i("Database Log", "Updating an item in DB: " + appendix);
                break;
            case 2:
                Log.i("Database Log", "Deleting an item from DB: " + appendix);
                break;
        }
    }
}
