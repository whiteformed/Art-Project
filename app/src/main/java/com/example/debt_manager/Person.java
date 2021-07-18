package com.example.debt_manager;

public class Person {
    private int ID;          // Person ID
    private int Status;      // 0 - My Debt, 1 - Their Debt
    private String Name;     // Person's name

    Person(Person person) {
        this.ID = person.getID();
        this.Status = person.getStatus();
        this.Name = person.getName();
    }

    Person(int status, String name) {
        this.Status = status;
        this.Name = name;
    }

    Person(int id, int status, String name) {
        this.ID = id;
        this.Status = status;
        this.Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
