package com.engine.privatefood;

import android.content.Context;
import android.content.SharedPreferences;
import com.engine.privatefood.bean.UserBean;

/**
 * Created by engine on 16/3/11.
 */
public class UserManager {
   private static UserManager instance ;
   private SharedPreferences sharedPreferences ;
   private SharedPreferences.Editor editor ;
   private UserManager(Context context){
      sharedPreferences=context.getSharedPreferences("user",1);
      editor = sharedPreferences.edit();
   }
   public static UserManager getInstance(Context context) {
      if (instance==null){
         instance = new UserManager(context);
      }
      return instance;
   }
   public void saveUser(UserBean userBean){
      editor.putInt("userid",userBean.userid);
      editor.putString("userName",userBean.userName);
      editor.putInt("userType",userBean.userType);
      editor.commit();
   }
   public UserBean getUser(){
      int userId = sharedPreferences.getInt("userid",-1);
      if (userId==-1){
         return null;
      }
      UserBean userBean = new UserBean();
      userBean.userid=userId;
      userBean.userName=sharedPreferences.getString("userName","");
      userBean.userType=sharedPreferences.getInt("userType",1);
      return userBean;
   }

   public void clean(){
      editor.clear();
      editor.commit();
   }
}
