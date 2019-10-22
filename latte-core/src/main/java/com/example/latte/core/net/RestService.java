package com.example.latte.core.net;



import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
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

public interface RestService {

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String  url, @FieldMap Map<String,Object> params);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String  url, @FieldMap Map<String,Object> params);

    @DELETE
    Call<String> delete(@Url String  url, @QueryMap Map<String,Object> params);

    @Streaming //避免一次性在内存写入大文件造成oom，需要在异步线程
    @GET    //download是GET的变种，算是特例
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String,Object> params);


    @Multipart
    @POST  //UPLOAD是post的变种，算是特例
    Call<String> upload(@Url String  url, @Part MultipartBody.Part file);
}
