package com.example.covidne;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeradorCsv {


    public static void criarArquivoCsvSeNaoExistir(String caminhoArquivo, String resultado, String id) {

        File file = new File(caminhoArquivo);
        boolean arquivoExiste = file.exists();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(caminhoArquivo, true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");

            if (!arquivoExiste) {
                writer.append("Data,Id,Resultado");
                writer.append(System.lineSeparator());
            }

            writer.append(buscarDataHoraAtual()).append(",").append(id).append(",").append(resultado);
            writer.append(System.lineSeparator());

            writer.flush();
            writer.close();

            System.out.println("Arquivo CSV criado/atualizado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String buscarDataHoraAtual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
