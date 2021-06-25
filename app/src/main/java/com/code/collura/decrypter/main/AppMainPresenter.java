package com.code.collura.decrypter.main;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.code.collura.decrypter.base.MyApplication;
import com.code.collura.decrypter.crypter.CriptografiaService;
import com.code.collura.decrypter.crypter.RequestCriptografia;
import com.code.collura.decrypter.crypter.RetornoCriptografia;
import com.code.collura.decrypter.utils.KeyboardService;
import com.code.collura.decrypter.utils.VibrateService;

class AppMainPresenter implements MainPresenter {
    private AppMainView view;
    private final Toast t = MyApplication.longToast;
    private Intent sendIntent = new Intent ();
    private ClipboardManager clipboard;

    @RequiresApi(api = Build.VERSION_CODES.M)
    AppMainPresenter(AppMainView view) {
        this.view = view;
        sendIntent.setType ("text/plain");
        sendIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setAction (Intent.ACTION_SEND);
//        ClipboardManager clipboard = (ClipboardManager)
//                view.getSystemService(Context.CLIPBOARD_SERVICE);
//        String pasteData = "";
//
//        if (clipboard.hasPrimaryClip()) {
//            if(clipboard.getPrimaryClip().getItemAt(0) != null  ) {
//                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
//                Log.d("betto", item.getText().toString());
//            }
//        }
    }

    View.OnClickListener getBtnEncriptOnClickListener(){
        return v -> {
            if (!inputIsEmpty()) {
                VibrateService.vibrate();
                String input = view.binding.txtInput.getText().toString();
                String key = view.binding.txtSecurityKey.getText().toString();
                String textoEncriptado = CriptografiaService.getDefault(new RequestCriptografia(key, input)).encrypt().getTexto();
                view.binding.txtResult.setText(textoEncriptado);
                KeyboardService.hide(view, view.binding.txtInput);
                KeyboardService.hide(view, view.binding.txtSecurityKey);
                view.binding.resultContainer.setVisibility(View.VISIBLE);

            }
        };
    }

    View.OnClickListener getBtnDecriptOnClickListener(){
        return v -> {
            view.binding.txtResult.setText("");
            if (!inputIsEmpty()) {
                RetornoCriptografia retorno;
                VibrateService.vibrate();
                String input = view.binding.txtInput.getText().toString();
                String key = view.binding.txtSecurityKey.getText().toString();

                retorno =  CriptografiaService.getDefault(new RequestCriptografia(key, input)).decrypt();
                if( !retorno.getError()) {
                    String textoDecriptado = retorno.getTexto();
                    view.binding.txtResult.setText(textoDecriptado);
                    KeyboardService.hide(view, view.binding.txtInput);
                    KeyboardService.hide(view, view.binding.txtSecurityKey);
                    view.binding.resultContainer.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    View.OnClickListener getBtnShareOnClickListener(){
        return v -> {
            if (!view.binding.txtResult.getText().toString().isEmpty()) {
                KeyboardService.hide(view, view.binding.txtInput);
                KeyboardService.hide(view, view.binding.txtSecurityKey);
                sendIntent.putExtra(Intent.EXTRA_TEXT, view.binding.txtResult.getText().toString());
                sendIntent.setPackage("com.whatsapp");
                view.startActivity(Intent.createChooser(sendIntent, "testetste"));
            }
        };
    }

    View.OnClickListener getBtnCleanOnClickListener(){
        return v -> {
            KeyboardService.hide(view, view.binding.txtInput);
            KeyboardService.hide(view, view.binding.txtSecurityKey);
            view.binding.txtResult.setText("");
            view.binding.txtSecurityKey.setText("");
            view.binding.txtInput.setText("");
            view.binding.txtInput.requestFocus();
            view.binding.resultContainer.setVisibility(View.GONE);
        };
    }

    private Boolean inputIsEmpty(){
        boolean empty = view.binding.txtInput.getText().toString().isEmpty()
                || view.binding.txtSecurityKey.getText().toString().isEmpty();

        if(empty) {
            t.setText("Preencha todos os campos !");
            t.show();
        }
        return empty;
    }
}
