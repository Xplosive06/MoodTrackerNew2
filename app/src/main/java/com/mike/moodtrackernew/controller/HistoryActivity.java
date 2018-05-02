package com.mike.moodtrackernew.controller;

import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
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
        Log.d("BDD", String.valueOf("AllMoodData: " + db.mMoodDataDAO().getAllMoodData().size()) + " mMoodDataList: " + String.valueOf(mMoodDataList.size()));

        if(!mMoodDataList.isEmpty()){
            updateData();
        mMoodDataList2 = new ArrayList<>(Arrays.asList(db.mMoodDataDAO().loadAllMoodFromYesterday(mCurrentDay2, mCurrentDay1)));
        Collections.reverse(mMoodDataList2);
        ChangeButtonsData();}

//        Log.d("BDD", String.valueOf(db.mMoodDataDAO().getAllMoodData().size()) + " mMoodDataList: " + String.valueOf(mMoodDataList.size()));



    }

    private void updateData() {
        testList = new ArrayList<>();
        for(int i = 0; i<mMoodDataList.size(); i++){
            testList.add(mMoodDataList.get(i).getId());
        }
        int first = testList.get(0);
        int last = testList.get(testList.size()-1);
        for(int i=first+1; i<last; i++){
            if(!testList.contains(i))
                db.mMoodDataDAO().insertAll(new MoodData(i,333, EMPTY, R.color.colorPrimaryDark));
        }
    }

    private void ChangeButtonsData() {
        for (int i = 0; i < mMoodDataList2.size(); i++) {
            if (mMoodDataList2.isEmpty()/*|| mMoodDataList.get(i).getId()-1 != mMoodDataList.get(i+1).getId()*/)  {
                mMoodDataList2.add(/*i+1, */new MoodData(mCurrentDay, 69, "Rien d'enregistré pour ce jour.", R.color.colorPrimaryDark));
                Log.d("BDD", "  " + mMoodDataList2.get(i).getId());
            }else{mButtonList.get(i).setBackgroundColor(mMoodDataList2.get(i).getColor());
                Log.d("BDD", "  " + mMoodDataList2.get(i).getColor());
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mButtonList.get(i).getLayoutParams();
                params.width = Math.round(mMoodDataList2.get(i).getSize());
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
    }

}
