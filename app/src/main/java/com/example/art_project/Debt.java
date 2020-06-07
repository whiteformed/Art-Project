package com.example.art_project;

import java.util.Date;

public class Debt {
    private String Status;      // 0 - Inc, 1 - Dec
    private String Name;        // Person's name
    private String Amount;      // Amount of Money
    private String Description; // Any commentary on debt

    Debt (String name, String status, String amount, String description)
    {
        this.Status = status;
        this.Name = name;
        this.Amount = amount;
        this.Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
