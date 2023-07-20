package com.example.testecamera2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvCreator {


    public static void createCsvFileIfNotExists(String filePath,String resultado, String id) {

        File file = new File(filePath);
        boolean fileExists = file.exists();

        try {
            // Cria o FileOutputStream para gravar o arquivo, com a codificação UTF-8
            FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");

            // Se o arquivo não existir, escreve o cabeçalho
            if (!fileExists) {
                writer.append("Data,Id,Resultado");
                writer.append(System.lineSeparator());
            }

            // Gera os dados a serem inseridos


            // Escreve os dados no arquivo
            writer.append(getCurrentDate()).append(",").append(id).append(",").append(resultado);
            writer.append(System.lineSeparator());

            // Fecha o writer
            writer.flush();
            writer.close();

            System.out.println("Arquivo CSV criado/atualizado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentDate() {
        // Obtém a data do momento em formato "dd/MM/yyyy HH:mm:ss"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
