package com.example.testecamera2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    Button botaoAnalisar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        botaoAnalisar = findViewById(R.id.analisar_button);
        botaoAnalisar.setVisibility(View.VISIBLE);
        String usuarioAparelhoId = ConfiguracaoId.id(getApplicationContext());
        TextView textViewUsuarioId = findViewById(R.id.usuario_id);
        textViewUsuarioId.setText("Seu identificador de usuário é: \n" + usuarioAparelhoId);
        botaoAnalisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botaoAnalisar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, Camera2BasicFragment.newInstance())
                        .commit();
            }
        });





    }
}