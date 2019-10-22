package com.example.venha.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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

    }

    private void testRestClient(){
        RestClient.builder()
                .url("")
                .params("","")
                .success(new ISucess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                }).failure(new IFailure() {
            @Override
            public void onFailure() {

            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

            }
        }).onRequest(new IRequest() {
            @Override
            public void onRequestStart() {

            }

            @Override
            public void onRequestEnd() {

            }
        }).build();
    }
}

