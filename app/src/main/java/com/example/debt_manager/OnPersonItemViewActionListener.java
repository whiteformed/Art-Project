package com.example.debt_manager;

public interface OnPersonItemViewActionListener {
    void onPersonItemViewClick(int personID);
    void onPersonItemViewOptionEditClick(Person person);
    void onPersonItemViewOptionDeleteClick(int personID);
}
