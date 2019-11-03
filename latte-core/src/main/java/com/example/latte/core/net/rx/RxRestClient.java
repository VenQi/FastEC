package com.example.latte.core.net.rx;

import android.content.Context;

import com.example.latte.core.net.HttpMethod;
import com.example.latte.core.net.RestCreator;
import com.example.latte.core.ui.loader.LatteLoader;
import com.example.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 *  RestClient 每次被builder builde的时候会生成一个全新的实例，里面的参数是一次构建不允许
 *  再更改的
 */
public class RxRestClient {

    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;


    //使用正在加载的图标
    private final LoaderStyle LOADERS_STYPE;
    private final Context CONTEXT;

    //上传的file
    private final File FILE;
    //下载相关的参数
//    private final String DOWNLOAD_DIR; //下载的地址
//    private final String EXTENSION;  //下载扩展参数
//    private final String NAME; //下载文件名


    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        File file,
                        Context context,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADERS_STYPE = loaderStyle;
    }

    public static RxRestClientBuilder builder(){
        return  new RxRestClientBuilder();
    }

    private Observable<String > request(HttpMethod method){
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;


        if (LOADERS_STYPE != null){
            LatteLoader.showLoading(CONTEXT,LOADERS_STYPE);
        }
        switch (method){
            case GET:
                observable = service.get(URL,PARAMS);
                break;
            case POST:
                observable = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL,BODY);
                break;
            case PUT:
                observable = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                observable = service.postRaw(URL,BODY);
                break;
            case DELETE:
                observable = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                //上传文件
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                observable =service.upload(URL,body);
                break;
                default:
                    break;
        }
        return observable;
    }


    public final Observable<String >  get(){
        return request(HttpMethod.GET);
    }

    public final Observable<String >   post(){
        if (BODY == null) {
            return request(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("Params must be null when using post_raw！");
            }
            return request(HttpMethod.POST_RAW);
        }
    }
    public final Observable<String >  delete(){
        return request(HttpMethod.DELETE);
    }
    public final Observable<String >  put(){
        if (BODY == null) {
            return request(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("Params must be null when using put_raw！");
            }
            return request(HttpMethod.PUT_RAW);
        }

    }

    public final Observable<ResponseBody>  download(){
        return RestCreator.getRxRestService().download(URL,PARAMS);
    }

}
