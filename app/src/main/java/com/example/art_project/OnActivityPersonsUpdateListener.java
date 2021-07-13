package com.example.art_project;

public interface OnActivityPersonsUpdateListener {
    void onAddPerson(Person newPerson);

    void onUpdPerson(Person updPerson);

    void onDelPerson(int personID);
}
