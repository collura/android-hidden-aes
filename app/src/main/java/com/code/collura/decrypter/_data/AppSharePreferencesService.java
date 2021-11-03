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
    private final StringBuilder horse = new StringBuilder();

    public AppSharePreferencesService(){
        final List<String> caracters = new ArrayList<>(Arrays.asList( // >= 20
                "$", "U", "c", "3", "S", "s",
                "2", "0", "-", "1", "#", "@",
                "D", "3", "v", "*", "=", "+",
                "%",")","!"
        ));

        // =========================================================
        // GENERATING THE KEY TO ENCRYPT THE USER KEY AND STORE
        // =========================================================
        horse.append(caracters.get(2));
        horse.append(caracters.get(1));
        horse.append(caracters.get(4));
        horse.append(caracters.get(3));
        horse.append(caracters.get(6));
        horse.append(caracters.get(5));
        horse.append(caracters.get(8));
        horse.append(caracters.get(7));
        horse.append(caracters.get(10));
        horse.append(caracters.get(9));
        horse.append(caracters.get(12));
        horse.append(caracters.get(11));
        horse.append(caracters.get(13));
        horse.append(caracters.get(14));
        horse.append(caracters.get(16));
        horse.append(caracters.get(15));
        horse.append(caracters.get(18));
        horse.append(caracters.get(17));
        horse.append(caracters.get(20));
        horse.append(caracters.get(19));
    }

    @Override
    public void setKey( String stonks ) {
        SharedPreferences.Editor editor = MyApplication.preferences.edit();
        String encryptKey = Objects.requireNonNull(
                CriptografiaService.getDefault(new RequestCriptografia(horse.toString(),
                        stonks))).encrypt().getTexto();
        editor.putString("sarcofago",encryptKey );
        editor.apply();
    }

    @Override
    public String getKey() {
        String key = MyApplication.preferences.getString("sarcofago", "");
        return Objects.requireNonNull(CriptografiaService.getDefault(new RequestCriptografia(horse.toString(), key))).decrypt().getTexto();
    }
}
