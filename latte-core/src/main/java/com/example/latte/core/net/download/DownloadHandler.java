package com.example.latte.core.net.download;

import android.os.AsyncTask;

import com.example.latte.core.net.RestCreator;
import com.example.latte.core.net.callback.IError;
import com.example.latte.core.net.callback.IFailure;
import com.example.latte.core.net.callback.IRequest;
import com.example.latte.core.net.callback.ISucess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISucess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
//    private final RequestBody BODY;

//    //使用正在加载的图标
//    private final LoaderStyle LOADERS_STYPE;
//    private final Context CONTEXT;

    //下载相关的参数
    private final String DOWNLOAD_DIR; //下载的地址
    private final String EXTENSION;  //下载文件后缀
    private final String NAME; //下载文件名

    public DownloadHandler(String url,
                           IRequest request,
                           ISucess sucess,
                           IError error,
                           IFailure failure,
                           String downloadDir,
                           String extension,
                           String name) {
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = sucess;
        this.ERROR = error;
        this.FAILURE = failure;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public final void handleDownload(){
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }
        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //这里一定要注意判断，否则文件不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if (ERROR != null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null)
                            FAILURE.onFailure();
                    }
                });
    }
}
