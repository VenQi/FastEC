package com.example.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.latte.core.app.AccountManager;
import com.example.latte.core.app.IUserChecker;
import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.ui.launcher.ILauncherListener;
import com.example.latte.core.ui.launcher.LauncherHolderCreator;
import com.example.latte.core.ui.launcher.OnLauncherFinishTag;
import com.example.latte.core.utils.storage.LattePreference;
import com.example.latte.ec.R;

import java.util.ArrayList;

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener{
    private ConvenientBanner<Integer> mConvenientBanner = null; //保存轮播图启动页面的图片资源,此对象就是轮播启动图的布局
    private  static  final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private ILauncherListener mILauncherListener = null;

    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_1);
        INTEGERS.add(R.mipmap.launcher_2);
        INTEGERS.add(R.mipmap.launcher_3);
        INTEGERS.add(R.mipmap.launcher_4);
        INTEGERS.add(R.mipmap.launcher_5);

        /**
         *  注意，在代码中涉及图片资源的处理
         *  1.如果是在业务代码中，那么尽量使用图片资源来做，这样方便迭代
         *  2.如果是在框架代码里，那么尽量使用代码解决，这样可以减少代码入侵（如使用解压替换资源图片）
         */
        mConvenientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focusl})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }
    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initBanner();
    }


    @Override
    public void onItemClick(int position) {

        //如果点击最后一个图片
        if (position == INTEGERS.size() -1 ){
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            // 检查用户是否登录的逻辑
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
}
