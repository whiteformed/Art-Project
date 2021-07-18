package com.example.art_project;

public interface OnActivityPersonsUpdateListener {
    void onAddPerson(Person person);

    void onUpdPerson(Person person);

    void onDelPerson(int personID);
}
