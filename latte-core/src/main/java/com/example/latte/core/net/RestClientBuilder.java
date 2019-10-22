package com.example.latte.core.net;

import com.example.latte.core.net.callback.IError;
import com.example.latte.core.net.callback.IFailure;
import com.example.latte.core.net.callback.IRequest;
import com.example.latte.core.net.callback.ISucess;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private  String mUrl;
    private static final Map<String,Object> PARAMS = RestCreator.getParams();
    private  IRequest mIRequest;
    private  ISucess mISucess;
    private  IError mIError;
    private  IFailure mIFailure;
    private  RequestBody mRequestBody;
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

    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mIRequest,mISucess,mIError,mIFailure,mRequestBody);
    }
}
