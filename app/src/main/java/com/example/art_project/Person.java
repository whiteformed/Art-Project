package com.example.art_project;

public class Person {
    private String Status;      // 0 - My Debt, 1 - Their Debt
    private String Name;        // Person's name
    private String Amount;      // Amount of Money

    Person (String status, String name, String amount) {
        this.Status = status;
        this.Name = name;
        this.Amount = amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
