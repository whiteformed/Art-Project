package com.example.art_project;

import java.util.Date;

public class Debt {
    private int Status;         // 0 - My Debt, 1 - Their Debt
    private String ID;          // Persons unique identifier
    private String Name;        // Person's name
    private String Amount;      // Amount of Money
    //private String Description; // Any commentary on debt
    //private Date DateInfo;      // Date information (Optional)

    Debt (int status, String id, String name, String amount)
    {
        this.Status = status;
        this.ID = id;
        this.Name = name;
        this.Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }
}
