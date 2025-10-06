package src.main.java.com.gestaohospitalar.model;

public class Quarto {

    private int numero;
    private TipoQuarto tipo;
    private double custoDiaria;
    private StatusQuarto status;

    // --- Construtor Completo ---
    public Quarto(int numero, TipoQuarto tipo, double custoDiaria) {
        this.numero = numero;
        this.tipo = tipo;
        this.custoDiaria = custoDiaria;
        this.status = StatusQuarto.LIVRE;
    }

    // -- Getters e Setters --

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public double getCustoDiaria() {
        return custoDiaria;
    }

    public void setCustoDiaria(double custoDiaria) {
        this.custoDiaria = custoDiaria;
    }

    public StatusQuarto getStatus() {
        return status;
    }

    public void setStatus(StatusQuarto status) {
        this.status = status;
    }
    
    // -- Métodos de Conveniência --

    public boolean estaLivre() {
        return this.status == StatusQuarto.LIVRE;
    }

    public void ocupar() {
        this.status = StatusQuarto.OCUPADO;
    }

    public void liberar() {
        this.status = StatusQuarto.LIVRE;
    }
    @Override
    public String toString() {
        return "Quarto [Número: " + numero +
               ", Tipo: " + tipo +
               ", Custo Diária: R$" + String.format("%.2f", custoDiaria) +
               ", Status: " + status + "]";
    }
}