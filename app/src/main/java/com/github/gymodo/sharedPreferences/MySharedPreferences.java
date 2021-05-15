package com.github.gymodo.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MySharedPreferences {

    public static final String PREF_FILE_NAME = "Authentication";

    public MySharedPreferences() {

    }

    public static void saveSharePref(String email, String password, Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    public static String[] readSharedPref(Context context) {
        //Si el fitxer de pref tenim dades vol dir que ja ens hem autenticat
        String[] credential = new String[2];
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        credential[0] = sharedPreferences.getString("email", null);
        credential[1] = sharedPreferences.getString("password", null);

        if (credential[0] != null & credential[1] != null) {
            return credential;
        }
        return null;
    }

    public static void deleteSharedPref(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.clear().apply();
    }

}
