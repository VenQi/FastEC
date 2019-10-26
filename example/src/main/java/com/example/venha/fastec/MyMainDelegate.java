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
import com.example.latte.core.net.rx.RxRestClient;
import com.example.latte.core.ui.LatteLoader;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyMainDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        testRxRestClient();

    }

    private void testRxRestClient(){//测试用，没什么卵用
        RxRestClient.builder()
                .url("https://news.baidu.com/index/")
                .loader(getContext())
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                        LatteLoader.stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void testRestClient(){
        RestClient.builder()
                .url("http://news.baidu.com/index")
                .loader(getContext())
                .success(new ISucess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
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

