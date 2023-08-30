package com.example.covidne;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

public class Retangulo extends View {
    Paint paint = new Paint();

    public Retangulo(Context context) {
        super(context);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = Posicoes.getScreenWidth();
        int height = Posicoes.getScreenHeight();
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        int width = Posicoes.getScreenWidth();
        int height = Posicoes.getScreenHeight();

        Posicoes posicoes = new Posicoes(width,height);



        int teste = posicoes.TOP;
        Rect retanguloPai = new Rect(posicoes.LEFT, posicoes.TOP, posicoes.RIGHT, posicoes.BOTTOM);
        Rect retanguloControle = new Rect(posicoes.LEFT, posicoes.TOPC, posicoes.RIGHT, posicoes.BOTTOMC);
        Rect retanguloTeste = new Rect(posicoes.LEFT, posicoes.TOPT, posicoes.RIGHT, posicoes.BOTTOMT);
        canvas.drawRect(retanguloPai, paint );
        canvas.drawRect(retanguloControle, paint );
        canvas.drawRect(retanguloTeste, paint );



    }
}