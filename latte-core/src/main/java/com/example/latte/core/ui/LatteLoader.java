package com.example.latte.core.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.latte.core.R;
import com.example.latte.core.utils.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class LatteLoader {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();//保存所有的对话框，放方便再不用的时候删除

    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    public static void showLoading(Context context, String type){
         final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

         final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type,context);
         dialog.setContentView(avLoadingIndicatorView);

         int deviceWidth = DimenUtil.getScreenWidth();
         int deviceHeight = DimenUtil.getScreenHeight();

         final Window dialogWindow = dialog.getWindow();

         if (dialogWindow != null){
             WindowManager.LayoutParams lp = dialogWindow.getAttributes();
             lp.width = deviceWidth / LOADER_SIZE_SCALE;
             lp.height = deviceHeight / LOADER_SIZE_SCALE;
             lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;//不合适可以删除
             lp.gravity = Gravity.CENTER;
         }
         LOADERS.add(dialog);
         dialog.show();
     }

    /**
     *
     * @param context 不要用applicationcontext
     */
     public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
     }

     public static void stopLoading(){
         for (AppCompatDialog dialog:LOADERS){
             if (dialog!=null){
                 if (dialog.isShowing())
                     dialog.cancel(); //dismiss和cancel的区别
             }
         }
     }
}
