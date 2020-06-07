package com.example.art_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqlDatabaseHelper extends SQLiteOpenHelper {
    private static final String database_name = "sql_database";

    private static final String table_persons = "persons";
    private static final String column_person_id = "id";
    private static final String column_person_status = "status";
    private static final String column_person_name = "name";
    private static final String column_person_amount = "amount";

    private static final String table_debts = "debts";
    private static final String column_debt_id = "id";
    private static final String column_debt_status = "status";
    private static final String column_debt_name = "name";
    private static final String column_debt_amount = "amount";
    private static final String column_debt_description = "description";

    private String sqlCommand;

    SqlDatabaseHelper(@Nullable Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase db) {
        sqlCommand = "create table "
                + table_debts + " ("
                + column_debt_id + " integer primary key autoincrement, "
                + column_debt_status + " text, "
                + column_debt_name + " text, "
                + column_debt_amount + " text, "
                + column_debt_description + " text)";

        db.execSQL(sqlCommand);

        sqlCommand = "create table "
                + table_persons + " ("
                + column_person_id + " integer primary key autoincrement, "
                + column_person_status + " text, "
                + column_person_name + " text, "
                + column_person_amount + " text)";

        db.execSQL(sqlCommand);
    }

    public boolean insertPerson(String table, Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_person_status, person.getStatus().trim());
        contentValues.put(column_person_name, person.getName().trim());
        contentValues.put(column_person_amount, person.getAmount().trim());

        long rowInserted = db.insert(table, null, contentValues);

        db.close();

        return rowInserted != -1;
    }

    public ArrayList<Person> getPersonList(String table, String status) {
        ArrayList<Person> personArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        sqlCommand = "select * from " + table + " where "
                + column_person_status + " = '" + status + "'";

        Cursor cursor = db.rawQuery(sqlCommand, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(
                        cursor.getString(cursor.getColumnIndex(column_person_status)),
                        cursor.getString(cursor.getColumnIndex(column_person_name)),
                        cursor.getString(cursor.getColumnIndex(column_person_amount)));

                personArrayList.add(person);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return personArrayList;
    }

    public boolean insertDebt(String table, Debt debt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_debt_status, debt.getStatus());
        contentValues.put(column_debt_name, debt.getName().trim());
        contentValues.put(column_debt_amount, debt.getAmount().trim());
        contentValues.put(column_debt_description, debt.getDescription().trim());

        long rowInserted = db.insert(table, null, contentValues);

        db.close();

        return rowInserted != -1;
    }

    public boolean insertDebtList(String table, ArrayList<Debt> debtArrayList) {
        for (int i = 0; i < debtArrayList.size(); i++) {
            if (!insertDebt(table, debtArrayList.get(i)))
                return false;
        }

        return true;
    }

    public boolean deleteDebt(String table, Debt debt) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause
                = column_debt_status + " = '" + debt.getStatus() + "' and "
                + column_debt_name + " = '" + debt.getName().trim() + "' and "
                + column_debt_amount + " = '" + debt.getAmount().trim() + "' and "
                + column_debt_description + " = '" + debt.getDescription().trim()+ "'";

        int rowsAffected = db.delete(table_debts, whereClause, null);

        db.close();

        return rowsAffected != 0;
    }

    public boolean deleteDebtList(String table, ArrayList<Debt> debtArrayList) {
        for (int i = 0; i < debtArrayList.size(); i++) {
            if (!deleteDebt(table, debtArrayList.get(i)))
                return false;
        }

        return true;
    }

    public boolean updateDebt(String table, Debt oldDebt, Debt newDebt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_debt_status, newDebt.getStatus());
        contentValues.put(column_debt_name, newDebt.getName().trim());
        contentValues.put(column_debt_amount, newDebt.getAmount().trim());
        contentValues.put(column_debt_description, newDebt.getDescription().trim());

        String whereClause
                = column_debt_status + " = '" + oldDebt.getStatus() + "' and "
                + column_debt_name + " = '" + oldDebt.getName().trim() + "' and "
                + column_debt_amount + " = '" + oldDebt.getAmount().trim() + "'";

        int rowsAffected = db.update(table, contentValues, whereClause, null);

        return rowsAffected != 0;
    }

    public boolean hasDebt(String table, Debt debt) {
        SQLiteDatabase db = this.getReadableDatabase();

        boolean exists = false;

        sqlCommand = "select * from " + table + " where "
                + column_debt_status + " = '" + debt.getStatus() + "' and "
                + column_debt_name + " = '" + debt.getName().trim() + "' and "
                + column_debt_amount + " = '" + debt.getAmount().trim() + "'";

        Cursor cursor = db.rawQuery(sqlCommand, null);

        if (cursor.moveToFirst()) {
            exists = true;
        }

        cursor.close();
        db.close();

        return exists;
    }

    public ArrayList<Debt> getDebtList(String table) {
        ArrayList<Debt> debtArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        sqlCommand = "select * from " + table;
        Cursor cursor = db.rawQuery(sqlCommand, null);

        if (cursor.moveToFirst()) {
            do {
                Debt debt = new Debt(
                        cursor.getString(cursor.getColumnIndex(column_debt_status)),
                        cursor.getString(cursor.getColumnIndex(column_debt_name)),
                        cursor.getString(cursor.getColumnIndex(column_debt_amount)),
                        cursor.getString(cursor.getColumnIndex(column_debt_description)));

                debtArrayList.add(debt);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return debtArrayList;
    }

    public static String getPersonsTableName() {
        return table_persons;
    }

    public static String getDebtsTableName() {
        return table_debts;
    }
}
