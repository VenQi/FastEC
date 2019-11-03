package com.example.latte.core.net;

import android.content.Context;

import com.example.latte.core.net.callback.IError;
import com.example.latte.core.net.callback.IFailure;
import com.example.latte.core.net.callback.IRequest;
import com.example.latte.core.net.callback.ISucess;
import com.example.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private  String mUrl = null;
    private static final Map<String,Object> PARAMS = RestCreator.getParams();
    private  IRequest mIRequest = null;
    private  ISucess mISucess = null;
    private  IError mIError = null;
    private  IFailure mIFailure = null;
    private  RequestBody mRequestBody = null;
    private File mFile = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;

    //下载相关的参数
    private String mDownloadDir = null; //下载的地址
    private String mExtension = null;  //下载扩展参数
    private String mName = null; //下载文件的名字
    RestClientBuilder(){
    }
    /**
     *    当觉得方法完善了的时候，不让别人改此方法的时候，可把方法设置为final，
     *    这样做的好处也可以让编译器进行优化（TODO final是怎么被编译器优化的）
     */
    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return  this;
    }

    public final RestClientBuilder params(Map<String,Object> params){
        PARAMS.putAll(params);
        return  this;
    }

    public final RestClientBuilder params(String key,Object value){
        PARAMS.put(key,value);
        return  this;
    }

    public final RestClientBuilder raw(String raw){
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset-UTF-8"),raw);
        return  this;
    }

    public final RestClientBuilder success(ISucess sucess){
        this.mISucess = sucess;
        return  this;
    }

    public final RestClientBuilder error(IError merror){
        this.mIError = merror;
        return  this;
    }

    public final RestClientBuilder failure(IFailure failure){
        this.mIFailure = failure;
        return  this;
    }

    public final RestClientBuilder onRequest(IRequest request){
        this.mIRequest = request;
        return  this;
    }

    public final RestClientBuilder file(File file){
        this.mFile = file;
        return  this;
    }
    public final RestClientBuilder file(String path){
        this.mFile = new File(path);
        return  this;
    }

    public final RestClientBuilder name(String name){
        this.mName = name;
        return  this;
    }
    public final RestClientBuilder downloadDir(String mDownloadDir){
        this.mDownloadDir = mDownloadDir;
        return  this;
    }
    public final RestClientBuilder extension(String extension){
        this.mExtension = extension;
        return  this;
    }

    /**
     * 定义loadingview的样式
     * @param context
     * @param loaderStyle
     * @return
     */
    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return  this;
    }
    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return  this;
    }
    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mIRequest,mISucess,mIError,mIFailure,mRequestBody,mFile,mName,mDownloadDir,mExtension,mContext,mLoaderStyle);
    }
}
