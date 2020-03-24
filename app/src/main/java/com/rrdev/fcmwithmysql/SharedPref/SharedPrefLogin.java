package com.rrdev.fcmwithmysql.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefLogin {
    private final String KEY = "keyLogin";
    private final String USERNAME = "username";
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPrefLogin(Context context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(KEY, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return sharedPreferences.getBoolean(KEY, false);
    }

    public void putUsername(String loginorout) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(USERNAME, loginorout);
        edit.commit();
    }
    public String getUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }

}
