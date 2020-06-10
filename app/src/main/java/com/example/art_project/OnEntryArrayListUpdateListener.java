package com.example.art_project;

public interface OnEntryArrayListUpdateListener {
    void onAddEntry(Entry entry);
    void onUpdEntry(int oldEntryID, Entry newEntry);
    void onDelEntry(int entryID);
    void onDelPerson();
}
