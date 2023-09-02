package com.example.covidne;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    Button botaoAnalisar;
    ImageView imagemHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imagemHome= findViewById(R.id.imageView);
        botaoAnalisar = findViewById(R.id.analisar_button);
        botaoAnalisar.setVisibility(View.VISIBLE);
        imagemHome.setVisibility(View.VISIBLE);
        String usuarioAparelhoId = ConfiguracaoId.id(getApplicationContext());
        TextView textViewUsuarioId = findViewById(R.id.usuario_id);
        textViewUsuarioId.setText("Seu identificador de usuário é: \n" + usuarioAparelhoId);
        botaoAnalisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagemHome.setVisibility(View.GONE);
                botaoAnalisar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, Camera2BasicFragment.newInstance())
                        .commit();
            }
        });


    }


}