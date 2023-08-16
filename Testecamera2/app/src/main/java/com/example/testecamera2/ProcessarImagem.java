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
import java.util.Arrays;
import java.util.List;


public class ProcessarImagem {

    private static int strong_red6 = Color.rgb(113, 80, 97);
    private static int strong_red5 = Color.rgb(118, 90, 104);
    private static int strong_red4 = Color.rgb(112, 88, 101);
    private static int strong_red3 = Color.rgb(124, 91, 108);
    private static int strong_red2 = Color.rgb(127, 106, 115);
    private static int strong_red = Color.rgb(109, 60, 82);
    private static int medium_red = Color.rgb(138, 86, 109);
    private static int light_red = Color.rgb(167, 157, 168);

    private static int light_red2 = Color.rgb(198, 185, 179);
    private static int[] acceptedColours = {
            strong_red,
            medium_red,
            light_red,
            light_red2
    };
    public static String GetResultado(Bitmap imageC, Bitmap imageT,Bitmap imageEntreCeT) {
        int backgroundColor = Color.rgb(167, 60, 78);

        int corMedia = CorMedia(imageEntreCeT);
        List<Point> pontosC = PercorrerImagemInvetido(imageC, corMedia,9);
        List<Point> pontosT = PercorrerImagemInvetido(imageT,corMedia,9);
        List<Point> pontosEntreCeT = PercorrerImagemInvetido(imageEntreCeT,corMedia,12);

        if(pontosEntreCeT.size() > 200){
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

                boolean hasRed =  Arrays.stream(acceptedColours).anyMatch(i -> isPontoVermelho(pixel, i, 3));
                if (hasRed) {
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

    private static List<Point>  PercorrerImagemInvetido(Bitmap image, int corMedia, int tolerancia) {
        List<Point> rectanglePoints = new ArrayList<>();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getPixel(x, y);

                boolean hasRed =  isPontoVermelhoInvertido(pixel,corMedia,tolerancia);
                if (hasRed) {
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

    private static boolean isPontoVermelhoInvertido(int pixel, int backgroundColor, int tolerance) {
        int redDiff = Math.abs(((pixel >> 16) & 0xFF) - ((backgroundColor >> 16) & 0xFF));
        int greenDiff = Math.abs(((pixel >> 8) & 0xFF) - ((backgroundColor >> 8) & 0xFF));
        int blueDiff = Math.abs((pixel & 0xFF) - (backgroundColor & 0xFF));
        int mediaDiff = (redDiff + greenDiff + blueDiff)/3;
        return mediaDiff > tolerance;
    }

    private static int CorMedia(Bitmap image) {
        long sumr = 0, sumg = 0, sumb = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getPixel(x, y);

                sumr += Math.abs(((pixel >> 16) & 0xFF));
                sumg += Math.abs(((pixel >> 8) & 0xFF) );
                sumb += Math.abs((pixel & 0xFF) );
            }
        }
        int num = image.getWidth() * image.getHeight();
        return Color.rgb((int) sumr / num, (int)sumg / num, (int)sumb / num);
    }

    private static boolean isIntervaloVermelho(int quantidadePontos) {

        return quantidadePontos > 50;
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
