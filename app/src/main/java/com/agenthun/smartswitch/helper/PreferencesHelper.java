package com.agenthun.smartswitch.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.agenthun.smartswitch.model.User;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/14 04:58.
 */

public class PreferencesHelper {

    private static final String USER_PREFERENCES = "userPreferences";
    private static final String PREFERENCE_USERNAME = USER_PREFERENCES + ".username";
    private static final String PREFERENCE_PASSWORD = USER_PREFERENCES + ".password";

    public PreferencesHelper() {
    }

    public static void writeToPreferences(Context context, User user) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_USERNAME, user.getUsername());
        editor.putString(PREFERENCE_PASSWORD, user.getPassword());
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final String username = preferences.getString(PREFERENCE_USERNAME, null);
        final String password = preferences.getString(PREFERENCE_PASSWORD, null);
        if (username == null || password == null) {
            return null;
        }
        return new User(username, password);
    }

    public static void signOut(Context context, boolean isSavePreferences) {
        if (!isSavePreferences) {
            SharedPreferences.Editor editor = getEditor(context);
            editor.remove(PREFERENCE_USERNAME);
            editor.remove(PREFERENCE_PASSWORD);
            editor.apply();
        }
    }

    public static void clearUser(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCE_USERNAME);
        editor.remove(PREFERENCE_PASSWORD);
        editor.apply();
    }

    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCE_USERNAME) && preferences.contains(PREFERENCE_PASSWORD);
    }

    public static boolean isInputDataValid(CharSequence username, CharSequence password) {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
}
