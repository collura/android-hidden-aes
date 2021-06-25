package com.code.collura.decrypter.main;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.code.collura.decrypter.databinding.MainViewBinding;

public class AppMainView extends AppCompatActivity implements MainView {
    MainViewBinding binding;
    private AppMainPresenter presenter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new AppMainPresenter(this);
        binding.btnEncript.setOnClickListener(presenter.getBtnEncriptOnClickListener());
        binding.btnDecript.setOnClickListener(presenter.getBtnDecriptOnClickListener());
        binding.btnShare.setOnClickListener(presenter.getBtnShareOnClickListener());
        binding.btnClean.setOnClickListener(presenter.getBtnCleanOnClickListener());
    }
}