package com.example.lib;


import org.w3c.dom.css.RGBColor;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class RectEdgesDetector {
    private static double yScale = 1;
    private static double xScale = 1;
    private static int left = (int) (310 * yScale);
    private static int top = 300;
    private static int right = (int) (390 * yScale);
    private static int bottom = 900;

    private static int topC = (int) (520 * xScale);
    private static int bottomC = (int) (540 * xScale);

    private static int topT = (int) (580 * xScale);

    private static int bottomT = (int) (600 * xScale);

    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    public static String detectRectEdges(int tolerance) throws IOException {
        File imageFile = new File("C:\\Users\\tiago\\AndroidStudioProjects\\MyApplication\\lib\\src\\main\\java\\com\\example\\lib\\pic4.jpg");

        BufferedImage image = resizeImage(ImageIO.read(imageFile),1504,720);
        File outputfile = new File("image.jpg");
        ImageIO.write(image, "jpg", outputfile);
        int width = image.getWidth();
        int height = image.getHeight();
        /*
        int xTopo1 = 107;
        int yEsquerdo = 200;
        int yDireito = 280;
        int xBaixo1 = 120;
        int xTopo2 = 169;
        int xBaixo2 = 184;
        */
        int xTopo1 = 733;
        int yEsquerdo = 424;
        int yDireito = 497;
        int xBaixo1 = 766;
        int xTopo2 = 890;
        int xBaixo2 = 915;
        Point ponto1Linha1 = new Point(topC,left);
        Point ponto2Linha1 = new Point(topC,right);

        Point ponto1Linha2 = new Point(bottomC,left);
        Point ponto2Linha2 = new Point(bottomC,right);

        Point ponto1Linha3 = new Point(topT,left);
        Point ponto2Linha3 = new Point(topT,right);

        Point ponto1Linha4 = new Point(bottomT,left);
        Point ponto2Linha4 = new Point(bottomT,right);

        Intervalo intervaloC = new Intervalo(new Linha(ponto1Linha1,ponto2Linha1), new Linha(ponto1Linha2,ponto2Linha2));
        Intervalo intervaloT = new Intervalo(new Linha(ponto1Linha3,ponto2Linha3), new Linha(ponto1Linha4,ponto2Linha4));


        int backgroundColor = new Color(144, 27, 36).getRGB();

        List<Point> pontosC = PercorrerIntervalo(tolerance, image, intervaloC, backgroundColor);
        List<Point> pontosT = PercorrerIntervalo(tolerance, image, intervaloT, backgroundColor);
        String resultado = "";
        if(isIntervaloVermelho(pontosT.size()) && isIntervaloVermelho(pontosC.size())){
            resultado = "Positivo";
        }
        if(!isIntervaloVermelho(pontosT.size()) && isIntervaloVermelho(pontosC.size())){
            resultado = "Negativo";
        }
        if(isIntervaloVermelho(pontosT.size()) && !isIntervaloVermelho(pontosC.size())){
            resultado = "Invalido";
        }
        if(!isIntervaloVermelho(pontosT.size()) && !isIntervaloVermelho(pontosC.size())){
            resultado = "Invalido";
        }
        return resultado;
    }

    private static List<Point>  PercorrerIntervalo(int tolerance, BufferedImage image, Intervalo intervaloT, int backgroundColor) {
        List<Point> rectanglePoints = new ArrayList<>();
        for (int x = intervaloT.PontoInicial.x; x < intervaloT.PontoFinal.x; x++) {
            for (int y = intervaloT.PontoInicial.y; y < intervaloT.PontoFinal.y; y++) {
                int pixel = image.getRGB(x, y);

                if (isPontoVermelho(pixel, backgroundColor, 10)) {
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

    public static int formaLinha(List<Point> listaPontos) {
        int quantidadeContagem = listaPontos.size()/7;
        int distanciaX = listaPontos.size()/5;
        int yAnterior = -1;
        int contador = 0;
        List<Integer> linhasX = new ArrayList<>();

        for (Point ponto : listaPontos) {
            int x = ponto.x;
            int y = ponto.y;

            if (yAnterior == -1) {
                yAnterior = y;
                contador = 1;
            } else if (y == yAnterior + 1) {
                contador++;
                if (contador >= quantidadeContagem) {
                    contador = 1;
                    if(linhasX.stream().anyMatch(xSalvo -> Math.abs(xSalvo - x) < 10)){
                        continue;
                    }
                    linhasX.add((x));


                }
            } else {

                contador = 1;
            }
            yAnterior = y;
        }

        return linhasX.size();
    }






    private static int calculateBackgroundColor(BufferedImage bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        long totalRed = 0;
        long totalGreen = 0;
        long totalBlue = 0;
        long pixelCount = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bitmap.getRGB(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                totalRed += red;
                totalGreen += green;
                totalBlue += blue;

                pixelCount++;
            }
        }

        int averageRed = (int) (totalRed / pixelCount);
        int averageGreen = (int) (totalGreen / pixelCount);
        int averageBlue = (int) (totalBlue / pixelCount);

        return (averageRed << 16) | (averageGreen << 8) | averageBlue;
    }

    private static boolean isRectanglePixel(int pixel, int backgroundColor, int tolerance) {
        int redDiff = Math.abs(((pixel >> 16) & 0xFF) - ((backgroundColor >> 16) & 0xFF));
        int greenDiff = Math.abs(((pixel >> 8) & 0xFF) - ((backgroundColor >> 8) & 0xFF));
        int blueDiff = Math.abs((pixel & 0xFF) - (backgroundColor & 0xFF));
        int mediaDiff = (redDiff + greenDiff + blueDiff)/3;
        return mediaDiff > tolerance;
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

    public static class Linha {
        public final Point Ponto1;
        public final Point Ponto2;

        public Linha(Point ponto1, Point ponto2) {
            this.Ponto1 = ponto1;
            this.Ponto2 = ponto2;
        }
    }

    public static class Intervalo {
        public final Linha Linha1;
        public final Linha Linha2;

        public final Point PontoInicial;
        public final Point PontoFinal;
        public Intervalo(Linha x, Linha y) {
            this.Linha1 = x;
            this.Linha2 = y;
            this.PontoInicial = this.Linha1.Ponto1;
            this.PontoFinal = this.Linha2.Ponto2;
        }
    }
}
