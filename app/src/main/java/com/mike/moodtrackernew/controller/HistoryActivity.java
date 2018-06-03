package com.mike.moodtrackernew.controller;


import android.arch.persistence.room.Room;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mike.moodtrackernew.R;
import com.mike.moodtrackernew.model.AppDataBase;
import com.mike.moodtrackernew.model.MoodData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<MoodData> mMoodDataList;
    private List<MoodData> mMoodDataList2;
    private List<View> mButtonList;
    private int mCurrentDay, mCurrentDay1, mCurrentDay2;
    private AppDataBase db;
    public static final String EMPTY = "Vide";
    ArrayList<Integer> testList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity_layout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        mButtonList = Arrays.asList(findViewById(R.id.button7), findViewById(R.id.button6), findViewById(R.id.button5), findViewById(R.id.button4), findViewById(R.id.button3), findViewById(R.id.button2), findViewById(R.id.button));

        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("D");
        mCurrentDay = Integer.parseInt(df.format(c.getTime()));
        mCurrentDay1 = mCurrentDay - 1;
        mCurrentDay2 = mCurrentDay - 7;

        //Passer en background thread
        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();
        mMoodDataList = new ArrayList<>(Arrays.asList(db.mMoodDataDAO().loadAllMoodFromYesterday(mCurrentDay2, mCurrentDay1)));
//        Collections.reverse(mMoodDataList);
//        Log.d("BDD", String.valueOf("AllMoodData: " + db.mMoodDataDAO().getAllMoodData().size()) + " mMoodDataList: " + String.valueOf(mMoodDataList.size()));

        if(!mMoodDataList.isEmpty()){

            mMoodDataList2 = new ArrayList<>(Arrays.asList(db.mMoodDataDAO().loadAllMoodFromYesterday(mCurrentDay2, mCurrentDay1)));
            Collections.reverse(mMoodDataList2);
//            updateData();
            ChangeButtonsData();}

//        Log.d("BDD", String.valueOf(db.mMoodDataDAO().getAllMoodData().size()) + " mMoodDataList: " + String.valueOf(mMoodDataList.size()));



    }

    private void updateData() {
        int first = mMoodDataList2.get(0).getId();
        int last = mCurrentDay1;

        for (int i = first; i<last; i++){
            for (int j = 0; j<last; j++){
                if(mMoodDataList2.get(j).getId() != i){
                    mMoodDataList2.add(new MoodData(j, 4, "Vide", 4));

                }
            }

        }
    }

    private void ChangeButtonsData() {
        for (int i = 0; i < mMoodDataList2.size(); i++) {
            mButtonList.get(i).setBackgroundColor(mMoodDataList2.get(i).getColor());
                Log.d("BDD", "  " + mMoodDataList2.get(i).getColor());
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mButtonList.get(i).getLayoutParams();
                params.width = (int) CalculateButtonSize(mMoodDataList2.get(i).getPosition());
                mButtonList.get(i).setLayoutParams(params);
                final int finalI = i;
                mButtonList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(),  mMoodDataList2.get(finalI).getComment(), Toast.LENGTH_LONG).show();

                    }
                });

        }
    }

    private float CalculateButtonSize(int position) {
        int[] sizeInPercent = {100, 80, 60, 40, 20};
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;

        Log.d("Test", "Screen width: " + width);
        return (width/ 100) * sizeInPercent[position];


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
