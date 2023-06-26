package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ResultActivity extends AppCompatActivity {

    private TextView resultLabel;
    private Button captureButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultLabel = findViewById(R.id.result_label);

        Intent intent = getIntent();
        boolean isPositive = intent.getBooleanExtra("isPositive", false);
        captureButton = findViewById(R.id.back_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (isPositive) {
            resultLabel.setText("Positivo");
        } else {
            resultLabel.setText("Negativo");
        }
    }
}
