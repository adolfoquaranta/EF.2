package me.adolfoquaranta.ef2.modelos;

/**
 * Created by adolfo on 05/11/16.
 */

public class Coleta {
    private Long id_Coleta, idForm_Coleta, idModelo_Coleta;
    private String nome_Coleta, descricao_Coleta, dataCriacao_Coleta, dataUltimaEdicao_Coleta, status_Coleta, tipo_Coleta;


    public Coleta(Long id_Coleta, Long idForm_Coleta, String nome_Coleta, String descricao_Coleta, String dataCriacao_Coleta, String dataUltimaEdicao_Coleta, String status_Coleta, String tipo_Coleta, Long idModelo_Coleta) {
        this.id_Coleta = id_Coleta;
        this.idForm_Coleta = idForm_Coleta;
        this.nome_Coleta = nome_Coleta;
        this.descricao_Coleta = descricao_Coleta;
        this.dataCriacao_Coleta = dataCriacao_Coleta;
        this.dataUltimaEdicao_Coleta = dataUltimaEdicao_Coleta;
        this.status_Coleta = status_Coleta;
        this.tipo_Coleta = tipo_Coleta;
        this.idModelo_Coleta = idModelo_Coleta;
    }

    public Coleta() {
    }

    public Long getId_Coleta() {
        return id_Coleta;
    }

    public void setId_Coleta(Long id_Coleta) {
        this.id_Coleta = id_Coleta;
    }

    public Long getIdForm_Coleta() {
        return idForm_Coleta;
    }

    public void setIdForm_Coleta(Long idForm_Coleta) {
        this.idForm_Coleta = idForm_Coleta;
    }

    public String getNome_Coleta() {
        return nome_Coleta;
    }

    public void setNome_Coleta(String nome_Coleta) {
        this.nome_Coleta = nome_Coleta;
    }

    public String getDescricao_Coleta() {
        return descricao_Coleta;
    }

    public void setDescricao_Coleta(String descricao_Coleta) {
        this.descricao_Coleta = descricao_Coleta;
    }

    public String getDataCriacao_Coleta() {
        return dataCriacao_Coleta;
    }

    public void setDataCriacao_Coleta(String dataCriacao_Coleta) {
        this.dataCriacao_Coleta = dataCriacao_Coleta;
    }

    public String getDataUltimaEdicao_Coleta() {
        return dataUltimaEdicao_Coleta;
    }

    public void setDataUltimaEdicao_Coleta(String dataUltimaEdicao_Coleta) {
        this.dataUltimaEdicao_Coleta = dataUltimaEdicao_Coleta;
    }

    public String getStatus_Coleta() {
        return status_Coleta;
    }

    public void setStatus_Coleta(String status_Coleta) {
        this.status_Coleta = status_Coleta;
    }

    public String getTipo_Coleta() {
        return tipo_Coleta;
    }

    public void setTipo_Coleta(String tipo_Coleta) {
        this.tipo_Coleta = tipo_Coleta;
    }

    public Long getIdModelo_Coleta() {
        return idModelo_Coleta;
    }

    public void setIdModelo_Coleta(Long idModelo_Coleta) {
        this.idModelo_Coleta = idModelo_Coleta;
    }

    @Override
    public String toString() {
        return "Coleta{" +
                "id_Coleta=" + id_Coleta +
                ", idForm_Coleta=" + idForm_Coleta +
                ", idModelo_Coleta=" + idModelo_Coleta +
                ", nome_Coleta='" + nome_Coleta + '\'' +
                ", descricao_Coleta='" + descricao_Coleta + '\'' +
                ", dataCriacao_Coleta='" + dataCriacao_Coleta + '\'' +
                ", dataUltimaEdicao_Coleta='" + dataUltimaEdicao_Coleta + '\'' +
                ", status_Coleta='" + status_Coleta + '\'' +
                ", tipo_Coleta='" + tipo_Coleta + '\'' +
                '}';
    }
}
