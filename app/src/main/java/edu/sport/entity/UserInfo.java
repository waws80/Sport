package edu.sport.entity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 个人信息键值
 */

public class UserInfo {

    private  UserInfo mUserInfo;
    private  SharedPreferences userSP;

    public UserInfo(Context context,String username){
        userSP=context.getSharedPreferences(username,Context.MODE_PRIVATE);
    }

    /**
     * 当前的用户信息
     */
    public static final String NAME="name";
    public static final String PASS="password";
    public static final String GENDER="gender";
    public static final String HEIGHT="height";
    public static final String WEIGHT="weight";

    /**
     * 获取当前用户的某个信息
     * @param key
     * @return
     */
    public  String getUserInfo(String key){
        if (userSP==null)throw new NullPointerException("请初始化个人信息方法 getInstance()");
        return userSP.getString(key,"");
    }

    /**
     * 设置当前用户的某个信息
     * @param key
     * @param value
     */
    public  void setUserInfo(String key,String value){
        if (userSP==null)throw new NullPointerException("请初始化个人信息方法 getInstance()");
        userSP.edit().putString(key, value).apply();
    }

    public  void cancelUserInfo(){
        if (userSP==null)throw new NullPointerException("请初始化个人信息方法 getInstance()");
        userSP.edit().clear().apply();
    }

}
