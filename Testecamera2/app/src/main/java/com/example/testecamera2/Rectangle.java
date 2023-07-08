package com.example.testecamera2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Rectangle extends View {
    Paint paint = new Paint();

    public Rectangle(Context context) {
        super(context);
    }
    private int left = 310;
    private int top = 300;
    private int right = 390;
    private int bottom = 900;

    private int topC = 520;
    private int bottomC = 540;

    private int topT = 580;

    private int bottomT = 600;


    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        Rect rect = new Rect(Constantes.LEFT, Constantes.TOP, Constantes.RIGHT, Constantes.BOTTOM);
        Rect rectC = new Rect(Constantes.LEFT, Constantes.TOPC, Constantes.RIGHT, Constantes.BOTTOMC);
        Rect rectT = new Rect(Constantes.LEFT, Constantes.TOPT, Constantes.RIGHT, Constantes.BOTTOMT);
        canvas.drawRect(rect, paint );
        canvas.drawRect(rectC, paint );
        canvas.drawRect(rectT, paint );




    }
}