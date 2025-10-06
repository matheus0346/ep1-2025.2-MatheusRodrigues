package src.main.java.com.gestaohospitalar.model;

public enum TipoQuarto {
    UTI("Unidade de Terapia Intensiva"),
    ENFERMARIA("Enfermaria"),
    PARTICULAR("Apartamento Particular");

    private final String descricao;

    TipoQuarto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}