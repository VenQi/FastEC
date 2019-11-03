package com.example.latte.core.net.rx;

import android.content.Context;

import com.example.latte.core.net.RestCreator;
import com.example.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxRestClientBuilder {
    private  String mUrl = null;
    private static final Map<String,Object> PARAMS = RestCreator.getParams();

    private  RequestBody mRequestBody = null;
    private File mFile = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;

    RxRestClientBuilder(){
    }
    /**
     *    当觉得方法完善了的时候，不让别人改此方法的时候，可把方法设置为final，
     *    这样做的好处也可以让编译器进行优化（TODO final是怎么被编译器优化的）
     */
    public final RxRestClientBuilder url(String url){
        this.mUrl = url;
        return  this;
    }

    public final RxRestClientBuilder params(Map<String,Object> params){
        PARAMS.putAll(params);
        return  this;
    }

    public final RxRestClientBuilder params(String key, Object value){
        PARAMS.put(key,value);
        return  this;
    }

    public final RxRestClientBuilder raw(String raw){
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset-UTF-8"),raw);
        return  this;
    }

    public final RxRestClientBuilder file(File file){
        this.mFile = file;
        return  this;
    }
    public final RxRestClientBuilder file(String path){
        this.mFile = new File(path);
        return  this;
    }
    /**
     * 定义loadingview的样式
     * @param context
     * @param loaderStyle
     * @return
     */
    public final RxRestClientBuilder loader(Context context, LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return  this;
    }
    public final RxRestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return  this;
    }
    public final RxRestClient build(){
        return new RxRestClient(mUrl,PARAMS,mRequestBody,mFile,mContext,mLoaderStyle);
    }
}
