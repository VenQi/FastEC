package com.example.latte.core.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

public class LauncherHolder implements Holder<Integer> {

    private AppCompatImageView appCompatImageView = null;//每一个页面都是一个imageview

    @Override
    public View createView(Context context) {
        appCompatImageView = new AppCompatImageView(context);
        return appCompatImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        appCompatImageView.setBackgroundResource(data);
    }
}
