package com.example.testecamera2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class ConfiguracaoId {
    private static String IdUnico = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public synchronized static String id(Context context) {
        if (IdUnico == null) {

            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            IdUnico = sharedPrefs.getString(PREF_UNIQUE_ID, null);

            if (IdUnico == null) {
                IdUnico = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, IdUnico);
                editor.commit();
            }
        }

        return IdUnico;
    }
}
