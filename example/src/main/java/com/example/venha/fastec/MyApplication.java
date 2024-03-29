package com.example.venha.fastec;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.latte.core.app.Latte;
import com.example.latte.ec.FontEcMoudle;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcMoudle())
                .configure();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
