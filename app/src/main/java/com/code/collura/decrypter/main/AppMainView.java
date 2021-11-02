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
        if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AppAboutView.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}