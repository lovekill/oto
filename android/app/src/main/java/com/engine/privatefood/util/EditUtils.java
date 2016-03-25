package com.engine.privatefood.util;

import android.widget.EditText;

/**
 * Created by engine on 16/3/22.
 */
public class EditUtils {
    public static boolean editIsNull(EditText editText,boolean trip){
        if (editText==null) return true ;
        String string = editText.getText().toString() ;
        if (trip){
            string = string.trim();
        }
        if (string.length()==0) return true ;
        return  false ;
    }
    public static boolean editsIsNull(boolean trip,EditText... editTexts){
        boolean flag= true;
        for (EditText editText:editTexts){
            flag=flag&&editIsNull(editText,trip);
            if (flag){
                return flag ;
            }
        }
        return  flag ;
    }

   public static  boolean isFloatValue(EditText editText){
       try {
           float f = Float.parseFloat(editText.getText().toString());
           return  true ;
       }catch (Exception e){
           return false ;
       }
   }

    public static boolean isFloatValue(EditText... editTexts){
        for (EditText editText:editTexts){
            if (!isFloatValue(editText)){
               return false ;
            }
        }
        return  true ;
    }
}
