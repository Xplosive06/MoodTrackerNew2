package com.mike.moodtrackernew.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MoodData.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MoodDataDAO mMoodDataDAO();
}
