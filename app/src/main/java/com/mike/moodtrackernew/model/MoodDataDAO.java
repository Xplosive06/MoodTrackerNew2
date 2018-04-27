package com.mike.moodtrackernew.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface MoodDataDAO {
    @Query("SELECT * FROM mooddata")
    List<MoodData> getAllMoodData();

    @Insert
    void insertAll(MoodData... moodData);
}
