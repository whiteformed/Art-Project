package com.example.art_project;

public interface OnActivityEntriesUpdateListener {
    void onAddEntry(Entry newEntry);

    void onUpdEntry(Entry updEntry);

    void onDelEntry(int entryID);

    void onUpdPerson(Person updPerson);

    void onDelPerson(int personID);
}
