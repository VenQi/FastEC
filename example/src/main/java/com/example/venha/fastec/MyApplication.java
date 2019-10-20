package com.example.venha.fastec;

import android.app.Application;

import com.example.latte.core.app.Latte;
import com.example.latte.ec.FontEcMoudle;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1")
                .withIcon(new FontEcMoudle())
                .configure();
    }
}
