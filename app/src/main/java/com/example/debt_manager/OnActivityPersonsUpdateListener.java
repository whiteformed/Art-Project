package com.example.debt_manager;

public interface OnActivityPersonsUpdateListener {
    void onAddPerson(Person person);

    void onUpdPerson(Person person);

    void onDelPerson(int personID);
}
