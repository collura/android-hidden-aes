package com.code.collura.decrypter.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;

import com.code.collura.decrypter.base.MyApplication;

public class VibrateService extends Service {

    public static void vibrate() {
        Vibrator rr = (Vibrator) MyApplication.getInstance().getSystemService(VIBRATOR_SERVICE);
        rr.vibrate(80);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
