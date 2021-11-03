package com.code.collura.decrypter._data;

import android.content.SharedPreferences;

import com.code.collura.decrypter._base.MyApplication;
import com.code.collura.decrypter.crypto.CriptografiaService;
import com.code.collura.decrypter.crypto.RequestCriptografia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AppSharePreferencesService implements SharedPreferencesService{
    private final StringBuilder keyTokey = new StringBuilder();

    public AppSharePreferencesService(){
        final List<String> caracters = new ArrayList<>(Arrays.asList(
                "$", "U", "c", "3", "S", "s", "2", "0", "0", "1", "#", "@", "D", "3", "v", "=", "=", "+", "%"
        ));
        for(int i = 0; i < caracters.size(); i++)
            keyTokey.append(i);
    }

    @Override
    public void setKey( String key ) {
        SharedPreferences.Editor editor = MyApplication.preferences.edit();
        String encryptKey = Objects.requireNonNull(
                CriptografiaService.getDefault(new RequestCriptografia(keyTokey.toString(),
                        key))).encrypt().getTexto();
        editor.putString("key",encryptKey );
        editor.apply();
    }

    @Override
    public String getKey() {
        String key = MyApplication.preferences.getString("key", "");
        return Objects.requireNonNull(CriptografiaService.getDefault(new RequestCriptografia(keyTokey.toString(), key))).decrypt().getTexto();
    }
}
