package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 10/10/16.
 */

public class Formulario {
    private Long id_Formulario;
    private String tipo_Formulario, nome_Formulario, descricao_Formulario, criador_Formulario, dataCriacao_Formulario;
    private Integer quantidadeTratamentos_Formulario, quantidadeRepeticoes_Formulario, quantidadeReplicacoes_Formulario, quantidadeVariaveis_Formulario, quantidadeBlocos_Formulario, quantidadeFatores_Formulario, quantidadeParcelas_Formulario, modelo_Formulario, status_Formulario;

    public Formulario() {
    }

    public Long getId_Formulario() {
        return id_Formulario;
    }

    public void setId_Formulario(Long id_Formulario) {
        this.id_Formulario = id_Formulario;
    }

    public String getTipo_Formulario() {
        return tipo_Formulario;
    }

    public void setTipo_Formulario(String tipo_Formulario) {
        this.tipo_Formulario = tipo_Formulario;
    }

    public String getNome_Formulario() {
        return nome_Formulario;
    }

    public void setNome_Formulario(String nome_Formulario) {
        this.nome_Formulario = nome_Formulario;
    }

    public String getDescricao_Formulario() {
        return descricao_Formulario;
    }

    public void setDescricao_Formulario(String descricao_Formulario) {
        this.descricao_Formulario = descricao_Formulario;
    }

    public String getCriador_Formulario() {
        return criador_Formulario;
    }

    public void setCriador_Formulario(String criador_Formulario) {
        this.criador_Formulario = criador_Formulario;
    }

    public String getDataCriacao_Formulario() {
        return dataCriacao_Formulario;
    }

    public void setDataCriacao_Formulario(String dataCriacao_Formulario) {
        this.dataCriacao_Formulario = dataCriacao_Formulario;
    }

    public Integer getStatus_Formulario() {
        return status_Formulario;
    }

    public void setStatus_Formulario(Integer status_Formulario) {
        this.status_Formulario = status_Formulario;
    }

    public Integer getQuantidadeTratamentos_Formulario() {
        return quantidadeTratamentos_Formulario;
    }

    public void setQuantidadeTratamentos_Formulario(Integer quantidadeTratamentos_Formulario) {
        this.quantidadeTratamentos_Formulario = quantidadeTratamentos_Formulario;
    }

    public Integer getQuantidadeRepeticoes_Formulario() {
        return quantidadeRepeticoes_Formulario;
    }

    public void setQuantidadeRepeticoes_Formulario(Integer quantidadeRepeticoes_Formulario) {
        this.quantidadeRepeticoes_Formulario = quantidadeRepeticoes_Formulario;
    }

    public Integer getQuantidadeReplicacoes_Formulario() {
        return quantidadeReplicacoes_Formulario;
    }

    public void setQuantidadeReplicacoes_Formulario(Integer quantidadeReplicacoes_Formulario) {
        this.quantidadeReplicacoes_Formulario = quantidadeReplicacoes_Formulario;
    }

    public Integer getQuantidadeVariaveis_Formulario() {
        return quantidadeVariaveis_Formulario;
    }

    public void setQuantidadeVariaveis_Formulario(Integer quantidadeVariaveis_Formulario) {
        this.quantidadeVariaveis_Formulario = quantidadeVariaveis_Formulario;
    }

    public Integer getQuantidadeBlocos_Formulario() {
        return quantidadeBlocos_Formulario;
    }

    public void setQuantidadeBlocos_Formulario(Integer quantidadeBlocos_Formulario) {
        this.quantidadeBlocos_Formulario = quantidadeBlocos_Formulario;
    }

    public Integer getQuantidadeFatores_Formulario() {
        return quantidadeFatores_Formulario;
    }

    public void setQuantidadeFatores_Formulario(Integer quantidadeFatores_Formulario) {
        this.quantidadeFatores_Formulario = quantidadeFatores_Formulario;
    }

    public Integer getQuantidadeParcelas_Formulario() {
        return quantidadeParcelas_Formulario;
    }

    public void setQuantidadeParcelas_Formulario(Integer quantidadeParcelas_Formulario) {
        this.quantidadeParcelas_Formulario = quantidadeParcelas_Formulario;
    }

    public Integer getModelo_Formulario() {
        return modelo_Formulario;
    }

    public void setModelo_Formulario(Integer modelo_Formulario) {
        this.modelo_Formulario = modelo_Formulario;
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "id_Formulario=" + id_Formulario +
                ", tipo_Formulario='" + tipo_Formulario + '\'' +
                ", nome_Formulario='" + nome_Formulario + '\'' +
                ", descricao_Formulario='" + descricao_Formulario + '\'' +
                ", criador_Formulario='" + criador_Formulario + '\'' +
                ", dataCriacao_Formulario='" + dataCriacao_Formulario + '\'' +
                ", status_Formulario=" + status_Formulario +
                ", quantidadeTratamentos_Formulario=" + quantidadeTratamentos_Formulario +
                ", quantidadeRepeticoes_Formulario=" + quantidadeRepeticoes_Formulario +
                ", quantidadeReplicacoes_Formulario=" + quantidadeReplicacoes_Formulario +
                ", quantidadeVariaveis_Formulario=" + quantidadeVariaveis_Formulario +
                ", quantidadeBlocos_Formulario=" + quantidadeBlocos_Formulario +
                ", quantidadeFatores_Formulario=" + quantidadeFatores_Formulario +
                ", quantidadeParcelas_Formulario=" + quantidadeParcelas_Formulario +
                ", modelo_Formulario=" + modelo_Formulario +
                '}';
    }
}