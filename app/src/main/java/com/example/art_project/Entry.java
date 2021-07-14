package com.example.art_project;

public class Entry {
    private int ID = -1;        // Entry ID
    private int Status;         // 0 - Dec, 1 - Inc
    private int Person_ID;      // Person' ID
    private int Amount;         // Amount of Money
    private String Comment;     // Any comment
    private String Date;        // Entry's adding date

    Entry(Entry entry) {
        this.ID = entry.ID;
        this.Status = entry.Status;
        this.Person_ID = entry.Person_ID;
        this.Amount = entry.Amount;
        this.Comment = entry.Comment;
        this.Date = entry.Date;
    }

    Entry(int status, int personID, int amount, String comment, String date) {
        this.Status = status;
        this.Person_ID = personID;
        this.Amount = amount;
        this.Comment = comment;
        this.Date = date;
    }

    Entry(int id, int status, int personID, int amount, String comment, String date) {
        this.ID = id;
        this.Status = status;
        this.Person_ID = personID;
        this.Amount = amount;
        this.Comment = comment;
        this.Date = date;
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
        this.Status = status;
    }

    public int getPersonID() {
        return Person_ID;
    }

    public void setPersonID(int person_ID) {
        Person_ID = person_ID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
