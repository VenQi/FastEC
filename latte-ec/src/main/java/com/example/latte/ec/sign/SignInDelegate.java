package com.example.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.util.PatternsCompat;
import android.view.View;

import com.example.latte.core.delegates.LatteDelegate;
import com.example.latte.core.net.RestClient;
import com.example.latte.core.net.callback.ISucess;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListeener = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListeener = (ISignListener) activity;
        }
    }


    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if (checkForm()){
            RestClient.builder()
                    .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")//todo替换真实的地址
                    .params("email",mEmail.getText().toString())
                    .params("password",mPassword.getText().toString())
                    .success(new ISucess() {
                        @Override
                        public void onSuccess(String response) {
                            SignHandler.onSignIn(response,mISignListeener);
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){

    }
    @OnClick(R2.id.tv_link_sign_up)
    void onClickToSignUp(){
        start(new SignUpDelegate());
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    private boolean checkForm(){//检验数据有效性
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();


        boolean isPass = true;

        if (email.isEmpty() || !PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("错误的邮箱格式");
            isPass =false;
        }else {
            mEmail.setError(null);
        }

//        if (phone.isEmpty() || phone.length() != 11){//后续扩展手机号有效性验证
//            mPhone.setError("手机号码错误");
//            isPass =false;
//        }else {
//            mPhone.setError(null);
//        }

        if (password.isEmpty()|| password.length()<6){
            mPassword.setError("密码格式错误");
            isPass = false;
        }else {
            mPassword.setError(null);
        }
        return isPass;

    }
}
