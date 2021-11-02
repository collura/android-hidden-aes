package com.code.collura.decrypter.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.code.collura.decrypter._base.MyApplication;
import com.code.collura.decrypter.databinding.AboutViewBinding;

import java.util.Objects;

public class AppAboutView extends AppCompatActivity {

    private AboutViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AboutViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sobre");
        binding.txtVersion.append(MyApplication.getVersionName());
        binding.btnOpenGithubProject.setOnClickListener(v -> {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/collura/hidden-aes")));
      });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}