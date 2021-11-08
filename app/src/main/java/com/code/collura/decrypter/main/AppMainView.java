package com.code.collura.decrypter.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.code.collura.decrypter.R;
import com.code.collura.decrypter._data.AppSharePreferencesService;
import com.code.collura.decrypter.about.AppAboutView;
import com.code.collura.decrypter.databinding.MainViewBinding;

public class AppMainView extends AppCompatActivity implements MainView {
    MainViewBinding binding;
    final AppSharePreferencesService preferenceService = new AppSharePreferencesService();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppMainPresenter presenter = new AppMainPresenter(this);
        binding.btnEncript.setOnClickListener(presenter.getBtnEncriptOnClickListener());
        binding.btnDecript.setOnClickListener(presenter.getBtnDecriptOnClickListener());
        binding.btnShare.setOnClickListener(presenter.getBtnShareOnClickListener());
        binding.btnClean.setOnClickListener(presenter.getBtnCleanOnClickListener());
        binding.btnContentPaste.setOnClickListener(presenter.getBtnContentPastOnClickListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.txtSecurityKey.setText(preferenceService.getKey());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                startActivity(new Intent(this, AppAboutView.class));
                break;
            case R.id.action_share_app:
                sharedApp();
                break;
        }
        return true;
    }

    public void sharedApp () {
        Intent sendIntent = new Intent ();
        sendIntent.setType ("text/plain");
        sendIntent.setAction (Intent.ACTION_SEND);
        sendIntent.putExtra (Intent.EXTRA_SUBJECT,
                "Hidden AES - Criptografia/Descriptografia de mensagens.");
        sendIntent.putExtra (Intent.EXTRA_TEXT,
                "Hidden AES - Criptografia/Descriptografia de " +
                        "mensagens com Chave de Segurança. " +
                        "\n\nApós a criptografia, somente " +
                        "será possível decifrar a mensagem de posse da chave utilizada no" +
                        " momento da criptografia. A mensagem pode ser compartilhada através " +
                        "de outros aplicativos de comunicação como E-Mail ou WhatsApp, " +
                        "proporcionando uma camada de segurança e privacidade." +
                        "\n\n* Este aplicativo não realiza nenhum tipo de troca de dados com a internet." +
                        "\n\nPara instalar visite : " +
                        "\n\nhttps://play.google.com/store/apps/details?id=com.code.collura.decrypter.");
        startActivity (Intent.createChooser (sendIntent, "Divulgar o App"));
    }

}