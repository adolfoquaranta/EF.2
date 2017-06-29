package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 18/10/16.
 */

public class Fator {
    private Long id_Fator, idFormulario_Fator;
    private Integer tipo_Fator, quantidadeNiveis_Fator;
    private String nome_Fator;

    public Fator() {
    }

    public Long getId_Fator() {
        return id_Fator;
    }

    public void setId_Fator(Long id_Fator) {
        this.id_Fator = id_Fator;
    }

    public Integer getTipo_Fator() {
        return tipo_Fator;
    }

    public void setTipo_Fator(Integer tipo_Fator) {
        this.tipo_Fator = tipo_Fator;
    }

    public String getNome_Fator() {
        return nome_Fator;
    }

    public void setNome_Fator(String nome_Fator) {
        this.nome_Fator = nome_Fator;
    }

    public Integer getQuantidadeNiveis_Fator() {
        return quantidadeNiveis_Fator;
    }

    public void setQuantidadeNiveis_Fator(Integer quantidadeNiveis_Fator) {
        this.quantidadeNiveis_Fator = quantidadeNiveis_Fator;
    }

    public Long getIdFormulario_Fator() {
        return idFormulario_Fator;
    }

    public void setIdFormulario_Fator(Long idFormulario_Fator) {
        this.idFormulario_Fator = idFormulario_Fator;
    }


    @Override
    public String toString() {
        return "Fator{" +
                "id_Fator=" + id_Fator +
                ", idFormulario_Fator=" + idFormulario_Fator +
                ", tipo_Fator=" + tipo_Fator +
                ", quantidadeNiveis_Fator=" + quantidadeNiveis_Fator +
                ", nome_Fator='" + nome_Fator + '\'' +
                '}';
    }
}
