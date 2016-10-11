package me.adolfoquaranta.ef2.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.modelos.Formulario;

public class FormulariosAdapter extends RecyclerView.Adapter<FormulariosAdapter.MyViewHolder> {

    private List<Formulario> formularioList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome_Form, descricao_Form, dataCriacao_Form;

        public MyViewHolder(View view) {
            super(view);
            nome_Form = (TextView) view.findViewById(R.id.nome_Form);
            descricao_Form = (TextView) view.findViewById(R.id.descricao_Form);
            dataCriacao_Form = (TextView) view.findViewById(R.id.dataCricao_Form);
        }
    }


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
        Formulario movie = formularioList.get(position);
        holder.nome_Form.setText(movie.getNome_Form());
        holder.descricao_Form.setText(movie.getDescricao_Form());
        holder.dataCriacao_Form.setText(movie.getDataCriacao_Form().toString());
    }

    @Override
    public int getItemCount() {
        return formularioList.size();
    }
    
}

