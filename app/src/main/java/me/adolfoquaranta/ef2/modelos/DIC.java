package me.adolfoquaranta.ef2.modelos;

/**
 * Created by adolfo on 14/10/16.
 */

public class DIC extends Formulario{
    private Long id_DIC, idFormulario_DIC;
    private Integer quantidadeTratamentos_DIC, quantidadeRepeticoes_DIC, quantidadeReplicacoes_DIC, quantidadeVariaveis_DIC;

    public DIC() {
        super();
    }

    public DIC(Long id_DIC, Long idFormulario_DIC, Integer quantidadeTratamentos_DIC, Integer quantidadeRepeticoes_DIC, Integer quantidadeReplicacoes_DIC, Integer quantidadeVariaveis_DIC) {
        super();
        this.id_DIC = id_DIC;
        this.idFormulario_DIC = idFormulario_DIC;
        this.quantidadeTratamentos_DIC = quantidadeTratamentos_DIC;
        this.quantidadeRepeticoes_DIC = quantidadeRepeticoes_DIC;
        this.quantidadeReplicacoes_DIC = quantidadeReplicacoes_DIC;
        this.quantidadeVariaveis_DIC = quantidadeVariaveis_DIC;
    }

    public Long getId_DIC() {
        return id_DIC;
    }

    public void setId_DIC(Long id_DIC) {
        this.id_DIC = id_DIC;
    }

    public Long getIdFormulario_DIC() {
        return idFormulario_DIC;
    }

    public void setIdFormulario_DIC(Long idFormulario_DIC) {
        this.idFormulario_DIC = idFormulario_DIC;
    }

    public Integer getQuantidadeTratamentos_DIC() {
        return quantidadeTratamentos_DIC;
    }

    public void setQuantidadeTratamentos_DIC(Integer quantidadeTratamentos_DIC) {
        this.quantidadeTratamentos_DIC = quantidadeTratamentos_DIC;
    }

    public Integer getQuantidadeRepeticoes_DIC() {
        return quantidadeRepeticoes_DIC;
    }

    public void setQuantidadeRepeticoes_DIC(Integer quantidadeRepeticoes_DIC) {
        this.quantidadeRepeticoes_DIC = quantidadeRepeticoes_DIC;
    }

    public Integer getQuantidadeReplicacoes_DIC() {
        return quantidadeReplicacoes_DIC;
    }

    public void setQuantidadeReplicacoes_DIC(Integer quantidadeReplicacoes_DIC) {
        this.quantidadeReplicacoes_DIC = quantidadeReplicacoes_DIC;
    }

    public Integer getQuantidadeVariaveis_DIC() {
        return quantidadeVariaveis_DIC;
    }

    public void setQuantidadeVariaveis_DIC(Integer quantidadeVariaveis_DIC) {
        this.quantidadeVariaveis_DIC = quantidadeVariaveis_DIC;
    }

    @Override
    public String toString() {
        return "DIC{" +
                "id_DIC=" + id_DIC +
                ", idFormulario_DIC=" + idFormulario_DIC +
                ", quantidadeTratamentos_DIC=" + quantidadeTratamentos_DIC +
                ", quantidadeRepeticoes_DIC=" + quantidadeRepeticoes_DIC +
                ", quantidadeReplicacoes_DIC=" + quantidadeReplicacoes_DIC +
                ", quantidadeVariaveis_DIC=" + quantidadeVariaveis_DIC +
                '}';
    }

}
