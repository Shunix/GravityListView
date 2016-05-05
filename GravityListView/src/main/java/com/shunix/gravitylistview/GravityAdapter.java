package com.shunix.gravitylistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author shunix
 * @since 2016/5/5
 */
public abstract class GravityAdapter<T> extends BaseAdapter {
    private final static int DELTA_Y = 500;
    private final static int DURATION = 600;
    protected LayoutInflater mInflater;
    protected List<T> mData;
    private int mPreviousPosition = -1;

    public GravityAdapter(Context context, List<T> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {
            return null;
        }
        if (convertView == null) {
            convertView = mInflater.inflate(getLayoutId(), null);
        }
        fillData(getItem(position), convertView);
        int deltaY = 0;
        if (position > mPreviousPosition) {
            // Scroll Up
            deltaY = DELTA_Y;
        } else {
            // Scroll Down
            deltaY = DELTA_Y * -1;
        }
        Animation translateAnimation = new TranslateAnimation(0, 0, deltaY, 0);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        convertView.startAnimation(translateAnimation);
        mPreviousPosition = position;
        return convertView;
    }

    /**
     * This method should return the layout id of each convert view.
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * Init views with corresponding data.
     * @param data
     * @param convertView
     */
    protected abstract void fillData(T data, View convertView);
}
