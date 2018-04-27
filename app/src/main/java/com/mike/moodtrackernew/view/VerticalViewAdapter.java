package com.mike.moodtrackernew.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mike.moodtrackernew.R;

public class VerticalViewAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mImageIdes = new int[] {R.drawable.smiley_super_happy, R.drawable.smiley_happy, R.drawable.smiley_normal, R.drawable.smiley_disappointed, R.drawable.smiley_sad};

    public VerticalViewAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageIdes.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(mImageIdes[position]);
        container.addView(imageView, 0);
        return imageView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
