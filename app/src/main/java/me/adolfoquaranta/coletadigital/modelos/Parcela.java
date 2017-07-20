package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 18/10/16.
 */

public class Parcela {
    private Long id_Parcela, idFormulario_Parcela;
    private Integer tipo_Parcela, quantidadeNiveis_Parcela;
    private String nome_Parcela;

    public Parcela() {
    }

    public Long getId_Parcela() {
        return id_Parcela;
    }

    public void setId_Parcela(Long id_Parcela) {
        this.id_Parcela = id_Parcela;
    }

    public Integer getTipo_Parcela() {
        return tipo_Parcela;
    }

    public void setTipo_Parcela(Integer tipo_Parcela) {
        this.tipo_Parcela = tipo_Parcela;
    }

    public String getNome_Parcela() {
        return nome_Parcela;
    }

    public void setNome_Parcela(String nome_Parcela) {
        this.nome_Parcela = nome_Parcela;
    }

    public Integer getQuantidadeNiveis_Parcela() {
        return quantidadeNiveis_Parcela;
    }

    public void setQuantidadeNiveis_Parcela(Integer quantidadeNiveis_Parcela) {
        this.quantidadeNiveis_Parcela = quantidadeNiveis_Parcela;
    }

    public Long getIdFormulario_Parcela() {
        return idFormulario_Parcela;
    }

    public void setIdFormulario_Parcela(Long idFormulario_Parcela) {
        this.idFormulario_Parcela = idFormulario_Parcela;
    }


    @Override
    public String toString() {
        return "Parcela{" +
                "id_Parcela=" + id_Parcela +
                ", idFormulario_Parcela=" + idFormulario_Parcela +
                ", tipo_Parcela=" + tipo_Parcela +
                ", quantidadeNiveis_Parcela=" + quantidadeNiveis_Parcela +
                ", nome_Parcela='" + nome_Parcela + '\'' +
                '}';
    }
}
