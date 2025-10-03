package src.main.java.com.gestaohospitalar.model;

public class PlanoSaude {
    private String nome;
    private double descontoConsulta;
    private boolean cobreInternacaoGratuitaCurtaDuracao;

    public PlanoSaude(String nome, double descontoConsulta, boolean cobreInternacaoGratuitaCurtaDuracao) {
        this.nome = nome;
        this.descontoConsulta = descontoConsulta;
        this.cobreInternacaoGratuitaCurtaDuracao = cobreInternacaoGratuitaCurtaDuracao;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDescontoConsulta() {
        return descontoConsulta;
    }

    public void setDescontoConsulta(double descontoConsulta) {
        this.descontoConsulta = descontoConsulta;
    }

    public boolean isCobreInternacaoGratuitaCurtaDuracao() {
        return cobreInternacaoGratuitaCurtaDuracao;
    }

    public void setCobreInternacaoGratuitaCurtaDuracao(boolean cobreInternacaoGratuitaCurtaDuracao) {
        this.cobreInternacaoGratuitaCurtaDuracao = cobreInternacaoGratuitaCurtaDuracao;
    }

    @Override
    public String toString() {
        return nome;
    }
}
