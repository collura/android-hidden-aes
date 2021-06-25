package com.code.collura.decrypter.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

// Esta classe é a classe pai da aplicação
// Ela é a primeira a ser criada e ultima a
// ser destruída. Um bom lugar para se colocar
// Recursos Globais
public class MyApplication extends Application {
    private static String versionName;
    private static String footerText;
    private static String dataHoraStart;
    private static MyApplication instance;
    public static Toast shortToast;
    public static Toast longToast;

    @SuppressLint({"ShowToast", "SimpleDateFormat"})
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        try {
            PackageInfo packageInfo = getPackageManager ().getPackageInfo (getPackageName (), 0);
            versionName = packageInfo.versionName;
        } catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace ();
        }
        shortToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        shortToast.setGravity(Gravity.CENTER, 0, 0);
        longToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        longToast.setGravity(Gravity.CENTER, 0, 0);
        footerText = "";

    }

    public static MyApplication getInstance () {
        return instance;
    }

    public String getVersionName(){
        return versionName;
    }

    public String getFooterText(){
        return footerText;
    }

}
