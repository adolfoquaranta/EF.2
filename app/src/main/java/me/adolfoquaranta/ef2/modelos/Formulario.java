package me.adolfoquaranta.ef2.modelos;

import java.util.Date;

/**
 * Created by adolfo on 10/10/16.
 */

public class Formulario {
    public Integer id_Form;
    public String modelo_Form, nome_Form, descricao_Form, criador_Form;
    public Date dataCriacao_Form;

    public Formulario(){

    }

    public Formulario(Integer id_Form, String modelo_Form, String nome_Form, String descricao_Form, String criador_Form, Date dataCriacao_Form){
        this.id_Form = id_Form;
        this.modelo_Form = modelo_Form;
        this.nome_Form = nome_Form;
        this.descricao_Form = descricao_Form;
        this.criador_Form = criador_Form;
        this.dataCriacao_Form = dataCriacao_Form;
    }

    public Integer getId_Form() {
        return id_Form;
    }

    public void setId_Form(Integer id_Form) {
        this.id_Form = id_Form;
    }

    public String getModelo_Form() {
        return modelo_Form;
    }

    public void setModelo_Form(String modelo_Form) {
        this.modelo_Form = modelo_Form;
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

    public Date getDataCriacao_Form() {
        return dataCriacao_Form;
    }

    public void setDataCriacao_Form(Date dataCriacao_Form) {
        this.dataCriacao_Form = dataCriacao_Form;
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "id_Form=" + id_Form +
                ", modelo_Form='" + modelo_Form + '\'' +
                ", nome_Form='" + nome_Form + '\'' +
                ", descricao_Form='" + descricao_Form + '\'' +
                ", criador_Form='" + criador_Form + '\'' +
                ", dataCriacao_Form=" + dataCriacao_Form +
                '}';
    }
}