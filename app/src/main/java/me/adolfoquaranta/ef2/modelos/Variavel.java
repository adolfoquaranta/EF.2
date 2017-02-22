package me.adolfoquaranta.ef2.modelos;

/**
 * Created by adolfo on 05/11/16.
 */

public class Variavel {
    private Long id_Variavel, idModelo_Variavel;
    private Integer tipo_Variavel;
    private String nome_Variavel;

    public Variavel(Long id_Variavel, Long idModelo_Variavel, Integer tipo_Variavel, String nome_Variavel) {
        this.id_Variavel = id_Variavel;
        this.idModelo_Variavel = idModelo_Variavel;
        this.tipo_Variavel = tipo_Variavel;
        this.nome_Variavel = nome_Variavel;
    }

    public Variavel() {
    }

    public Long getId_Variavel() {
        return id_Variavel;
    }

    public void setId_Variavel(Long id_Variavel) {
        this.id_Variavel = id_Variavel;
    }

    public Long getIdModelo_Variavel() {
        return idModelo_Variavel;
    }

    public void setIdModelo_Variavel(Long idModelo_Variavel) {
        this.idModelo_Variavel = idModelo_Variavel;
    }

    public Integer getTipo_Variavel() {
        return tipo_Variavel;
    }

    public void setTipo_Variavel(Integer tipo_Variavel) {
        this.tipo_Variavel = tipo_Variavel;
    }

    public String getNome_Variavel() {
        return nome_Variavel;
    }

    public void setNome_Variavel(String nome_Variavel) {
        this.nome_Variavel = nome_Variavel;
    }

    @Override
    public String toString() {
        return "Variavel{" +
                "id_Variavel=" + id_Variavel +
                ", idModelo_Variavel=" + idModelo_Variavel +
                ", tipo_Variavel=" + tipo_Variavel +
                ", nome_Variavel='" + nome_Variavel + '\'' +
                '}';
    }
}
