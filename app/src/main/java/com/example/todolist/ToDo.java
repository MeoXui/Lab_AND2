package com.example.todolist;

import androidx.annotation.NonNull;

public class ToDo {
    int ID;
    String Title,
            Comt,
            Date,
            Type;
    int Status;

    public ToDo(int ID, String title, String comt, String date, String type, int status) {
        this.ID = ID;
        Title = title;
        Comt = comt;
        Date = date;
        Type = type;
        Status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getComt() {
        return Comt;
    }

    public void setComt(String comt) {
        Comt = comt;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return Title + '\n' +
               Comt + '\n' +
               Date;
    }
}
