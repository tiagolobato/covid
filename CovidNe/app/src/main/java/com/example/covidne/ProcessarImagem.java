package com.example.covidne;




import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProcessarImagem {

    private static int vermelhoEscuro = Color.rgb(109, 60, 82);
    private static int vermelhoMedio = Color.rgb(138, 86, 109);
    private static int vermelhoClaro = Color.rgb(167, 157, 168);

    private static int[] coresAceitas = {
            vermelhoEscuro,
            vermelhoMedio,
            vermelhoClaro
    };
    public static String BuscarResultado(Bitmap imagemControle, Bitmap imagemTeste, Bitmap imageEntreCeT) {

        int corMedia = CorMedia(imageEntreCeT);
        List<Point> pontosC = PercorrerImagemInvetido(imagemControle, corMedia,9);
        List<Point> pontosT = PercorrerImagemInvetido(imagemTeste,corMedia,9);
        List<Point> pontosEntreCeT = PercorrerImagemInvetido(imageEntreCeT,corMedia,12);

        if(pontosEntreCeT.size() > 200){
            return Resultado.INVALIDO;
        }
        if(isIntervaloReagente(pontosT.size()) && isIntervaloReagente(pontosC.size())){
            return Resultado.POSITIVO;
        }
        if(!isIntervaloReagente(pontosT.size()) && isIntervaloReagente(pontosC.size())){
            return Resultado.NEGATIVO;
        }
        if(isIntervaloReagente(pontosT.size()) && !isIntervaloReagente(pontosC.size())){
            return Resultado.INVALIDO;
        }
        if(!isIntervaloReagente(pontosT.size()) && !isIntervaloReagente(pontosC.size())){
            return Resultado.INVALIDO;
        }
        return Resultado.INVALIDO;

    }



    private static List<Point>  PercorrerImagem(Bitmap imagem, int backgroundColor) {
        List<Point> rectanglePoints = new ArrayList<>();
        for (int x = 0; x < imagem.getWidth(); x++) {
            for (int y = 0; y < imagem.getHeight(); y++) {
                int pixel = imagem.getPixel(x, y);

                boolean isPontoReagente =  Arrays.stream(coresAceitas).anyMatch(i -> isPontoReagente(pixel, i, 3));
                if (isPontoReagente) {
                    rectanglePoints.add(new Point(x, y));
                }
            }
        }
        return rectanglePoints;
    }

    private static List<Point>  PercorrerImagemInvetido(Bitmap imagem, int corMedia, int tolerancia) {
        List<Point> pontosReagentes = new ArrayList<>();

        for (int x = 0; x < imagem.getWidth(); x++) {
            for (int y = 0; y < imagem.getHeight(); y++) {
                int pixel = imagem.getPixel(x, y);

                boolean isPontoReagente =  isPontoReagenteInvertido(pixel,corMedia,tolerancia);
                if (isPontoReagente) {
                    pontosReagentes.add(new Point(x, y));
                }
            }
        }
        return pontosReagentes;
    }



    private static boolean isPontoReagente(int pixel, int backgroundColor, int tolerancia) {
        int redDiff = Math.abs(((pixel >> 16) & 0xFF) - ((backgroundColor >> 16) & 0xFF));
        int greenDiff = Math.abs(((pixel >> 8) & 0xFF) - ((backgroundColor >> 8) & 0xFF));
        int blueDiff = Math.abs((pixel & 0xFF) - (backgroundColor & 0xFF));
        int mediaDiff = (redDiff + greenDiff + blueDiff)/3;
        return mediaDiff < tolerancia;
    }

    private static boolean isPontoReagenteInvertido(int pixel, int backgroundColor, int tolerancia) {
        int redDiff = Math.abs(((pixel >> 16) & 0xFF) - ((backgroundColor >> 16) & 0xFF));
        int greenDiff = Math.abs(((pixel >> 8) & 0xFF) - ((backgroundColor >> 8) & 0xFF));
        int blueDiff = Math.abs((pixel & 0xFF) - (backgroundColor & 0xFF));
        int mediaDiff = (redDiff + greenDiff + blueDiff)/3;
        return mediaDiff > tolerancia;
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

    private static boolean isIntervaloReagente(int quantidadePontos) {

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
