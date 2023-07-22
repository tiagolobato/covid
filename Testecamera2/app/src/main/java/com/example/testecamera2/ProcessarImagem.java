package com.example.testecamera2;




import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProcessarImagem {

    public static String GetResultado(Bitmap imageC, Bitmap imageT,Bitmap imageEntreCeT) {
        int backgroundColor = Color.rgb(167, 60, 78);

        List<Point> pontosC = PercorrerImagem(imageC, backgroundColor);
        List<Point> pontosT = PercorrerImagem(imageT,backgroundColor);
        List<Point> pontosEntreCeT = PercorrerImagem(imageEntreCeT,backgroundColor);

        if(isIntervaloVermelho(pontosEntreCeT.size())){
            return "Inválido";
        }
        if(isIntervaloVermelho(pontosT.size()) && isIntervaloVermelho(pontosC.size())){
            return "Positivo";
        }
        if(!isIntervaloVermelho(pontosT.size()) && isIntervaloVermelho(pontosC.size())){
            return "Negativo";
        }
        if(isIntervaloVermelho(pontosT.size()) && !isIntervaloVermelho(pontosC.size())){
            return "Inválido";
        }
        if(!isIntervaloVermelho(pontosT.size()) && !isIntervaloVermelho(pontosC.size())){
            return "Inválido";
        }
        return "Inválido";

    }



    private static List<Point>  PercorrerImagem(Bitmap image, int backgroundColor) {
        List<Point> rectanglePoints = new ArrayList<>();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getPixel(x, y);

                if (isPontoVermelho(pixel, backgroundColor, 20)) {
                    rectanglePoints.add(new Point(x, y));
                    int redDiff = Math.abs(((pixel >> 16) & 0xFF));
                    int greenDiff = Math.abs(((pixel >> 8) & 0xFF) );
                    int blueDiff = Math.abs((pixel & 0xFF) );
                    int teste =1;
                }
            }
        }
        return rectanglePoints;
    }


    private static boolean isPontoVermelho(int pixel, int backgroundColor, int tolerance) {
        int redDiff = Math.abs(((pixel >> 16) & 0xFF) - ((backgroundColor >> 16) & 0xFF));
        int greenDiff = Math.abs(((pixel >> 8) & 0xFF) - ((backgroundColor >> 8) & 0xFF));
        int blueDiff = Math.abs((pixel & 0xFF) - (backgroundColor & 0xFF));
        int mediaDiff = (redDiff + greenDiff + blueDiff)/3;
        return mediaDiff < tolerance;
    }

    private static boolean isIntervaloVermelho(int quantidadePontos) {

        return quantidadePontos > 100;
    }

    public static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
