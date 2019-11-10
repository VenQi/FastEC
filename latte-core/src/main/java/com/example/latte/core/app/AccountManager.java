package com.example.latte.core.app;

import com.example.latte.core.utils.storage.LattePreference;

public class AccountManager {
    private enum SignTag{
        SIGN_TAG
    }

    /**
     * 保存用户登录状态，用户登陆以后调用
     * @param state
     */
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }
    private static boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
