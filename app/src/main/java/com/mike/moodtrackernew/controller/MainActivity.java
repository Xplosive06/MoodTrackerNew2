package com.mike.moodtrackernew.controller;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mike.moodtrackernew.R;
import com.mike.moodtrackernew.model.AppDataBase;
import com.mike.moodtrackernew.model.MoodData;
import com.mike.moodtrackernew.view.VerticalViewAdapter;
import com.mike.moodtrackernew.view.VerticalViewPager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    VerticalViewPager mViewPager;
    VerticalViewAdapter mAdapter;
    EditText mEditTextComment;
    String mComment;

    ImageButton mButtonAddAComment;
    ImageButton mButtonHistory;
    int[] mColorList;
    int mCurrentColor;
    int mCurrentPosition;
    int mCurrentDay;
    List<MoodData> mMoodDataArrayList;
    AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorList = new int[]{R.color.banana_yellow, R.color.light_sage, R.color.cornflower_blue_65, R.color.warm_grey, R.color.faded_red};
        mButtonAddAComment = findViewById(R.id.addAComment);
        mButtonHistory = findViewById(R.id.history);

        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("D");
        mCurrentDay = Integer.parseInt(df.format(c.getTime()));
        mMoodDataArrayList = new ArrayList<>();


        createVerticalViewPager();

        mButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);

                Log.d("Test", "Intent on HistoryActivity!");

                startActivity(historyActivityIntent);
            }
        });

    }

    public void buttonClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.pop_up_comment, null);
        mEditTextComment = alertLayout.findViewById(R.id.comment);

        //Passer en background thread

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // allow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(true);
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Commentaire annulé", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mComment = mEditTextComment.getText().toString();
                float buttonSize = buttonSize(mCurrentPosition);
                db.mMoodDataDAO().insertAll(new MoodData(mCurrentDay,buttonSize, mComment, mCurrentColor));
                Log.d("Test", "Today is: " + mCurrentDay + "Current color is: " + mCurrentColor + "mCurrent position: " + mCurrentPosition);
                Toast.makeText(getBaseContext(), "Humeur actuelle enregistrée.\nVotre commentaire pour aujourd'hui : " + mComment, Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.light_blue));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.light_blue));
    }


    private void createVerticalViewPager() {

        mAdapter = new VerticalViewAdapter(this);

        mViewPager = findViewById(R.id.verticalViewPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setBackgroundColor(getResources().getColor(R.color.light_sage));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Change the mViewPager's background depending to his position
                mViewPager.setBackgroundColor(getResources().getColor(mColorList[position]));
                Log.d("Test", "Position is:" + position);
                mCurrentColor = getResources().getColor(mColorList[position]);
                mCurrentPosition = position;
                Log.d("Test", "currentColor is: " + mCurrentColor);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private float buttonSize(int currentPosition) {
        currentPosition = mCurrentPosition+1;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        Log.d("Test", "Screen width: " + width);
        return width/currentPosition;


    }

}

