package uz.adkhamjon.promovie.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceTheme {
    SharedPreferences prefs;
    private static SharedPreferenceTheme sharePreference;
    SharedPreferences.Editor editor;

    public static SharedPreferenceTheme getInstance(Context context) {
        if (sharePreference != null)
            return sharePreference;
        else return sharePreference = new SharedPreferenceTheme(context);
    }

    private SharedPreferenceTheme(Context context) {
        prefs = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
    }
    public void setHasDark(boolean hasLang) {
        editor = prefs.edit();
        editor.putBoolean("hasLang", hasLang);
        editor.apply();
    }

    public boolean getHasDark() {
        return prefs.getBoolean("hasLang", false);
    }
}
