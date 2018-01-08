package com.creeps.sl_app.quizapp.core_services.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rohan on 1/10/17.
 * A helper class for using SharedPreferences.
 */

public class SharedPreferenceHandler {
    private Context mContext;
    private static SharedPreferenceHandler mSharedPreferenceHandler;/* to be used for singleton*/
    final SharedPreferences sharedPreferences;/* to be used for persisting essential data across sessions*/
    private final String SHARED_PREF_NAME="Sl_pref";/* name of the sharePref file*/

    private SharedPreferenceHandler(Context context){
        this.mContext=context;
        this.sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
    }



    public static SharedPreferenceHandler getInstance(Context context){
        if(SharedPreferenceHandler.mSharedPreferenceHandler == null){
            SharedPreferenceHandler.mSharedPreferenceHandler = new SharedPreferenceHandler(context);
        }

        return SharedPreferenceHandler.mSharedPreferenceHandler;
    }

    public String get(String x){
        return this.sharedPreferences.getString(x,null);
    }
    public void add(String key,String value){
        SharedPreferences.Editor editor=this.sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();/* commit asynchronously*/
    }



    public void remove(String key){
        SharedPreferences.Editor editor=this.sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public interface SharedPreferenceHandlerConstants{
        /* base details to be used as keys for sharedPreferences... */
        public final static String USER_ID="user_id",USER_NAME="name",USER_EMAIL="email",USER_SUBJECT="subject",USER_BRANCH="branch",USER_CHAPTERS="chapters";

    }


}
