package com.example.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

public class DataBaseManager {
    private DaoSession daoSession = null;
    private UserProfileDao mDao = null;

    private DataBaseManager(){

    }

    public DataBaseManager init(Context context){
        initDao(context);
        return this;
    }

    private static final class Holder{
        private static final DataBaseManager INSTANCE = new DataBaseManager();
    }
    public static DataBaseManager getInstance(){
        return Holder.INSTANCE;
    }
    private void initDao(Context context){
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fase_ec.db");
        final Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        mDao = daoSession.getUserProfileDao();
    }

    public final UserProfileDao getmDao(){
        return mDao;
    }
}
