package com.vikination.imagemachine.ui.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.vikination.imagemachine.databinding.ActivityQrscannerBinding;

public class QrScannerActivity extends AppCompatActivity {

    ActivityQrscannerBinding binding;
    CodeScanner codeScanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrscannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        codeScanner = new CodeScanner(this, binding.scannerView);
        codeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            Intent intent = new Intent();
            intent.putExtra("qrdata",result.getText());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}
