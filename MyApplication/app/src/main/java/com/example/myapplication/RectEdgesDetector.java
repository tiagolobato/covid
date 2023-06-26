package com.example.myapplication;

import android.graphics.Bitmap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RectEdgesDetector {
    public static int detectRectEdges(Bitmap bitmap, int tolerance) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int backgroundColor = calculateBackgroundColor(bitmap);
        List<Point> rectanglePoints = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bitmap.getPixel(x, y);

                if (isRectanglePixel(pixel, backgroundColor, tolerance)) {
                    rectanglePoints.add(new Point(x, y));
                    int redDiff = Math.abs(((pixel >> 16) & 0xFF));
                    int greenDiff = Math.abs(((pixel >> 8) & 0xFF) );
                    int blueDiff = Math.abs((pixel & 0xFF) );
                    int teste =1;
                }
            }
        }
        int numerolinhas = formaLinha(rectanglePoints);
        printImageWithRectangle(bitmap,rectanglePoints);
        return numerolinhas;
    }
    public static int formaLinha(List<RectEdgesDetector.Point> listaPontos) {
        int quantidadeContagem = listaPontos.size()/7;
        int distanciaX = listaPontos.size()/5;
        int yAnterior = -1;
        int contador = 0;
        List<Integer> linhasX = new ArrayList<>();

        for (RectEdgesDetector.Point ponto : listaPontos) {
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


    public static void printImage(Bitmap bitmap, List<RectEdgesDetector.Point> rectEdges) {
        String filePath = "C:\\Users\\tiago\\AndroidStudioProjects\\MyApplication\\app\\src\\main\\java\\com\\example\\myapplication\\saida.txt";
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        StringBuilder stringBuilder = new StringBuilder();

        // Preencher imagem com caracteres '.'
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                stringBuilder.append('.');
            }
            stringBuilder.append("\n");
        }

        // Marcar retângulo na imagem
        for (RectEdgesDetector.Point point : rectEdges) {
            int x = point.x;
            int y = point.y;
            if (x >= 0 && x < width && y >= 0 && y < height) {
                int position = y * (width + 1) + x; // +1 para contar o caractere de nova linha
                stringBuilder.setCharAt(position, 'X');
            }
        }

        // Salvar imagem em um arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(stringBuilder.toString());
            System.out.println("Imagem com retângulo salva no arquivo: " + filePath);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a imagem com retângulo: " + e.getMessage());
        }
    }

    public static void printImageWithRectangle(Bitmap bitmap, List<RectEdgesDetector.Point> rectEdges) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        char[][] imageMatrix = new char[height][width];

        // Preencher imagem com caracteres '.'
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imageMatrix[i][j] = '.';
            }
        }

        // Marcar retângulo na imagem
        for (RectEdgesDetector.Point point : rectEdges) {
            int x = point.x;
            int y = point.y;
            if (x >= 0 && x < width && y >= 0 && y < height) {
                imageMatrix[y][x] = 'X';
            }
        }

        // Imprimir imagem no console
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(imageMatrix[i][j]);
            }
            System.out.println();
        }
    }


    private static int calculateBackgroundColor(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        long totalRed = 0;
        long totalGreen = 0;
        long totalBlue = 0;
        long pixelCount = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bitmap.getPixel(x, y);
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

    public static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
