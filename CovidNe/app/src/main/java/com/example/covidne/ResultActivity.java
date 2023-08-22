package com.example.covidne;


import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    private TextView resultadoLabel;
    private ConstraintLayout resultadoPage;
    private TextView resultadoDescricao;
    private Button botaoVoltar;
    private Button botaoEnviarResultado;
    private ImageView imagemSemaforo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultadoLabel = findViewById(R.id.resultado_label);
        resultadoDescricao = findViewById(R.id.resultado_descricao);
        resultadoPage = findViewById(R.id.result_page);
        imagemSemaforo = findViewById(R.id.semaforo_image);

        Intent intent = getIntent();
        String resultado = intent.getStringExtra("resultado");
        botaoVoltar = findViewById(R.id.voltar_button);
        botaoEnviarResultado = findViewById(R.id.enviar_resultado_button);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        botaoEnviarResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarResultado(resultado);
                mostrarToast("Resultado enviado com sucesso!");
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if(resultado.equals(Resultado.POSITIVO)){
            resultadoDescricao.setBackgroundColor(Color.parseColor("#ff0000"));
            resultadoDescricao.setText("Procure um médico");
            resultadoLabel.setBackgroundColor(Color.parseColor("#ff0000"));
            imagemSemaforo.setImageResource(R.drawable.sinal_vermelho);
        }
        if(resultado.equals(Resultado.NEGATIVO)){
            resultadoDescricao.setBackgroundColor(Color.parseColor("#4fa866"));
            resultadoDescricao.setText("Esse resultado não garante 100% de certeza");
            resultadoLabel.setBackgroundColor(Color.parseColor("#4fa866"));
            imagemSemaforo.setImageResource(R.drawable.sinal_verde);
        }
        if(resultado.equals(Resultado.INVALIDO)){
            resultadoDescricao.setBackgroundColor(Color.parseColor("#ffd966"));
            resultadoDescricao.setText("Exame deve ser descartado e repetido com outro kit");
            resultadoLabel.setBackgroundColor(Color.parseColor("#ffd966"));
            imagemSemaforo.setImageResource(R.drawable.sinal_amarelo);
        }
        resultadoLabel.setText("Teste\n" + resultado.toUpperCase());


        File caminhoRaiz = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"result.csv");
        String id = ConfiguracaoId.id(getApplicationContext());
        GeradorCsv.criarArquivoCsvSeNaoExistir(caminhoRaiz.getPath(),resultado,id);


    }

    private void mostrarToast(final String texto) {
        final Activity activity = this;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, texto, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void enviarResultado(String resultado) {
        String id = ConfiguracaoId.id(getApplicationContext());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> registro = new HashMap<>();
        registro.put("resultado", resultado);
        registro.put("_id", id);
        registro.put("data", GeradorCsv.buscarDataHoraAtual());

        db.collection("registros")
                .add(registro)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Documento adicionado com ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Erro ao adicionar documento", e);
                    }
                });
    }

}

