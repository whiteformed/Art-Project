package com.example.debt_manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class PrefsHelper {
    private static final String PREFS = "my_prefs";
    private static final String PREF_KEY_LANG = "lang_code";
    private static final String PREF_KEY_THEME = "theme";

    public static void setLocale(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString(PREF_KEY_LANG, context.getString(R.string.pref_locale_en));

        Locale locale = new Locale(langCode.toLowerCase());
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        //noinspection deprecation
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static void setTheme(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String theme = sharedPreferences.getString(PREF_KEY_THEME, context.getString(R.string.pref_theme_light));

        if (theme.equals(context.getString(R.string.pref_theme_light))) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public static void setLocalePrefs(Activity context, String langCode) {
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_LANG, langCode);
        editor.apply();
    }

    public static void setThemePrefs(Activity context, String theme) {
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_THEME, theme);
        editor.apply();
    }

    public static String getLocalePrefs(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        return sharedPreferences.getString(PREF_KEY_LANG, context.getString(R.string.pref_locale_en));
    }

    public static String getThemePrefs(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        return sharedPreferences.getString(PREF_KEY_THEME, context.getString(R.string.pref_theme_light));
    }
}
