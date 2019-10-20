package com.example.latte.core.app;

import android.content.Context;

import java.util.WeakHashMap;

//工具类 暴露出去的接口
public final class Latte {
    public static Configrator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configrator.getInstance();
    }

    private static WeakHashMap<String,Object> getConfigurations(){
        return Configrator.getInstance().getLatteConfigs();
    }
}
