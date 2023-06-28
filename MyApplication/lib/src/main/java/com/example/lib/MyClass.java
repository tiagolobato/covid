package com.example.lib;

import java.io.IOException;

public class MyClass {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world");
        RectEdgesDetector.detectRectEdges(10);
    }
}