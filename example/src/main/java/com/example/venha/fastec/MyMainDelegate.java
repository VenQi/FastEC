package com.example.venha.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.latte.core.delegates.LatteDelegate;

public class MyMainDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
