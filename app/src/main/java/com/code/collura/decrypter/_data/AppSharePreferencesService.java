package com.code.collura.decrypter._data;

import android.content.SharedPreferences;

import com.code.collura.decrypter._base.MyApplication;
import com.code.collura.decrypter.crypto.CriptografiaService;
import com.code.collura.decrypter.crypto.RequestCriptografia;

import java.util.Objects;

public class AppSharePreferencesService implements SharedPreferencesService{
    @Override
    public void setKey( String key ) {
        SharedPreferences.Editor editor = MyApplication.preferences.edit();
        String encryptKey = Objects.requireNonNull(CriptografiaService.getDefault(new RequestCriptografia("$Ucess0$4Ndr01d2021%", key))).encrypt().getTexto();
        editor.putString("key",encryptKey );
        editor.apply();
    }

    @Override
    public String getKey() {
        String key = MyApplication.preferences.getString("key", "");
        return Objects.requireNonNull(CriptografiaService.getDefault(new RequestCriptografia("$Ucess0$4Ndr01d2021%", key))).decrypt().getTexto();
    }
}
