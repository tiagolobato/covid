package com.example.testecamera2;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;

public class ResultActivity extends AppCompatActivity {

    private TextView resultLabel;
    private ConstraintLayout resultPage;
    private TextView resultadoDescricao;
    private Button captureButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultLabel = findViewById(R.id.result_label);
        resultadoDescricao = findViewById(R.id.resultado_descricao);
        resultPage = findViewById(R.id.result_page);

        Intent intent = getIntent();
        String resultado = intent.getStringExtra("resultado");
        captureButton = findViewById(R.id.back_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if(resultado.equals("Positivo")){
            resultPage.setBackgroundColor(Color.parseColor("#ff0000"));
            resultadoDescricao.setText("Procure um médico");
            resultadoDescricao.setTextColor(Color.parseColor("#ffffff"));
            resultLabel.setTextColor(Color.parseColor("#ffffff"));
        }
        if(resultado.equals("Negativo")){
            resultPage.setBackgroundColor(Color.parseColor("#4fa866"));
            resultadoDescricao.setText("Esse resultado não garante 100% de certeza");
            resultadoDescricao.setTextColor(Color.parseColor("#ffffff"));
            resultLabel.setTextColor(Color.parseColor("#ffffff"));
        }
        if(resultado.equals("Inválido")){
            resultPage.setBackgroundColor(Color.parseColor("#ffd966"));
            resultadoDescricao.setText("Exame deve ser descartado e repetido com outro kit");
            resultadoDescricao.setTextColor(Color.parseColor("#ff0000"));
            resultLabel.setTextColor(Color.parseColor("#ff0000"));
        }
        resultLabel.setText("  Teste\n" + resultado.toUpperCase());

        String id = IdConfig.id(getApplicationContext());
        File rootPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"result.csv");
        CsvCreator.createCsvFileIfNotExists(rootPath.getPath(),resultado,id);


    }

}

