package src.main.java.com.gestaohospitalar.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import src.main.java.com.gestaohospitalar.model.Quarto;
import src.main.java.com.gestaohospitalar.model.StatusQuarto;
import src.main.java.com.gestaohospitalar.model.TipoQuarto;

public class QuartoRepository {
    private static final String FILE_PATH = "quartos.csv";

    public void salvar(List<Quarto> quartos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Quarto quarto : quartos) {
                // Formato: numero;tipo;custoDiaria;status
                String linha = String.join(";",
                        String.valueOf(quarto.getNumero()),
                        quarto.getTipo().name(), // Enum 
                        String.valueOf(quarto.getCustoDiaria()),
                        quarto.getStatus().name() // Enum
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar quartos: " + e.getMessage());
        }
    }

    public List<Quarto> carregar() {
        List<Quarto> quartos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    int numero = Integer.parseInt(dados[0]);
                    TipoQuarto tipo = TipoQuarto.valueOf(dados[1]);
                    double custoDiaria = Double.parseDouble(dados[2]);
                    StatusQuarto status = StatusQuarto.valueOf(dados[3]);

                    Quarto quarto = new Quarto(numero, tipo, custoDiaria);
                    quarto.setStatus(status);
                    quartos.add(quarto);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de quartos não encontrado. Começando com lista vazia.");
        }
        return quartos;
    }
}