package com.example.latte.core.net.rx;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 这个类结合了retrofit2 实现了网络请求的响应式编程
 */
public interface RxRestService {

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> params);

    /**
     * 当使用元数据的时候，不加@FormUrlEncoded,同理还有putRaw
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> params);

    /**
     * 当使用元数据的时候，不加@FormUrlEncoded,同理还有putRaw
     *
     * @param url
     * @param body
     * @return
     */
    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    @Streaming //避免一次性在内存写入大文件造成oom，需要在异步线程
    @GET    //download是GET的变种，算是特例
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);


    @Multipart
    @POST  //UPLOAD是post的变种，算是特例
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);
}
