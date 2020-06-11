package com.example.art_project;

public interface OnEntryArrayListUpdateListener {
    void onAddEntry(Entry newEntry);
    void onUpdEntry(Entry updEntry);
    void onDelEntry(int entryID);
    void onDelPerson(int personID);
}
