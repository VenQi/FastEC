package com.example.latte.core.net;

import android.content.Context;

import com.example.latte.core.net.callback.IError;
import com.example.latte.core.net.callback.IFailure;
import com.example.latte.core.net.callback.IRequest;
import com.example.latte.core.net.callback.ISucess;
import com.example.latte.core.net.callback.ResquestCallBacks;
import com.example.latte.core.net.download.DownloadHandler;
import com.example.latte.core.ui.loader.LatteLoader;
import com.example.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

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


    //使用正在加载的图标
    private final LoaderStyle LOADERS_STYPE;
    private final Context CONTEXT;

    //上传的file
    private final File FILE;
    //下载相关的参数
    private final String DOWNLOAD_DIR; //下载的地址
    private final String EXTENSION;  //下载扩展参数
    private final String NAME; //下载文件名


    public RestClient(String url,
                      Map<String, Object> params,
                      IRequest request, ISucess sucess,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                      File file,
                      String name,
                      String download_dir,
                      String extension,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = sucess;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.FILE = file;
        this.NAME = name;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.CONTEXT = context;
        this.LOADERS_STYPE = loaderStyle;
    }

    public static RestClientBuilder builder(){
        return  new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null)
            REQUEST.onRequestStart();

        if (LOADERS_STYPE != null){
            LatteLoader.showLoading(CONTEXT,LOADERS_STYPE);
        }
        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                //上传文件
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call =RestCreator.getRestService().upload(URL,body);
                break;
                default:
                    break;
        }
        if (call != null){
            call.enqueue(getRequestCallback());//此方法为异步，如果要再主线程/同步执行，需要使用call.execute()
        }
    }

    private Callback<String> getRequestCallback(){
        return new ResquestCallBacks(REQUEST,SUCCESS,ERROR,FAILURE,LOADERS_STYPE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void  post(){
        if (BODY == null) {
            request(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("Params must be null when using post_raw！");
            }
            request(HttpMethod.POST_RAW);
        }
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void put(){
        if (BODY == null) {
            request(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("Params must be null when using put_raw！");
            }
            request(HttpMethod.PUT_RAW);
        }

    }

    public final void download(){
        new DownloadHandler(URL,REQUEST,SUCCESS,ERROR,FAILURE,DOWNLOAD_DIR,EXTENSION, NAME)
                .handleDownload();
    }

}
