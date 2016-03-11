package com.engine.privatefood;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by engine on 16/3/11.
 */
public class UserManager {
   private UserManager instance ;
   private SharedPreferences sharedPreferences ;
   private SharedPreferences.Editor editor ;
   public UserManager getInstance(Context context) {
      return instance;
   }
}
