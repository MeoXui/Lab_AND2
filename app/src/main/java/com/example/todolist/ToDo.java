package com.example.todolist;

import androidx.annotation.NonNull;

public class ToDo {
    public int ID;
    public String Title,
            Comt,
            Date,
            Type;
    public int Status;

    public ToDo(int ID, String title, String comt, String date, String type, int status) {
        this.ID = ID;
        Title = title;
        Comt = comt;
        Date = date;
        Type = type;
        Status = status;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setComt(String comt) {
        Comt = comt;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setType(String type) {
        Type = type;
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
