package me.adolfoquaranta.ef2.modelos;

/**
 * Created by adolfo on 18/10/16.
 */

public class Tratamento {
    private Long id_Tratamento, idForm_Tratamento;
    private Integer tipo_Tratamento;
    private String nome_Tratamento;

    public Tratamento() {
    }

    public Tratamento(Long id_Tratamento, Long idForm_Tratamento, Integer tipo_Tratamento, String nome_Tratamento) {
        this.id_Tratamento = id_Tratamento;
        this.idForm_Tratamento = idForm_Tratamento;
        this.tipo_Tratamento = tipo_Tratamento;
        this.nome_Tratamento = nome_Tratamento;
    }

    public Long getId_Tratamento() {
        return id_Tratamento;
    }

    public void setId_Tratamento(Long id_Tratamento) {
        this.id_Tratamento = id_Tratamento;
    }

    public Long getIdForm_Tratamento() {
        return idForm_Tratamento;
    }

    public void setIdForm_Tratamento(Long idForm_Tratamento) {
        this.idForm_Tratamento = idForm_Tratamento;
    }

    public Integer getTipo_Tratamento() {
        return tipo_Tratamento;
    }

    public void setTipo_Tratamento(Integer tipo_Tratamento) {
        this.tipo_Tratamento = tipo_Tratamento;
    }

    public String getNome_Tratamento() {
        return nome_Tratamento;
    }

    public void setNome_Tratamento(String nome_Tratamento) {
        this.nome_Tratamento = nome_Tratamento;
    }

    @Override
    public String toString() {
        return "Tratamento{" +
                "id_Tratamento=" + id_Tratamento +
                ", idForm_Tratamento=" + idForm_Tratamento +
                ", tipo_Tratamento=" + tipo_Tratamento +
                ", nome_Tratamento='" + nome_Tratamento + '\'' +
                '}';
    }
}
