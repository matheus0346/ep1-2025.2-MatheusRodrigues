package src.main.java.com.gestaohospitalar.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.gestaohospitalar.model.Consulta;
import src.main.java.com.gestaohospitalar.model.Medico;
import src.main.java.com.gestaohospitalar.model.Paciente;
import src.main.java.com.gestaohospitalar.model.StatusConsulta;

public class ConsultaRepository {
    private static final String FILE_PATH = "consultas.csv";

    public void salvar(List<Consulta> consultas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Consulta consulta : consultas) {
                // Formato: cpfPaciente;crmMedico;dataHora;local;status;valor
                String linha = String.join(";",
                        consulta.getPaciente().getCpf(),
                        consulta.getMedico().getCrm(),
                        consulta.getDataHora().toString(),
                        consulta.getLocal(),
                        consulta.getStatus().name(),
                        String.valueOf(consulta.getValor())
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    public List<Consulta> carregar(List<Paciente> pacientes, List<Medico> medicos) {
        List<Consulta> consultas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 6) {
                    String cpfPaciente = dados[0];
                    String crmMedico = dados[1];
                    
                    Paciente paciente = pacientes.stream().filter(p -> p.getCpf().equals(cpfPaciente)).findFirst().orElse(null);
                    Medico medico = medicos.stream().filter(m -> m.getCrm().equals(crmMedico)).findFirst().orElse(null);

                    if (paciente != null && medico != null) {
                        LocalDateTime dataHora = LocalDateTime.parse(dados[2]);
                        String local = dados[3];
                        StatusConsulta status = StatusConsulta.valueOf(dados[4]);
                        double valor = Double.parseDouble(dados[5]);

                        Consulta consulta = new Consulta(paciente, medico, dataHora, local);
                        // Atualiza os dados salvos (status e valor podem ter sido alterados)
                        consulta.setStatus(status);
                        consulta.setValor(valor); 
                        consultas.add(consulta);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de consultas ainda não encontrado.");
        }
        return consultas;
    }
}