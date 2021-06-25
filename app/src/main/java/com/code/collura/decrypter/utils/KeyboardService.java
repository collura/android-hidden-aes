package com.code.collura.decrypter.utils;

import android.content.Context;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;

public class KeyboardService {
    public static void hide (Context context, View editText ) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void show( Context context, View editText ){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput( editText, 0 );
    }
}
