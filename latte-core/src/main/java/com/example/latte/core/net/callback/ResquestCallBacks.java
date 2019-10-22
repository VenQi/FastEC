package com.example.latte.core.net.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResquestCallBacks implements Callback<String >{
    private final IRequest REQUEST;
    private final ISucess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;

    public ResquestCallBacks(IRequest request, ISucess sucess, IError error, IFailure failure) {
        this.REQUEST = request;
        this.SUCCESS = sucess;
        this.ERROR = error;
        this.FAILURE = failure;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if (ERROR != null){
                ERROR.onError(response.code(),response.message());
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        t.printStackTrace();
        if (FAILURE != null){
            FAILURE.onFailure();
        }

        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }
    }
}
