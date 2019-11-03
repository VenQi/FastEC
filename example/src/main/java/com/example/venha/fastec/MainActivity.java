package com.example.venha.fastec;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.latte.core.activities.ProxyActivity;
import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.ec.launcher.LauncherDelegate;
import com.example.latte.ec.launcher.LauncherScrollDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherScrollDelegate();
    }
}
