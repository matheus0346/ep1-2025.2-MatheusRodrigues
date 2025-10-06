package src.main.java.com.gestaohospitalar.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Internacao {

    private Paciente paciente;
    private Medico medicoResponsavel;
    private Quarto quarto;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private double custoTotal;
    private StatusInternacao status;

    public Internacao(Paciente paciente, Medico medicoResponsavel, Quarto quarto) {
        if (!quarto.estaLivre()) {
            throw new IllegalStateException("Erro: O quarto " + quarto.getNumero() + " já está ocupado.");
        }
        
        this.paciente = paciente;
        this.medicoResponsavel = medicoResponsavel;
        this.quarto = quarto;
        this.dataEntrada = LocalDateTime.now();
        this.status = StatusInternacao.ATIVA;
        this.quarto.ocupar();
    }

    //Dar Alta no Paciente
    public void darAlta() {
        if (this.status == StatusInternacao.ATIVA) {
            this.dataSaida = LocalDateTime.now();
            this.status = StatusInternacao.FINALIZADA;
            this.custoTotal = calcularCustoTotal();
            this.quarto.liberar(); // Libera o quarto
            System.out.println("Alta realizada para " + this.paciente.getNome() + ". Custo total: R$" + String.format("%.2f", this.custoTotal));
        } else {
            System.err.println("Esta internação não está ativa, não é possível dar alta.");
        }
    }
    
    // Calcular o Custo
    private double calcularCustoTotal() {
        if (this.dataSaida == null) {
            return 0;
        }

        long dias = ChronoUnit.DAYS.between(dataEntrada.toLocalDate(), dataSaida.toLocalDate());
        if (dias == 0) {
            dias = 1; // Mínimo de 1 diária
        }

        // Verifica a regra do plano de saúde para internação gratuita
        PlanoSaude plano = paciente.getPlanoDeSaude();
        if (plano != null && plano.isCobreInternacaoGratuitaCurtaDuracao() && dias < 7) {
            return 0.0; // para Internação gratuita
        }

        return dias * this.quarto.getCustoDiaria();
    }
    
    public long getDuracaoEmDias() {
        if (dataSaida != null) {
            return ChronoUnit.DAYS.between(dataEntrada.toLocalDate(), dataSaida.toLocalDate());
        }
        return ChronoUnit.DAYS.between(dataEntrada.toLocalDate(), LocalDate.now());
    }

    // --- Getters e Setters ---
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Medico getMedicoResponsavel() { return medicoResponsavel; }
    public void setMedicoResponsavel(Medico medicoResponsavel) { this.medicoResponsavel = medicoResponsavel; }
    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }
    public LocalDateTime getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }
    public double getCustoTotal() { return custoTotal; }
    public void setCustoTotal(double custoTotal) { this.custoTotal = custoTotal; }
    public StatusInternacao getStatus() { return status; }
    public void setStatus(StatusInternacao status) { this.status = status; }

    @Override
    public String toString() {
        return "Internacao [" +
               "Paciente: " + paciente.getNome() +
               ", Médico: " + medicoResponsavel.getNome() +
               ", Quarto: " + quarto.getNumero() +
               ", Entrada: " + dataEntrada +
               ", Status: " + status +
               ']';
    }
}