package com.example.latte.core.net;

import com.example.latte.core.net.callback.IError;
import com.example.latte.core.net.callback.IFailure;
import com.example.latte.core.net.callback.IRequest;
import com.example.latte.core.net.callback.ISucess;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;

/**
 *  RestClient 每次被builder builde的时候会生成一个全新的实例，里面的参数是一次构建不允许
 *  再更改的
 */
public class RestClient {

    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISucess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;

    public RestClient(String url,
                      Map<String, Object> params,
                      IRequest request, ISucess sucess,
                      IError error,
                      IFailure failure,
                      RequestBody body) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = sucess;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
    }

    public static RestClientBuilder builder(){
        return  new RestClientBuilder();
    }

}
