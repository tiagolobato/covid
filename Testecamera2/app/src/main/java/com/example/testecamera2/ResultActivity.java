package com.example.testecamera2;


import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private TextView resultLabel;
    private ConstraintLayout resultPage;
    private TextView resultadoDescricao;
    private Button backButton;
    private Button enviarResultadosButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultLabel = findViewById(R.id.result_label);
        resultadoDescricao = findViewById(R.id.resultado_descricao);
        resultPage = findViewById(R.id.result_page);

        Intent intent = getIntent();
        String resultado = intent.getStringExtra("resultado");
        backButton = findViewById(R.id.back_button);
        enviarResultadosButton = findViewById(R.id.enviar_resultado);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        enviarResultadosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarResultado(resultado);
                showToast("Resultado enviado com sucesso!");
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


        File rootPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"result.csv");
        String id = IdConfig.id(getApplicationContext());
        CsvCreator.createCsvFileIfNotExists(rootPath.getPath(),resultado,id);




    }

    private void showToast(final String text) {
        final Activity activity = this;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void enviarResultado(String resultado) {
        String id = IdConfig.id(getApplicationContext());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("resultado", resultado);
        user.put("_id", id);
        user.put("data", CsvCreator.getCurrentDate());

// Add a new document with a generated ID
        db.collection("registros")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}

