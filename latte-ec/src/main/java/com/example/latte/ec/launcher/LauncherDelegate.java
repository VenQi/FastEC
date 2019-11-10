package com.example.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.latte.core.app.AccountManager;
import com.example.latte.core.app.IUserChecker;
import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.ui.launcher.ILauncherListener;
import com.example.latte.core.ui.launcher.OnLauncherFinishTag;
import com.example.latte.core.utils.storage.LattePreference;
import com.example.latte.core.utils.timer.BaseTimerTask;
import com.example.latte.core.utils.timer.ITimerListener;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


public class LauncherDelegate extends LatteDelegate implements ITimerListener{
    @BindView(R2.id.tv_timer_area)
    AppCompatTextView tv_timer = null;

    private int mCount = 5;

    private Timer mTimer = null;

    private ILauncherListener mILauncherListener = null;

    @OnClick(R2.id.tv_timer_area)
    void onClickTimerView(){
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
            checkIsShowScrollLauncher();
        }
    }

    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initTimer();
    }

    //判断是否显示滑动启动页
    private void checkIsShowScrollLauncher(){
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            start(new LauncherScrollDelegate(),SINGLETASK);
        }else {
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
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
                            checkIsShowScrollLauncher();
                        }
                    }
                }
            }
        });
    }
}
