package com.mike.moodtrackernew.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MoodData {
    @PrimaryKey()
    private int id;

    @ColumnInfo(name = "size")
    private int size;

    @ColumnInfo(name = "comment")
    private String comment;

    @ColumnInfo(name = "color")
    private int color;

    public MoodData(int id, int size, String comment, int color) {
        this.id = id;
        this.size = size;
        this.comment = comment;
        this.color = color;
    }

    public MoodData(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
