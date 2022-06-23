package com.example.prc.unstopeble_timer;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.prc.R;

public class AppPreference {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sEditor;
    Context context;

    public AppPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void open_editor() {
        sEditor = sharedPreferences.edit();
    }

    //save remainig second befor destroying
    public void setRemaining(long remainingMillis) {
        open_editor();
        sEditor.putLong("Remaining", remainingMillis);
        sEditor.commit();
    }

    public long getRemaining() {
        return sharedPreferences.getLong("Remaining", 0);
    }

    public void removeAll() {
        open_editor();
        sEditor.clear();
        sEditor.commit();
    }
}


