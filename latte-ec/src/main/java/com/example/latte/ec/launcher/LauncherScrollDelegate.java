package com.example.latte.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.ui.launcher.LauncherHolderCreator;
import com.example.latte.ec.R;

import java.util.ArrayList;

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener{
    private ConvenientBanner<Integer> mConvenientBanner = null; //保存轮播图启动页面的图片资源,此对象就是轮播启动图的布局
    private  static  final ArrayList<Integer> INTEGERS = new ArrayList<>();

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
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initBanner();
    }


    @Override
    public void onItemClick(int position) {

    }
}
