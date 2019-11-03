package com.example.latte.ec.launcher;

import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.utils.timer.BaseTimerTask;
import com.example.latte.core.utils.timer.ITimerListener;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;

import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


public class LauncherDelegate extends LatteDelegate implements ITimerListener{
    @BindView(R2.id.tv_timer_area)
    AppCompatTextView tv_timer = null;

    private int mCount = 5;

    private Timer mTimer = null;

    @OnClick(R2.id.tv_timer_area)
    void onClickTimerView(){

    }

    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTimer() {

        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tv_timer != null){
                    tv_timer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount -- ;
                    if (mCount < 0){
                        if (mTimer != null){
                            mTimer.cancel();
                            mTimer = null;
                        }
                    }
                }
            }
        });
    }
}
