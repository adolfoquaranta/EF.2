package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 05/11/16.
 */

public class Variavel {
    private Long id_Variavel, idFormulario_Variavel;
    private Integer tipo_Variavel;
    private String nome_Variavel;

    public Variavel() {
    }

    public Long getId_Variavel() {
        return id_Variavel;
    }

    public void setId_Variavel(Long id_Variavel) {
        this.id_Variavel = id_Variavel;
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

    public Long getIdFormulario_Variavel() {
        return idFormulario_Variavel;
    }

    public void setIdFormulario_Variavel(Long idFormulario_Variavel) {
        this.idFormulario_Variavel = idFormulario_Variavel;
    }

    @Override
    public String toString() {
        return "Variavel{" +
                "id_Variavel=" + id_Variavel +
                ", idFormulario_Variavel=" + idFormulario_Variavel +
                ", tipo_Variavel=" + tipo_Variavel +
                ", nome_Variavel='" + nome_Variavel + '\'' +
                '}';
    }
}
