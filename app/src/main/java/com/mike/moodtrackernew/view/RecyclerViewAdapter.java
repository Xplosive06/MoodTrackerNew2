package com.mike.moodtrackernew.view;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mike.moodtrackernew.R;
import com.mike.moodtrackernew.model.MoodData;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TestViewHolder> {

    private List<MoodData> mMoodDataList;
    private String testComment;

    public class TestViewHolder extends RecyclerView.ViewHolder{
        public TextView mDateView;
        public TextView mTextView1;

        public TestViewHolder(final View itemView) {
            super(itemView);
            mDateView = itemView.findViewById(R.id.dateView);
            mTextView1 = itemView.findViewById(R.id.textView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    testComment = "Rien d'enregistré pour ce jour!, position: " + pos;
                    Toast.makeText(v.getContext(), testComment, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public RecyclerViewAdapter(List<MoodData> moodDataList){
        mMoodDataList = moodDataList;

    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item,parent, false );

        TestViewHolder testViewHolder = new TestViewHolder(v);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        MoodData currentItem = mMoodDataList.get(position);

        holder.mDateView.setText(currentItem.getDate());
        holder.mTextView1.setText(currentItem.getComment());

    }

    @Override
    public int getItemCount() {
        return mMoodDataList.size();
    }


}
