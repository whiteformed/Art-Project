package com.example.debt_manager;

public interface OnActivityEntriesUpdateListener {
    void onAddEntry(Entry entry);

    void onUpdEntry(Entry entry);

    void onDelEntry(int entryID);

    void onUpdPerson(Person person);

    void onDelPerson(int personID);
}
