package com.mike.moodtrackernew.controller;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mike.moodtrackernew.R;
import com.mike.moodtrackernew.model.AppDataBase;
import com.mike.moodtrackernew.model.MoodData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphicActivity extends AppCompatActivity {

    PieChart mPieChart;
    private AppDataBase db;
    List<PieEntry> mPieEntries = new ArrayList<>();
    PieDataSet mPieDataSet;
    PieData mPieData;
    List<MoodData> mMoodDataList;
    int[] mColorList;
    int[] mCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphic_activity);
        mPieChart = findViewById(R.id.pie_chart);
        mPieChart.setNoDataText("Aucun Mood enregistré pour le moment.");
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setDrawEntryLabels(false);


        mColorList = new int[] {-398257, -4658810, -1522103591, -6579301, -2212784}; /*{Yellow, Green, Blue, Grey, Red}*/
        mCount = new int[5];
        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();
        mMoodDataList = new ArrayList<>(Arrays.asList(db.mMoodDataDAO().getAllMoodData()));

        if(!mMoodDataList.isEmpty()){

        setMCount();
        for(int count : mCount){
            Log.d("Test", "mCount: " + count);
        }
        initializePieChart();
        calculatePercent();
        }

    }

    private void initializePieChart(){
        mPieDataSet = new PieDataSet(mPieEntries, "");
        mPieDataSet.setColors(mColorList);
        Log.d("Test", "Percent :" + mPieChart.isUsePercentValuesEnabled());
        mPieData = new PieData(mPieDataSet);
        mPieData.setDrawValues(false);
        mPieChart.setData(mPieData);
        mPieChart.setDrawEntryLabels(false);
        mPieChart.getLegend().setEnabled(false);
        mPieChart.getDescription().setEnabled(false);

        mPieChart.invalidate(); // refresh
    }

    private void setMCount(){
        for(int i = 0; i < mMoodDataList.size(); i++) {
            if (mMoodDataList.get(i).getColor() == mColorList[0]) {
                mCount[0]+=1;
            }else if (mMoodDataList.get(i).getColor() == mColorList[1]) {
                mCount[1]+=1;
            }else if (mMoodDataList.get(i).getColor() == mColorList[2]) {
                mCount[2]+=1;
            }else if (mMoodDataList.get(i).getColor() == mColorList[3]) {
                mCount[3]+=1;
            }else if (mMoodDataList.get(i).getColor() == mColorList[4]) {
                mCount[4]+=1;
            }
            Log.d("Test", "Color" + mMoodDataList.get(i).getColor());
        }
    }

    private void calculatePercent(){
        int totalMCount = 0;

        for (int i = 0; i<mCount.length; i++){
            totalMCount += mCount[i];
        }
        for (int i = 0; i<mCount.length; i++){
            mPieEntries.add(new PieEntry((mCount[i]*100)/totalMCount, ""));
            Log.d("Test", "mPiesEntries["+i+"]: " + mPieEntries.get(i).getValue());
        }
        Log.d("Test", "totalMCount = " + totalMCount);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
