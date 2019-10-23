package com.example.latte.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.latte.core.app.Latte;
import com.example.latte.core.net.callback.IRequest;
import com.example.latte.core.net.callback.ISucess;
import com.example.latte.core.utils.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

//todo 扩展其他的功能回调，如成功失败之类
public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISucess SUCCESS;

    public SaveFileTask(IRequest request, ISucess sucess) {
        this.REQUEST = request;
        this.SUCCESS = sucess;
    }

    @Override
    protected File doInBackground(Object... objects) {
       String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String )objects[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")){
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")){
            extension = "";
        }
        if (name == null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);//此方法必须要用完整路径名
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        //主线程操作
        if (SUCCESS != null)
            SUCCESS.onSuccess(file.getPath());

        if (REQUEST != null)
            REQUEST.onRequestEnd();

        autoInstallApk(file);//如果下载的是apk文件，自动安装，根据实际业务逻辑添加删除

    }

    /**
     * 判断文件是否为apk，如果是apk就自动安装
     * @param file
     */
    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equalsIgnoreCase("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");//todo 学习该功能 扩展Intent的所有用法
            Latte.getApplicationContext().startActivity(install);

        }
    }
}
