package com.example.covidne;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Retangulo extends View {
    Paint paint = new Paint();

    public Retangulo(Context context) {
        super(context);
    }



    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        Rect retanguloPai = new Rect(Constantes.LEFT, Constantes.TOP, Constantes.RIGHT, Constantes.BOTTOM);
        Rect retanguloControle = new Rect(Constantes.LEFT, Constantes.TOPC, Constantes.RIGHT, Constantes.BOTTOMC);
        Rect retanguloTeste = new Rect(Constantes.LEFT, Constantes.TOPT, Constantes.RIGHT, Constantes.BOTTOMT);
        canvas.drawRect(retanguloPai, paint );
        canvas.drawRect(retanguloControle, paint );
        canvas.drawRect(retanguloTeste, paint );




    }
}