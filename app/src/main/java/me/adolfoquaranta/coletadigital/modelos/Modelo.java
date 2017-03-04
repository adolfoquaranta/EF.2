package me.adolfoquaranta.coletadigital.modelos;

/**
 * Created by adolfo on 14/10/16.
 */

public class Modelo extends Formulario {
    private Long id_Modelo, idFormulario_Modelo;
    private Integer quantidadeTratamentos_Modelo, quantidadeRepeticoes_Modelo, quantidadeReplicacoes_Modelo, quantidadeVariaveis_Modelo, quantidadeBlocos_Modelo, quantidadeFatores_Modelo, quantidadeDivisoes_Modelo, modelo_Modelo;

    public Modelo() {
        super();
    }

    public Modelo(Long id_Modelo, Long idFormulario_Modelo, Integer modelo_Modelo, Integer quantidadeTratamentos_Modelo, Integer quantidadeRepeticoes_Modelo, Integer quantidadeReplicacoes_Modelo, Integer quantidadeVariaveis_Modelo, Integer quantidadeBlocos_Modelo, Integer quantidadeFatores_Modelo, Integer quantidadeDivisoes_Modelo) {
        super();
        this.id_Modelo = id_Modelo;
        this.idFormulario_Modelo = idFormulario_Modelo;
        this.modelo_Modelo = modelo_Modelo;
        this.quantidadeTratamentos_Modelo = quantidadeTratamentos_Modelo;
        this.quantidadeRepeticoes_Modelo = quantidadeRepeticoes_Modelo;
        this.quantidadeReplicacoes_Modelo = quantidadeReplicacoes_Modelo;
        this.quantidadeVariaveis_Modelo = quantidadeVariaveis_Modelo;
        this.quantidadeBlocos_Modelo = quantidadeBlocos_Modelo;
        this.quantidadeFatores_Modelo = quantidadeFatores_Modelo;
        this.quantidadeDivisoes_Modelo = quantidadeDivisoes_Modelo;
    }


    public Long getId_Modelo() {
        return id_Modelo;
    }

    public void setId_Modelo(Long id_Modelo) {
        this.id_Modelo = id_Modelo;
    }

    public Long getIdFormulario_Modelo() {
        return idFormulario_Modelo;
    }

    public void setIdFormulario_Modelo(Long idFormulario_Modelo) {
        this.idFormulario_Modelo = idFormulario_Modelo;
    }

    public Integer getModelo_Modelo() {
        return modelo_Modelo;
    }

    public void setModelo_Modelo(Integer modelo_Modelo) {
        this.modelo_Modelo = modelo_Modelo;
    }

    public Integer getQuantidadeTratamentos_Modelo() {
        return quantidadeTratamentos_Modelo;
    }

    public void setQuantidadeTratamentos_Modelo(Integer quantidadeTratamentos_Modelo) {
        this.quantidadeTratamentos_Modelo = quantidadeTratamentos_Modelo;
    }

    public Integer getQuantidadeRepeticoes_Modelo() {
        return quantidadeRepeticoes_Modelo;
    }

    public void setQuantidadeRepeticoes_Modelo(Integer quantidadeRepeticoes_Modelo) {
        this.quantidadeRepeticoes_Modelo = quantidadeRepeticoes_Modelo;
    }

    public Integer getQuantidadeReplicacoes_Modelo() {
        return quantidadeReplicacoes_Modelo;
    }

    public void setQuantidadeReplicacoes_Modelo(Integer quantidadeReplicacoes_Modelo) {
        this.quantidadeReplicacoes_Modelo = quantidadeReplicacoes_Modelo;
    }

    public Integer getQuantidadeVariaveis_Modelo() {
        return quantidadeVariaveis_Modelo;
    }

    public void setQuantidadeVariaveis_Modelo(Integer quantidadeVariaveis_Modelo) {
        this.quantidadeVariaveis_Modelo = quantidadeVariaveis_Modelo;
    }

    public Integer getQuantidadeBlocos_Modelo() {
        return quantidadeBlocos_Modelo;
    }

    public void setQuantidadeBlocos_Modelo(Integer quantidadeBlocos_Modelo) {
        this.quantidadeBlocos_Modelo = quantidadeBlocos_Modelo;
    }

    public Integer getQuantidadeFatores_Modelo() {
        return quantidadeFatores_Modelo;
    }

    public void setQuantidadeFatores_Modelo(Integer quantidadeFatores_Modelo) {
        this.quantidadeFatores_Modelo = quantidadeFatores_Modelo;
    }

    public Integer getQuantidadeDivisoes_Modelo() {
        return quantidadeDivisoes_Modelo;
    }

    public void setQuantidadeDivisoes_Modelo(Integer quantidadeDivisoes_Modelo) {
        this.quantidadeDivisoes_Modelo = quantidadeDivisoes_Modelo;
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "id_Modelo=" + id_Modelo +
                ", idFormulario_Modelo=" + idFormulario_Modelo +
                ", quantidadeTratamentos_Modelo=" + quantidadeTratamentos_Modelo +
                ", quantidadeRepeticoes_Modelo=" + quantidadeRepeticoes_Modelo +
                ", quantidadeReplicacoes_Modelo=" + quantidadeReplicacoes_Modelo +
                ", quantidadeVariaveis_Modelo=" + quantidadeVariaveis_Modelo +
                ", quantidadeBlocos_Modelo=" + quantidadeBlocos_Modelo +
                ", quantidadeFatores_Modelo=" + quantidadeFatores_Modelo +
                ", quantidadeDivisoes_Modelo=" + quantidadeDivisoes_Modelo +
                ", modelo_Modelo=" + modelo_Modelo +
                '}';
    }
}
