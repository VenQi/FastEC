package com.example.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.core.app.AccountManager;
import com.example.latte.ec.database.DataBaseManager;
import com.example.latte.ec.database.UserProfile;


public class SignHandler {
    public static void onSignIn(String response,ISignListener iSignListener){
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DataBaseManager.getInstance().getmDao().insert(profile);//数据持久化到数据库

        //已经注册成功并成功登录
        AccountManager.setSignState(true);
        iSignListener.onSignInSuccess();
    }

    public static void onSignUp(String response,ISignListener iSignListener){
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DataBaseManager.getInstance().getmDao().insert(profile);//数据持久化到数据库

        //已经注册成功并成功登录
        AccountManager.setSignState(true);//许多应用都是注册完就直接登录的，如果逻辑上不是直接登录，那就不在这里设置
        iSignListener.onSignUpSuccess();
    }
    public static void testInsert(long userId,String name,String avatar,String gender,String address,ISignListener iSignListener){
        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DataBaseManager.getInstance().getmDao().insert(profile);//数据持久化到数据库

        //已经注册成功并成功登录
        AccountManager.setSignState(true);
        iSignListener.onSignUpSuccess();
    }
}
