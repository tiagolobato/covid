package com.example.myapplication;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.View;


import androidx.camera.view.PreviewView;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.zxing.integration.android.IntentIntegrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int X_POSITION = 100; // posição x do pixel desejado
    private static final int Y_POSITION = 100; // posição y do pixel desejado


    private Button captureButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captureButton = findViewById(R.id.capture_button);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    openCamera2();
                }
            }
        });
    }


    private void openCamera2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Valor RGB do pixel");

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                System.out.println("teste erro");
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    System.out.println("teste erro3");
                    processImage(bitmap);
                }
            }
        }
        else{
            System.out.println("teste erro");
        }
    }

    private void processImage(Bitmap bitmap) {
        int numeroLinhas = RectEdgesDetector.detectRectEdges(bitmap,80);
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        int pixel = bitmap.getPixel(X_POSITION, Y_POSITION);

        int red = Color.red(pixel);
        int green = Color.green(pixel);
        int blue = Color.blue(pixel);
        // Exemplo de uso: exibir o valor RGB do pixel no console
        System.out.println("Valor RGB do pixel (" + X_POSITION + ", " + Y_POSITION + "): " + red + ", " + green + ", " + blue);
        openResultActivity(numeroLinhas > 1);
    }

    private void openResultActivity(boolean isPositive) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("isPositive", isPositive);
        startActivity(intent);
    }




}