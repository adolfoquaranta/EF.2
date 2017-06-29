package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 18/10/16.
 */

public class NivelFator {
    private Long id_NivelFator, idFator_NivelFator;
    private Integer numero_NivelFator;
    private String nome_NivelFator;

    public NivelFator() {
    }

    public Long getId_NivelFator() {
        return id_NivelFator;
    }

    public void setId_NivelFator(Long id_NivelFator) {
        this.id_NivelFator = id_NivelFator;
    }

    public Long getIdFator_NivelFator() {
        return idFator_NivelFator;
    }

    public void setIdFator_NivelFator(Long idFator_NivelFator) {
        this.idFator_NivelFator = idFator_NivelFator;
    }

    public Integer getNumero_NivelFator() {
        return numero_NivelFator;
    }

    public void setNumero_NivelFator(Integer numero_NivelFator) {
        this.numero_NivelFator = numero_NivelFator;
    }

    public String getNome_NivelFator() {
        return nome_NivelFator;
    }

    public void setNome_NivelFator(String nome_Fator) {
        this.nome_NivelFator = nome_Fator;
    }

    @Override
    public String toString() {
        return "NivelFator{" +
                "id_NivelFator=" + id_NivelFator +
                ", idFator_NivelFator=" + idFator_NivelFator +
                ", numero_NivelFator=" + numero_NivelFator +
                ", nome_NivelFator='" + nome_NivelFator + '\'' +
                '}';
    }
}
