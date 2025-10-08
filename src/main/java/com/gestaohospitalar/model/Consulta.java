package src.main.java.com.gestaohospitalar.model;

import java.time.LocalDateTime;
import java.util.List;

public class Consulta {

    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private StatusConsulta status;
    private double valor;
    private String diagnostico;
    private List<String> prescricao;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = StatusConsulta.AGENDADA;
        this.valor = calcularValorConsulta(); // O valor já será calculado com o novo desconto
    }

    // --- LÓGICA DE NEGÓCIO ATUALIZADA ---
    private double calcularValorConsulta() {
        double custoBase = this.medico.getCustoDaConsulta();
        double valorAposPlano = custoBase;

        // 1. Aplica o desconto do Plano de Saúde (se houver)
        if (this.paciente.getPlanoDeSaude() != null) {
            double descontoPlano = this.paciente.getPlanoDeSaude().getDescontoConsulta();
            valorAposPlano = custoBase * (1 - descontoPlano);
        }
        
        // 2. Aplica um desconto ADICIONAL de 20% se o paciente tiver 60 anos ou mais
        if (this.paciente.getIdade() >= 60) {
            double descontoIdoso = 0.20; // 20% de desconto
            // O desconto de idoso é aplicado sobre o valor que sobrou após o desconto do plano
            return valorAposPlano * (1 - descontoIdoso);
        }
        
        // Se não for idoso, retorna apenas o valor com o desconto do plano
        return valorAposPlano;
    }
    
    // (O resto da classe, com todos os Getters e Setters, continua o mesmo)
    // ...
    public Paciente getPaciente() {
        return paciente; 
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public StatusConsulta getStatus() {
        return status;
    }
    public void setStatus(StatusConsulta status) {
        this.status = status;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    public List<String> getPrescricao() {
        return prescricao;
    }
    public void setPrescricao(List<String> prescricao) {
        this.prescricao = prescricao;
    }

    @Override
    public String toString() {
        return "Consulta [" +
                "Paciente: " + paciente.getNome() +
                ", Medico: " + medico.getNome() +
                ", Data/Hora: " + dataHora +
                ", Local: " + local +
                ", Status: " + status +
                ", Valor: R$" + String.format("%.2f", valor) + // Exibe o valor já com desconto
                ']';
    }
}