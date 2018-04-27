package com.mike.moodtrackernew.controller;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mike.moodtrackernew.R;
import com.mike.moodtrackernew.model.AppDataBase;
import com.mike.moodtrackernew.model.MoodData;
import com.mike.moodtrackernew.view.RecyclerViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MoodData> mMoodDataList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity_layout);

        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd");
        int mCurrentDay = Integer.parseInt(df.format(c.getTime()));
        int mCurrentDay1 = mCurrentDay +6;

        //Passer en background thread
        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();
        mMoodDataList = Arrays.asList(db.mMoodDataDAO().loadAllMoodFromYesterday(mCurrentDay, mCurrentDay1));
//        mMoodDataList.add(new MoodData(mCurrentDay, "TestComment", 123456));
        for (MoodData str : mMoodDataList){
            Log.d("BDD", "  " + str.getId() );
        }


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter2 = new RecyclerViewAdapter(mMoodDataList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter2);

    }
}