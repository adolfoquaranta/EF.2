package me.adolfoquaranta.coletadigital.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.modelos.Formulario;

public class FormulariosAdapter extends RecyclerView.Adapter<FormulariosAdapter.MyViewHolder> {

    private List<Formulario> formularioList = new ArrayList<>();
    private String[] modelos = {"DIC", "DBC", "FAT", "SUB"};

    public FormulariosAdapter(List<Formulario> formularioList) {
        this.formularioList = formularioList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.formulario_linha_lista, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Formulario formulario = formularioList.get(position);
        holder.nome_Form.setText(formulario.getNome_Formulario());
        holder.descricao_Form.setText(formulario.getDescricao_Formulario());
        holder.dataCriacao_Form.setText(formulario.getDataCriacao_Formulario());
        holder.modelo_Form.setText(modelos[formulario.getModelo_Formulario()]);
    }

    @Override
    public int getItemCount() {
        return formularioList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome_Form, descricao_Form, dataCriacao_Form, modelo_Form;

        MyViewHolder(View view) {
            super(view);
            nome_Form = (TextView) view.findViewById(R.id.nome_Form);
            descricao_Form = (TextView) view.findViewById(R.id.descricao_Form);
            dataCriacao_Form = (TextView) view.findViewById(R.id.dataCricao_Form);
            modelo_Form = (TextView) view.findViewById(R.id.modelo_Form);
        }
    }
    
}

