package uz.adkhamjon.promovie.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceLanguage {
    SharedPreferences prefs;
    private static SharedPreferenceLanguage sharePreference;
    SharedPreferences.Editor editor;

    public static SharedPreferenceLanguage getInstance(Context context) {
        if (sharePreference != null)
            return sharePreference;
        else return sharePreference = new SharedPreferenceLanguage(context);
    }

    private SharedPreferenceLanguage(Context context) {
        prefs = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
    }

    public void setLang(String lang) {
        editor = prefs.edit();
        editor.putString("lang", lang);
        editor.apply();
    }

    public String getLang() {
        return prefs.getString("lang", "en");
    }

    public void setTarget(boolean target) {
        editor = prefs.edit();
        editor.putBoolean("target", target);
        editor.apply();
    }

    public boolean getTarget() {
        return prefs.getBoolean("target", false);
    }

    public void setHasLang(boolean hasLang) {
        editor = prefs.edit();
        editor.putBoolean("hasLang", hasLang);
        editor.apply();
    }

    public boolean getHaslang() {
        return prefs.getBoolean("hasLang", false);
    }
}
