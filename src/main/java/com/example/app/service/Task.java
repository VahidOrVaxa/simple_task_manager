package com.example.app.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task implements Comparable {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Id
    @Column(name = "ID")
    private int ID;
    @Column(name = "task_name")
    private String name;
    @Column(name = "task_description")
    private String description;
    @Column(name = "date_of_creation")
    private Date creationDate;
    @Column(name = "deadline")
    private Date deadline = null;
    @Column(name = "priority")
    private int priority = 99;

    public Task() {}

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.creationDate = new Date();
    }

    public Task(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.creationDate = new Date();
        this.priority = priority;
    }

    public Task(String name, String description, int priority, String deadline) {
        this.name = name;
        this.description = description;
        this.creationDate = new Date();
        this.priority = priority;
        try {
            this.deadline = dateFormat.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("This task has not deadline!");
        }

    }

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        int result = priority - task.priority;
        if (result != 0) {
            return result;
        } else if (deadline!=null) {
            return deadline.compareTo(task.deadline);
        } else {
            return creationDate.compareTo(task.creationDate);
        }
    }

    @Override
    public String toString() {
        return "Task ID: " + ID + "\n" +
                "Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Data of creation: " + creationDate + "\n" +
                "Deadline: " + (deadline == null ? "No deadline!" : deadline) + "\n" +
                "Priority: " + (priority == 99 ? "No priority!" : priority) + "\n" +
                "==================================\n";
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getPriority() {
        return priority;
    }

    public String getDeadline() {
        return dateFormat.format(deadline);
    }

    public String getSimpleCreationDate(){
        return dateFormat.format(creationDate);
    }
}
