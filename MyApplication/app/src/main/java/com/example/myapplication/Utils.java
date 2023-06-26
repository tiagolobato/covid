package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Utils {
    public static boolean isRedLine(Bitmap bitmap, int startY, int endY, int tolerance) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int pixelCount = 0;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int y = startY; y <= endY; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);

                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                redSum += red;
                greenSum += green;
                blueSum += blue;
                pixelCount++;
            }
        }

        int averageRed = redSum / pixelCount;
        int averageGreen = greenSum / pixelCount;
        int averageBlue = blueSum / pixelCount;

        // Verifica se a média de vermelho está dentro da faixa de tolerância
        return (averageRed >= 46 - tolerance && averageRed <= 46 &&
                averageGreen >= 32 - tolerance && averageGreen <= 32 &&
                averageBlue >= 107 - tolerance && averageBlue <= 107);
    }


}
