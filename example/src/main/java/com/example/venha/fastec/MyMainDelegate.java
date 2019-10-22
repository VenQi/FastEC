package com.example.venha.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.net.RestClient;
import com.example.latte.core.net.callback.IError;
import com.example.latte.core.net.callback.IFailure;
import com.example.latte.core.net.callback.IRequest;
import com.example.latte.core.net.callback.ISucess;

public class MyMainDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        testRestClient();

    }

    private void testRestClient(){
        RestClient.builder()
                .url("http://news.baidu.com/")
                .loader(getContext())
                .success(new ISucess() {
                    @Override
                    public void onSuccess(String response) {
//                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        Log.e("TEST",response);
                    }
                }).failure(new IFailure() {
            @Override
            public void onFailure() {
                Log.e("TEST","onFailure");
            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Log.e("TEST","error"+code + msg);
            }
        }).onRequest(new IRequest() {
            @Override
            public void onRequestStart() {
                Log.e("TEST","onRequestStart");
            }

            @Override
            public void onRequestEnd() {
                Log.e("TEST","onRequestEnd");
            }
        }).build()
        .get();
    }
}

