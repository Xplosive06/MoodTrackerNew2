package com.mike.moodtrackernew.controller;

import android.annotation.SuppressLint;
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

    private VerticalViewPager mViewPager;
    private EditText mEditTextComment;
    private String mComment;

    private int[] mColorList;
    private int mCurrentColor;
    private int mCurrentPosition;
    private int mCurrentDay;
    protected List<MoodData> mMoodDataArrayList;
    private AppDataBase db;
    private static final String COMMENT_TITLE = "Humeur actuelle enregistrée.\nVotre commentaire pour aujourd'hui : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorList = new int[]{R.color.banana_yellow, R.color.light_sage, R.color.cornflower_blue_65, R.color.warm_grey, R.color.faded_red};
        ImageButton buttonHistory = findViewById(R.id.history);
        ImageButton buttonGraphic = findViewById(R.id.graphic);

        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("D");
        mCurrentDay = Integer.parseInt(df.format(c.getTime()));
        mMoodDataArrayList = new ArrayList<>();


        createVerticalViewPager();

        buttonGraphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphicActivityIntent = new Intent(MainActivity.this, GraphicActivity.class);

                Log.d("Test", "Intent on GraphicActivity!");

                startActivity(graphicActivityIntent);
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
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
        @SuppressLint("InflateParams") View alertLayout = inflater.inflate(R.layout.pop_up_comment, null);
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
                db.mMoodDataDAO().insertAll(new MoodData(mCurrentDay,mCurrentPosition, mComment, mCurrentColor));
                Log.d("Test", "Today is: " + mCurrentDay + "Current color is: " + mCurrentColor + "mCurrent position: " + mCurrentPosition);
                Toast.makeText(getBaseContext(), COMMENT_TITLE + mComment, Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.light_blue));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.light_blue));
    }


    private void createVerticalViewPager() {

        VerticalViewAdapter adapter = new VerticalViewAdapter(this);

        mViewPager = findViewById(R.id.verticalViewPager);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setBackgroundColor(getResources().getColor(R.color.light_sage));
        mCurrentColor = getResources().getColor(mColorList[1]);

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

