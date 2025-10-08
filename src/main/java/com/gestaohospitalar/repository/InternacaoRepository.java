package src.main.java.com.gestaohospitalar.repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.main.java.com.gestaohospitalar.model.*;

public class InternacaoRepository {
    private static final String FILE_PATH = "internacoes.csv";

    public void salvar(List<Internacao> internacoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Internacao internacao : internacoes) {
                // Formato: cpfPaciente;crmMedico;numQuarto;dataEntrada;dataSaida;custoTotal;status
                String linha = String.join(";",
                        internacao.getPaciente().getCpf(),
                        internacao.getMedicoResponsavel().getCrm(),
                        String.valueOf(internacao.getQuarto().getNumero()),
                        internacao.getDataEntrada().toString(),
                        internacao.getDataSaida() != null ? internacao.getDataSaida().toString() : "null",
                        String.valueOf(internacao.getCustoTotal()),
                        internacao.getStatus().name()
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar internações: " + e.getMessage());
        }
    }

    public List<Internacao> carregar(List<Paciente> pacientes, List<Medico> medicos, List<Quarto> quartos) {
        List<Internacao> internacoes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    String cpfPaciente = dados[0];
                    String crmMedico = dados[1];
                    int numQuarto = Integer.parseInt(dados[2]);

                    Paciente paciente = pacientes.stream().filter(p -> p.getCpf().equals(cpfPaciente)).findFirst().orElse(null);
                    Medico medico = medicos.stream().filter(m -> m.getCrm().equals(crmMedico)).findFirst().orElse(null);
                    Quarto quarto = quartos.stream().filter(q -> q.getNumero() == numQuarto).findFirst().orElse(null);

                    if (paciente != null && medico != null && quarto != null) {
                    
                        Internacao internacao = new Internacao(paciente, medico, quarto);

                        internacao.setDataEntrada(LocalDateTime.parse(dados[3]));
                        internacao.setDataSaida(dados[4].equals("null") ? null : LocalDateTime.parse(dados[4]));
                        internacao.setCustoTotal(Double.parseDouble(dados[5]));
                        internacao.setStatus(StatusInternacao.valueOf(dados[6]));

                        quarto.setStatus(internacao.getStatus() == StatusInternacao.ATIVA ? StatusQuarto.OCUPADO : StatusQuarto.LIVRE);

                        internacoes.add(internacao);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de internações não encontrado.");
        }
        return internacoes;
    }
}