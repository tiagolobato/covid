package com.example.testecamera2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class ConfiguracaoId {
    private static final String ALIAS_ID_UNICO = "ALIAS_ID_UNICO";
    private static String IdUnico = null;


    public synchronized static String id(Context context) {
        if (IdUnico == null) {

            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    ALIAS_ID_UNICO, Context.MODE_PRIVATE);
            IdUnico = sharedPrefs.getString(ALIAS_ID_UNICO, null);

            if (IdUnico == null) {
                IdUnico = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(ALIAS_ID_UNICO, IdUnico);
                editor.commit();
            }
        }

        return IdUnico;
    }
}
