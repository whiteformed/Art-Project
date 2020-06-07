package com.example.art_project;

public class Entry {
    private int Status;         // 0 - Inc, 1 - Dec
    private int Person;         // Person
    private int Amount;         // Amount of Money
    private String Comment;     // Any comment

    Entry(int status, int person, int amount, String comment) {
        this.Status = status;
        this.Person = person;
        this.Amount = amount;
        this.Comment = comment;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

    public int getPerson() {
        return Person;
    }

    public void setPerson(int person) {
        Person = person;
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
}
