package com.example.art_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocaleHelper {
    private static String PREFS = "my_prefs";
    private static String PREF_KEY = "lang_code";

    public static void setLocale(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString(PREF_KEY, context.getString(R.string.pref_locale_en));

        Locale locale = new Locale(langCode.toLowerCase());
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        //noinspection deprecation
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static void setLocalePrefs(Activity context, String langCode) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY, langCode);
        editor.apply();
    }

    public static String getLocalePrefs(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        return sharedPreferences.getString(PREF_KEY, context.getString(R.string.pref_locale_en));
    }
}
