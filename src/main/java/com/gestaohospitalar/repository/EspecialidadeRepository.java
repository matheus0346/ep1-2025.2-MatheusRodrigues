package src.main.java.com.gestaohospitalar.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import src.main.java.com.gestaohospitalar.model.Especialidade;

public class EspecialidadeRepository {
    private static final String FILE_PATH = "especialidades.csv";

    public void salvar(List<Especialidade> especialidades) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Especialidade esp : especialidades) {
                bw.write(esp.getNome());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar especialidades: " + e.getMessage());
        }
    }

    public List<Especialidade> carregar() {
        List<Especialidade> especialidades = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String nomeEspecialidade;
            while ((nomeEspecialidade = br.readLine()) != null) {
                especialidades.add(new Especialidade(nomeEspecialidade));
            }
        } catch (IOException e) {
            System.out.println("Arquivo de especialidades não encontrado. Começando com lista vazia.");
        }
        return especialidades;
    }
}