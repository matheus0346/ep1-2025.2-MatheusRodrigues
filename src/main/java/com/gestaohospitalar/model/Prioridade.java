package src.main.java.com.gestaohospitalar.model;

public enum Prioridade {
    EMERGENCIA(1, "Emergência (Vermelho)"),
    URGENTE(2, "Urgente (Laranja)"),
    NORMAL(3, "Normal (Amarelo)"),
    PREFERENCIAL(4, "Preferencial (Azul)"),
    NAO_URGENTE(5, "Não Urgente (Verde)");

    private final int codigo;
    private final String descricao;

    Prioridade(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Prioridade valueOfCodigo(int codigo) {
        for (Prioridade prioridade : values()) {
            if (prioridade.getCodigo() == codigo) {
                return prioridade;
            }
        }

        throw new IllegalArgumentException("Código de prioridade inválido: " + codigo);
    }
}