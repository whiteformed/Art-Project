package com.example.art_project;

public interface OnPersonItemViewActionListener {
    void onPersonItemViewClick(int personID);
    void onPersonItemViewOptionEditClick(Person person);
    void onPersonItemViewOptionDeleteClick(int personID);
}
