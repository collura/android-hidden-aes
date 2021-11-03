package com.code.collura.decrypter.main;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.code.collura.decrypter._base.MyApplication;
import com.code.collura.decrypter._data.AppSharePreferencesService;
import com.code.collura.decrypter.crypto.CriptografiaService;
import com.code.collura.decrypter.crypto.RequestCriptografia;
import com.code.collura.decrypter.crypto.RetornoCriptografia;
import com.code.collura.decrypter.utils.KeyboardService;

import java.util.Objects;

class AppMainPresenter implements MainPresenter {
    private final AppMainView view;
    private final Toast t = MyApplication.longToast;
    private final Intent sendIntent = new Intent ();
    private ClipboardManager clipboard;
    private RetornoCriptografia retorno;

    AppMainPresenter(AppMainView view) {
        this.view = view;
        sendIntent.setType ("text/plain");
        sendIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setAction (Intent.ACTION_SEND);
    }

    View.OnClickListener getBtnEncriptOnClickListener(){
        return v -> {
            if (!inputIsEmpty()) {
                String input = view.binding.txtInputContent.getText().toString();
                String key = view.binding.txtSecurityKey.getText().toString();
                retorno = Objects.requireNonNull(CriptografiaService.getDefault(new RequestCriptografia(key, input))).encrypt();
                view.binding.txtResult.setText(retorno.getTexto());
                KeyboardService.hide(view, view.binding.txtInputContent);
                KeyboardService.hide(view, view.binding.txtSecurityKey);
                view.binding.resultContainer.setVisibility(View.VISIBLE);
                view.preferenceService.setKey(key);
//              ClipData clip = ClipData.newPlainText("encrypted", textoEncriptado);
//              clipboard.setPrimaryClip(clip);
            }
        };
    }

    View.OnClickListener getBtnDecriptOnClickListener(){
        retorno = null;
        return v -> {
            view.binding.txtResult.setText("");
            if (!inputIsEmpty()) {
                String input = view.binding.txtInputContent.getText().toString();
                String key = view.binding.txtSecurityKey.getText().toString();
                retorno =  Objects.requireNonNull(CriptografiaService.getDefault(new RequestCriptografia(key, input))).decrypt();
                if( !retorno.getError()) {
                    String textoDecriptado = retorno.getTexto();
                    view.binding.txtResult.setText(textoDecriptado);
                    view.binding.resultContainer.setVisibility(View.VISIBLE);
                } else
                    view.binding.resultContainer.setVisibility(View.GONE);
            }
        };
    }

    View.OnClickListener getBtnShareOnClickListener(){
        return v -> {
            if (!view.binding.txtResult.getText().toString().isEmpty()) {
                KeyboardService.hide(view, view.binding.txtInputContent);
                KeyboardService.hide(view, view.binding.txtSecurityKey);
                sendIntent.putExtra(Intent.EXTRA_TEXT, view.binding.txtResult.getText().toString());
                sendIntent.setType("text/plain");
                // Compartilha direto para o WhatApp
                //sendIntent.setPackage("com.whatsapp");
                view.startActivity(Intent.createChooser(sendIntent, null));
            }
        };
    }

    View.OnClickListener getBtnCleanOnClickListener(){
        return v -> {
            KeyboardService.hide(view, view.binding.txtInputContent);
            KeyboardService.hide(view, view.binding.txtSecurityKey);
            view.binding.txtResult.setText("");
            view.binding.txtInputContent.setText("");
            view.binding.txtInputContent.requestFocus();
            view.binding.resultContainer.setVisibility(View.GONE);
        };
    }

    View.OnClickListener getBtnContentPastOnClickListener(){
        return v -> {
            clipboard = (ClipboardManager) view.getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboard != null){
                if(clipboard.getPrimaryClip() != null) {
                    String textoRecuperado = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
                    view.binding.txtInputContent.setText(textoRecuperado);
                }
            }
        };
    }

    private Boolean inputIsEmpty(){
        boolean empty = view.binding.txtInputContent.getText().toString().isEmpty()
                || view.binding.txtSecurityKey.getText().toString().isEmpty();

        if(empty) {
            t.setText("Preencha todos os campos !");
            t.show();
        }
        return empty;
    }
}
