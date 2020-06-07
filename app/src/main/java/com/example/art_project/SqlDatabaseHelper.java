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

    private static final String table_entries = "entries";
    private static final String column_entry_id = "id";
    private static final String column_entry_status = "status";
    private static final String column_entry_person = "person";
    private static final String column_entry_amount = "amount";
    private static final String column_entry_comment = "comment";

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
                + table_persons + " ("
                + column_person_id + " integer primary key autoincrement, "
                + column_person_status + " integer, "
                + column_person_name + " text, "
                + column_person_amount + " integer)";

        db.execSQL(sqlCommand);

        sqlCommand = "create table "
                + table_entries + " ("
                + column_entry_id + " integer primary key autoincrement, "
                + column_entry_status + " integer, "
                + column_entry_person + " integer, "
                + column_entry_amount + " integer, "
                + column_entry_comment + " text)";

        db.execSQL(sqlCommand);
    }

    public boolean addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_person_status, person.getStatus());
        contentValues.put(column_person_name, person.getName().trim());
        contentValues.put(column_person_amount, person.getAmount());

        long rowInserted = db.insert(table_persons, null, contentValues);

        db.close();

        return rowInserted != -1;
    }

    public ArrayList<Person> getPersonList(int status) {
        ArrayList<Person> personArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        sqlCommand = "select * from " + table_persons + " where "
                + column_person_status + " = '" + status + "'";

        Cursor cursor = db.rawQuery(sqlCommand, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(
                        cursor.getInt(cursor.getColumnIndex(column_person_status)),
                        cursor.getString(cursor.getColumnIndex(column_person_name)),
                        cursor.getInt(cursor.getColumnIndex(column_person_amount)));

                personArrayList.add(person);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return personArrayList;
    }

    public boolean addEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_entry_status, entry.getStatus());
        contentValues.put(column_entry_person, entry.getPerson());
        contentValues.put(column_entry_amount, entry.getAmount());
        contentValues.put(column_entry_comment, entry.getComment().trim());

        long rowInserted = db.insert(table_entries, null, contentValues);

        db.close();

        return rowInserted != -1;
    }

    public boolean addEntryList(ArrayList<Entry> entryArrayList) {
        for (int i = 0; i < entryArrayList.size(); i++) {
            if (!addEntry(entryArrayList.get(i)))
                return false;
        }

        return true;
    }

    public boolean deleteEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause
                = column_entry_status + " = '" + entry.getStatus() + "' and "
                + column_entry_person + " = '" + entry.getPerson() + "' and "
                + column_entry_amount + " = '" + entry.getAmount() + "' and "
                + column_entry_comment + " = '" + entry.getComment().trim() + "'";

        int rowsAffected = db.delete(table_entries, whereClause, null);

        db.close();

        return rowsAffected != 0;
    }

    public boolean deleteEntryList(ArrayList<Entry> entryArrayList) {
        for (int i = 0; i < entryArrayList.size(); i++) {
            if (!deleteEntry(entryArrayList.get(i)))
                return false;
        }

        return true;
    }

    public boolean updateEntry(Entry oldEntry, Entry newEntry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_entry_status, newEntry.getStatus());
        contentValues.put(column_entry_person, newEntry.getPerson());
        contentValues.put(column_entry_amount, newEntry.getAmount());
        contentValues.put(column_entry_comment, newEntry.getComment().trim());

        String whereClause
                = column_entry_status + " = '" + oldEntry.getStatus() + "' and "
                + column_entry_person + " = '" + oldEntry.getPerson() + "' and "
                + column_entry_amount + " = '" + oldEntry.getAmount() + "' and "
                + column_entry_comment + " = '" + oldEntry.getComment().trim() + "'";

        int rowsAffected = db.update(table_entries, contentValues, whereClause, null);

        return rowsAffected != 0;
    }

    public ArrayList<Entry> getEntryList() {
        ArrayList<Entry> entryArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        sqlCommand = "select * from " + table_entries;
        Cursor cursor = db.rawQuery(sqlCommand, null);

        if (cursor.moveToFirst()) {
            do {
                Entry entry = new Entry(
                        cursor.getInt(cursor.getColumnIndex(column_entry_status)),
                        cursor.getInt(cursor.getColumnIndex(column_entry_person)),
                        cursor.getInt(cursor.getColumnIndex(column_entry_amount)),
                        cursor.getString(cursor.getColumnIndex(column_entry_comment)));

                entryArrayList.add(entry);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return entryArrayList;
    }

    public static String getPersonsTableName() {
        return table_persons;
    }

    public static String getEntriesTableName() {
        return table_entries;
    }
}
