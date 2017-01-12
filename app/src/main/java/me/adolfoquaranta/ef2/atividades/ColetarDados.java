package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.List;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Tratamento;
import me.adolfoquaranta.ef2.modelos.Variavel;

public class ColetarDados extends AppCompatActivity {
    private DBAuxilar dbAuxilar;
    private List<Tratamento> tratamentos;
    private List<Variavel> variaveis;
    private DIC dic;
    private String[] valorDadosLista;
    private DadosListAdapter dadosListAdapter;
    private ListView list_viewColetar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        dbAuxilar = new DBAuxilar(getApplicationContext());

        Intent coletarDados = getIntent();
        Long id_Form = coletarDados.getLongExtra("id_Form", 0);
        Long id_Coleta = coletarDados.getLongExtra("id_Coleta", 0);

        dic = dbAuxilar.lerDICdoFormulario(id_Form);
        tratamentos = dbAuxilar.lerTodosTratamentos(id_Form);
        variaveis = dbAuxilar.lerTodasVariaveis(id_Form);



        valorDadosLista = new String[dic.getQuantidadeVariaveis_DIC()];

        dadosListAdapter = new DadosListAdapter();
        list_viewColetar = (ListView) findViewById(R.id.list_viewColetar);
        list_viewColetar.setAdapter(dadosListAdapter);
        //list_viewTratamentos.setFooterDividersEnabled(true);


    }

    private class DadosListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(dic.getQuantidadeVariaveis_DIC()!= null && dic.getQuantidadeVariaveis_DIC() != 0) {
                return dic.getQuantidadeVariaveis_DIC();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return valorDadosLista[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ColetarDados.DadosListAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new ColetarDados.DadosListAdapter.ViewHolder();
                LayoutInflater inflater = ColetarDados.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.coletar_linha_lista, null);
                holder.inputLayoutValor_Dado = (TextInputLayout) convertView.findViewById(R.id.input_layout_valorDado);
                holder.inputValor_Dado = (TextInputEditText) convertView.findViewById(R.id.input_valorDado);
                holder.btn_nuloDado = (ToggleButton) convertView.findViewById(R.id.btn_nuloDado);
                convertView.setTag(holder);
            }
            else{
                holder = (ColetarDados.DadosListAdapter.ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            if(valorDadosLista.length>0){
                holder.inputValor_Dado.setText(valorDadosLista[position]);
            }

            holder.inputValor_Dado.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    valorDadosLista[holder.ref] = s.toString();
                    validarValorDado(holder.ref);
                }

            });

            holder.btn_nuloDado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        valorDadosLista[position]=null;
                        holder.inputValor_Dado.setEnabled(false);
                    } else {
                        // The toggle is disabled
                        holder.inputValor_Dado.setEnabled(true);
                    }
                }
            });

            return convertView;
        }
        private class ViewHolder {
            TextInputLayout inputLayoutValor_Dado;
            TextInputEditText inputValor_Dado;
            ToggleButton btn_nuloDado;
            int ref;
        }

    }

    private boolean validarValorDado(Integer position) {

        return !valorDadosLista[position].isEmpty();
    }
}
