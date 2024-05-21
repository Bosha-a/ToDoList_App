package com.example.todolist;

import java.util.Date;

public class MyTask {

    int ID;
    String task;
    Date deadline;
    String details;

    public MyTask(String task, Date deadline, String details) {
        this.task = task;
        this.deadline = deadline;
        this.details = details;
    }

    public MyTask(String task, java.sql.Date deadline, String details) {
        this.task = task;
        this.deadline = deadline;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getTask() {
        return task;
    }

    public int getID() {
        return ID;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}