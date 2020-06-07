package com.example.art_project;

public class Person {
    private int Status;      // 0 - My Entry, 1 - Their Entry
    private String Name;     // Person's name
    private int Amount;      // Amount of Money

    Person(int status, String name, int amount) {
        this.Status = status;
        this.Name = name;
        this.Amount = amount;
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

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
