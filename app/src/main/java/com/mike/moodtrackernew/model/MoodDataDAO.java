package com.mike.moodtrackernew.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface MoodDataDAO {

    @Query("SELECT * FROM mooddata WHERE id BETWEEN :currentDay AND :currentDay1")
    MoodData[] loadAllMoodFromYesterday(int currentDay, int currentDay1);

    @Query("SELECT * FROM mooddata")
    MoodData[] getAllMoodData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MoodData... moodData);
}
