package com.example.venha.fastec;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.latte.core.app.Latte;
import com.example.latte.core.net.myinterceptors.DebugInterceptor;
import com.example.latte.ec.database.DataBaseManager;
import com.example.latte.ec.icon.FontEcMoudle;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcMoudle())
                .withInterceptor(new DebugInterceptor("index",R.raw.hello))
                .configure();
        initStetho();
        DataBaseManager.getInstance().init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化 Facebook的stetho，Stetho可以将原生或者数据库抽象的展示出来
     */
    private void initStetho(){
        //初始化之后用chrome浏览器输入chrome://inspect/#devices就能查看视图了
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }
}
