package src.main.java.com.gestaohospitalar.model;

import java.time.LocalDate;
import java.time.Period;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private String endereco;
    private String telefone;
    private String email;
    private int idade;

    public Pessoa(String nome, String cpf, LocalDate dataDeNascimento, String endereco, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.idade = calcularIdade();
    }

    private int calcularIdade() {
        if (this.dataDeNascimento == null) {
            return 0;
        }
        return Period.between(this.dataDeNascimento, LocalDate.now()).getYears();
    }

//GETTERS

    public String getNome() {
        return nome; 
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() { return email;
    }

    public int getIdade() {
        return idade;
    }

//SETTERS  

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        this.idade = calcularIdade(); // Importante: recalcula a idade
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Intencionalmente NÃO existe um setIdade(int idade)
}