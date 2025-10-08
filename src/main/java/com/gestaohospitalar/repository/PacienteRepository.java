package src.main.java.com.gestaohospitalar.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.main.java.com.gestaohospitalar.model.Paciente;
import src.main.java.com.gestaohospitalar.model.PlanoSaude;
import src.main.java.com.gestaohospitalar.model.Prioridade;

public class PacienteRepository {
    private static final String FILE_PATH = "pacientes.csv";

    // Método para salvar a lista de pacientes
    public void salvar(List<Paciente> pacientes, List<PlanoSaude> planosDisponiveis) { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Paciente paciente : pacientes) {
            
                String alergiasStr = String.join("|", paciente.getAlergias());
                String medicamentosStr = String.join("|", paciente.getMedicamentosEmUso());

                // O formato:
                // nome;cpf;dataNascimento;endereco;telefone;email;numProntuario;tipoSanguineo;prioridade;nomePlanoSaude;alergias;medicamentos
                String linha = String.join(";",
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getDataDeNascimento().toString(), 
                        paciente.getEndereco(),
                        paciente.getTelefone(),
                        paciente.getEmail(),
                        paciente.getNumeroProntuario(),
                        paciente.getTipoSanguineo(),
                        paciente.getPrioridade().name(), // Enum 
                        paciente.getPlanoDeSaude() != null ? paciente.getPlanoDeSaude().getNome() : "NENHUM", // Objeto 
                        alergiasStr,
                        medicamentosStr
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    // Método para carregar a lista de pacientes
    public List<Paciente> carregar(List<PlanoSaude> planosDisponiveis) { 
        List<Paciente> pacientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length == 12) {
                    
                    String nome = dados[0];
                    String cpf = dados[1];
                    LocalDate dataNascimento = LocalDate.parse(dados[2]);
                    String endereco = dados[3];
                    String telefone = dados[4];
                    String email = dados[5];
                    String numProntuario = dados[6];
                    String tipoSanguineo = dados[7];
                    Prioridade prioridade = Prioridade.valueOf(dados[8]);

                
                    PlanoSaude planoSaude = null;
                    for (PlanoSaude p : planosDisponiveis) {
                        if (p.getNome().equals(dados[9])) {
                            planoSaude = p;
                            break;
                        }
                    }

                
                    List<String> alergias = dados[10].isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(dados[10].split("\\|")));
                    List<String> medicamentos = dados[11].isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(dados[11].split("\\|")));

                    pacientes.add(new Paciente(nome, cpf, dataNascimento, endereco, telefone, email, numProntuario,
                                                tipoSanguineo, prioridade, planoSaude, alergias, medicamentos));
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de pacientes ainda não encontrado. Será criado um novo.");
        }
        return pacientes;
    }
}