package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 10/10/16.
 */

public class Formulario {
    private Long id_Form;
    private String tipo_Form, nome_Form, descricao_Form, criador_Form, dataCriacao_Form, status_Form;

    public Formulario(){

    }

    public Formulario(Long id_Form, String tipo_Form, String nome_Form, String descricao_Form, String criador_Form, String dataCriacao_Form, String status_Form) {
        this.id_Form = id_Form;
        this.tipo_Form = tipo_Form;
        this.nome_Form = nome_Form;
        this.descricao_Form = descricao_Form;
        this.criador_Form = criador_Form;
        this.dataCriacao_Form = dataCriacao_Form;
        this.status_Form = status_Form;
    }

    public Long getId_Form() {
        return id_Form;
    }

    public void setId_Form(Long id_Form) {
        this.id_Form = id_Form;
    }

    public String getTipo_Form() {
        return tipo_Form;
    }

    public void setTipo_Form(String tipo_Form) {
        this.tipo_Form = tipo_Form;
    }

    public String getNome_Form() {
        return nome_Form;
    }

    public void setNome_Form(String nome_Form) {
        this.nome_Form = nome_Form;
    }

    public String getDescricao_Form() {
        return descricao_Form;
    }

    public void setDescricao_Form(String descricao_Form) {
        this.descricao_Form = descricao_Form;
    }

    public String getCriador_Form() {
        return criador_Form;
    }

    public void setCriador_Form(String criador_Form) {
        this.criador_Form = criador_Form;
    }

    public String getDataCriacao_Form() {
        return dataCriacao_Form;
    }

    public void setDataCriacao_Form(String dataCriacao_Form) {
        this.dataCriacao_Form = dataCriacao_Form;
    }

    public String getStatus_Form() {
        return status_Form;
    }

    public void setStatus_Form(String status_Form) {
        this.status_Form = status_Form;
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "id_Form=" + id_Form +
                ", tipo_Form='" + tipo_Form + '\'' +
                ", nome_Form='" + nome_Form + '\'' +
                ", descricao_Form='" + descricao_Form + '\'' +
                ", criador_Form='" + criador_Form + '\'' +
                ", dataCriacao_Form='" + dataCriacao_Form + '\'' +
                ", status_Form='" + status_Form + '\'' +
                '}';
    }
}