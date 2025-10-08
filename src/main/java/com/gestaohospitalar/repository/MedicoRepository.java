package src.main.java.com.gestaohospitalar.repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import src.main.java.com.gestaohospitalar.model.Especialidade;
import src.main.java.com.gestaohospitalar.model.Medico;

public class MedicoRepository {
    private static final String FILE_PATH = "medicos.csv";

    public void salvar(List<Medico> medicos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Medico medico : medicos) {
                String linha = String.join(";",
                        medico.getNome(),
                        medico.getCpf(),
                        medico.getDataDeNascimento().toString(),
                        medico.getEndereco(),
                        medico.getTelefone(),
                        medico.getEmail(),
                        medico.getCrm(),
                        medico.getEspecialidade().getNome(), // Salva o nome da especialidade
                        String.valueOf(medico.getCustoDaConsulta())
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar médicos: " + e.getMessage());
        }
    }

    public List<Medico> carregar(List<Especialidade> especialidadesDisponiveis) {
        List<Medico> medicos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 9) {
                    String nome = dados[0];
                    String cpf = dados[1];
                    LocalDate dataNascimento = LocalDate.parse(dados[2]);
                    String endereco = dados[3];
                    String telefone = dados[4];
                    String email = dados[5];
                    String crm = dados[6];
                    String nomeEspecialidade = dados[7];
                    double custoConsulta = Double.parseDouble(dados[8]);

                    // Encontra o objeto Especialidade correspondente pelo nome
                    Especialidade especialidade = especialidadesDisponiveis.stream()
                            .filter(e -> e.getNome().equals(nomeEspecialidade))
                            .findFirst()
                            .orElse(null);

                    if (especialidade != null) {
                        medicos.add(new Medico(nome, cpf, dataNascimento, endereco, telefone, email, crm, especialidade, custoConsulta));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de médicos não encontrado. Começando com lista vazia.");
        }
        return medicos;
    }
}