package com.example.simpletodo2.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "date")
    private int date;

    @ColumnInfo(name = "priority")
    private int priority;

    public Item(String text, int year, int month, int date, int priority) {
        this.text = text;
        this.year = year;
        this.month = month;
        this.date = date;
        this.priority = priority;
    }

    @Ignore
    public Item(int id, String text, int year, int month, int date, int priority) {
        this.id = id;
        this.text = text;
        this.year = year;
        this.month = month;
        this.date = date;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
