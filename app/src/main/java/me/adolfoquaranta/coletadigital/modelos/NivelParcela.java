package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 18/10/16.
 */

public class NivelParcela {
    private Long id_NivelParcela, idParcela_NivelParcela;
    private Integer numero_NivelParcela;
    private String nome_NivelParcela;

    public NivelParcela() {
    }

    public Long getId_NivelParcela() {
        return id_NivelParcela;
    }

    public void setId_NivelParcela(Long id_NivelParcela) {
        this.id_NivelParcela = id_NivelParcela;
    }

    public Long getIdParcela_NivelParcela() {
        return idParcela_NivelParcela;
    }

    public void setIdParcela_NivelParcela(Long idParcela_NivelParcela) {
        this.idParcela_NivelParcela = idParcela_NivelParcela;
    }

    public Integer getNumero_NivelParcela() {
        return numero_NivelParcela;
    }

    public void setNumero_NivelParcela(Integer numero_NivelParcela) {
        this.numero_NivelParcela = numero_NivelParcela;
    }

    public String getNome_NivelParcela() {
        return nome_NivelParcela;
    }

    public void setNome_NivelParcela(String nome_Fator) {
        this.nome_NivelParcela = nome_Fator;
    }

    @Override
    public String toString() {
        return "NivelParcela{" +
                "id_NivelParcela=" + id_NivelParcela +
                ", idFator_NivelParcela=" + idParcela_NivelParcela +
                ", numero_NivelParcela=" + numero_NivelParcela +
                ", nome_NivelParcela='" + nome_NivelParcela + '\'' +
                '}';
    }
}
