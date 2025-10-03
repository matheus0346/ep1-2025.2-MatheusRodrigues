package src.main.java.com.gestaohospitalar.model;

import java.time.LocalDate;
import java.util.List;

public class Paciente extends Pessoa {

    private String numeroProntuario;
    private String tipoSanguineo;
    private Prioridade prioridade; //vou usar Enum 
    private PlanoSaude planoDeSaude; // Objeto
    private List<String> alergias;
    private List<String> medicamentosEmUso;

    public Paciente(String nome, String cpf, LocalDate dataDeNascimento, String endereco, 
                    String telefone, String email, String numeroProntuario, String tipoSanguineo,
                    Prioridade prioridade, PlanoSaude planoDeSaude, List<String> alergias, List<String> medicamentosEmUso) {
        
        super(nome, cpf, dataDeNascimento, endereco, telefone, email);
        
        this.numeroProntuario = numeroProntuario;
        this.tipoSanguineo = tipoSanguineo;
        this.prioridade = prioridade;
        this.planoDeSaude = planoDeSaude;
        this.alergias = alergias;
        this.medicamentosEmUso = medicamentosEmUso;
    }

    public String getNumeroProntuario() {
        return numeroProntuario;
    }

    public void setNumeroProntuario(String numeroProntuario) {
        this.numeroProntuario = numeroProntuario;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
    
    public PlanoSaude getPlanoDeSaude() {
        return planoDeSaude;
    }

    public void setPlanoDeSaude(PlanoSaude planoDeSaude) {
        this.planoDeSaude = planoDeSaude;
    }

    public List<String> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }

    public List<String> getMedicamentosEmUso() {
        return medicamentosEmUso;
    }

    public void setMedicamentosEmUso(List<String> medicamentosEmUso) {
        this.medicamentosEmUso = medicamentosEmUso;
    }
}

