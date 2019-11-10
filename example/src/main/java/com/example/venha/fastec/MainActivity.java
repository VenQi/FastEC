package com.example.venha.fastec;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.latte.core.activities.ProxyActivity;
import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.ui.launcher.ILauncherListener;
import com.example.latte.core.ui.launcher.OnLauncherFinishTag;
import com.example.latte.ec.launcher.LauncherDelegate;
import com.example.latte.ec.launcher.LauncherScrollDelegate;
import com.example.latte.ec.sign.ISignListener;
import com.example.latte.ec.sign.SignInDelegate;
import com.example.latte.ec.sign.SignUpDelegate;

public class MainActivity extends ProxyActivity implements
        ISignListener,ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                Toast.makeText(this,"启动结束，用户登录",Toast.LENGTH_LONG).show();
                break;
            case NOT_SIGNED:
                Toast.makeText(this,"启动结束，用户没有登录",Toast.LENGTH_LONG).show();
                startWithPop(new SignInDelegate());
                break;
                default:
                    break;
        }
    }
}
