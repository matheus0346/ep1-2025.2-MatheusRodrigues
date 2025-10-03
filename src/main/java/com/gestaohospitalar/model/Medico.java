package src.main.java.com.gestaohospitalar.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Pessoa {

    private String crm;
    private Especialidade especialidade;
    private double custoDaConsulta;
    private List<Consulta> agendaDeHorarios;

    public Medico(String nome, String cpf, LocalDate dataDeNascimento, String endereco, String telefone, String email,
                  String crm, Especialidade especialidade, double custoDaConsulta) {
        
        super(nome, cpf, dataDeNascimento, endereco, telefone, email);

        this.crm = crm;
        this.especialidade = especialidade;
        this.custoDaConsulta = custoDaConsulta;
        this.agendaDeHorarios = new ArrayList<>();
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public double getCustoDaConsulta() {
        return custoDaConsulta;
    }

    public void setCustoDaConsulta(double custoDaConsulta) {
        this.custoDaConsulta = custoDaConsulta;
    }

    public List<Consulta> getAgendaDeHorarios() {
        return agendaDeHorarios;
    }

    public void adicionarConsultaNaAgenda(Consulta consulta) {
        this.agendaDeHorarios.add(consulta);
    }
}