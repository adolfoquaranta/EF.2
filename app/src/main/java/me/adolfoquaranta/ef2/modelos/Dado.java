package me.adolfoquaranta.ef2.modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adolfo on 09/11/16.
 */

public class Dado implements Parcelable {
    public static final Creator<Dado> CREATOR = new Creator<Dado>() {
        @Override
        public Dado createFromParcel(Parcel in) {
            Dado dado = new Dado();
            dado.id_Dado = in.readLong();
            dado.idColeta_Dado = in.readLong();
            dado.idTratamento_Dado = in.readLong();
            dado.idVariavel_Dado = in.readLong();
            dado.valor_Dado = in.readString();
            dado.repeticao_Dado = in.readInt();
            dado.replicacao_Dado = in.readInt();
            dado.bloco_Dado = in.readInt();
            dado.fator_Dado = in.readInt();
            dado.divisao_Dado = in.readInt();
            dado.nivelFator_Dado = in.readInt();
            dado.nivelDivisao_Dado = in.readInt();
            return dado;
        }

        @Override
        public Dado[] newArray(int size) {
            return new Dado[size];
        }
    };
    private Long id_Dado, idTratamento_Dado, idVariavel_Dado, idColeta_Dado;
    private String valor_Dado;
    private Integer repeticao_Dado, replicacao_Dado, bloco_Dado, fator_Dado, divisao_Dado, nivelFator_Dado, nivelDivisao_Dado;

    public Dado() {
    }

    public Dado(Long id_Dado, Long idTratamento_Dado, Long idVariavel_Dado, Long idColeta_Dado, String valor_Dado, Integer repeticao_Dado, Integer replicacao_Dado, Integer bloco_Dado, Integer fator_Dado, Integer divisao_Dado, Integer nivelFator_Dado, Integer nivelDivisao_Dado) {
        this.id_Dado = id_Dado;
        this.idTratamento_Dado = idTratamento_Dado;
        this.idVariavel_Dado = idVariavel_Dado;
        this.idColeta_Dado = idColeta_Dado;
        this.valor_Dado = valor_Dado;
        this.repeticao_Dado = repeticao_Dado;
        this.replicacao_Dado = replicacao_Dado;
        this.bloco_Dado = bloco_Dado;
        this.fator_Dado = fator_Dado;
        this.divisao_Dado = divisao_Dado;
        this.nivelFator_Dado = nivelFator_Dado;
        this.nivelDivisao_Dado = nivelDivisao_Dado;
    }

    protected Dado(Parcel in) {
        String[] data = new String[12];
        in.readStringArray(data);
        this.id_Dado = Long.valueOf(data[0]);
        this.idTratamento_Dado = Long.valueOf(data[1]);
        this.idVariavel_Dado = Long.valueOf(data[2]);
        this.idColeta_Dado = Long.valueOf(data[3]);
        this.valor_Dado = data[4];
        this.repeticao_Dado = Integer.valueOf(data[5]);
        this.replicacao_Dado = Integer.valueOf(data[6]);
        this.bloco_Dado = Integer.valueOf(data[7]);
        this.fator_Dado = Integer.valueOf(data[8]);
        this.divisao_Dado = Integer.valueOf(data[9]);
        this.nivelFator_Dado = Integer.valueOf(data[10]);
        this.nivelDivisao_Dado = Integer.valueOf(data[11]);
    }

    public Long getId_Dado() {
        return id_Dado;
    }

    public void setId_Dado(Long id_Dado) {
        this.id_Dado = id_Dado;
    }

    public Long getIdTratamento_Dado() {
        return idTratamento_Dado;
    }

    public void setIdTratamento_Dado(Long idTratamento_Dado) {
        this.idTratamento_Dado = idTratamento_Dado;
    }

    public Long getIdVariavel_Dado() {
        return idVariavel_Dado;
    }

    public void setIdVariavel_Dado(Long idVariavel_Dado) {
        this.idVariavel_Dado = idVariavel_Dado;
    }

    public Long getIdColeta_Dado() {
        return idColeta_Dado;
    }

    public void setIdColeta_Dado(Long idColeta_Dado) {
        this.idColeta_Dado = idColeta_Dado;
    }

    public String getValor_Dado() {
        return valor_Dado;
    }

    public void setValor_Dado(String valor_Dado) {
        this.valor_Dado = valor_Dado;
    }

    public Integer getRepeticao_Dado() {
        return repeticao_Dado;
    }

    public void setRepeticao_Dado(Integer repeticao_Dado) {
        this.repeticao_Dado = repeticao_Dado;
    }

    public Integer getReplicacao_Dado() {
        return replicacao_Dado;
    }

    public void setReplicacao_Dado(Integer replicacao_Dado) {
        this.replicacao_Dado = replicacao_Dado;
    }

    public Integer getBloco_Dado() {
        return bloco_Dado;
    }

    public void setBloco_Dado(Integer bloco_Dado) {
        this.bloco_Dado = bloco_Dado;
    }

    public Integer getFator_Dado() {
        return fator_Dado;
    }

    public void setFator_Dado(Integer fator_Dado) {
        this.fator_Dado = fator_Dado;
    }

    public Integer getDivisao_Dado() {
        return divisao_Dado;
    }

    public void setDivisao_Dado(Integer divisao_Dado) {
        this.divisao_Dado = divisao_Dado;
    }

    public Integer getNivelFator_Dado() {
        return nivelFator_Dado;
    }

    public void setNivelFator_Dado(Integer nivelFator_Dado) {
        this.nivelFator_Dado = nivelFator_Dado;
    }

    public Integer getNivelDivisao_Dado() {
        return nivelDivisao_Dado;
    }

    public void setNivelDivisao_Dado(Integer nivelDivisao_Dado) {
        this.nivelDivisao_Dado = nivelDivisao_Dado;
    }

    @Override
    public String toString() {
        return "Dado{" +
                "id_Dado=" + id_Dado +
                ", idTratamento_Dado=" + idTratamento_Dado +
                ", idVariavel_Dado=" + idVariavel_Dado +
                ", idColeta_Dado=" + idColeta_Dado +
                ", valor_Dado='" + valor_Dado + '\'' +
                ", repeticao_Dado=" + repeticao_Dado +
                ", replicacao_Dado=" + replicacao_Dado +
                ", bloco_Dado=" + bloco_Dado +
                ", fator_Dado=" + fator_Dado +
                ", divisao_Dado=" + divisao_Dado +
                ", nivelFator_Dado=" + nivelFator_Dado +
                ", nivelDivisao_Dado=" + nivelDivisao_Dado +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id_Dado != null) dest.writeLong(id_Dado);
        if (idColeta_Dado != null) dest.writeLong(idColeta_Dado);
        if (idTratamento_Dado != null) dest.writeLong(idTratamento_Dado);
        if (idVariavel_Dado != null) dest.writeLong(idVariavel_Dado);
        if (valor_Dado != null) dest.writeString(valor_Dado);
        if (repeticao_Dado != null) dest.writeInt(repeticao_Dado);
        if (replicacao_Dado != null) dest.writeInt(replicacao_Dado);
        if (bloco_Dado != null) dest.writeInt(bloco_Dado);
        if (fator_Dado != null) dest.writeInt(fator_Dado);
        if (divisao_Dado != null) dest.writeInt(divisao_Dado);
        if (nivelFator_Dado != null) dest.writeInt(nivelFator_Dado);
        if (nivelDivisao_Dado != null) dest.writeInt(nivelDivisao_Dado);
    }

}
