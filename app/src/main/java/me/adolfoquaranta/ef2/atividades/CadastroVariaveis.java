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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Variavel;

public class CadastroVariaveis extends AppCompatActivity {

    private ListView list_viewVariaveis;
    private DIC dic;
    private String[] nomeVariaveisLista;
    private Integer[] tipoVariaveisLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_variaveis);
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


        final Intent cadastroVariaveis = getIntent();
        final Long idFormulario_DIC = cadastroVariaveis.getLongExtra("idFormulario_DIC", 0);

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());

        dic = dbauxiliar.lerDICdoFormulario(idFormulario_DIC);

        nomeVariaveisLista = new String[dic.getQuantidadeVariaveis_DIC()];
        tipoVariaveisLista = new Integer[dic.getQuantidadeVariaveis_DIC()];

        CadastroVariaveis.VariaveisListAdapter variaveisListAdapter = new CadastroVariaveis.VariaveisListAdapter();
        list_viewVariaveis = (ListView) findViewById(R.id.list_viewVariaveis);
        list_viewVariaveis.setAdapter(variaveisListAdapter);
        //list_viewVariaveis.setFooterDividersEnabled(true);


        Button btn_salvar_Variaveis = (Button) findViewById(R.id.btn_salvar_Variaveis);
        btn_salvar_Variaveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < list_viewVariaveis.getCount(); i++) {
                    if(!validarNomeVariavel(i)) {
                        View v = list_viewVariaveis.getChildAt(i);
                        if(((TextInputEditText) v.findViewById(R.id.input_nomeVariavel)).getText().toString().isEmpty()){
                            ((TextInputLayout) v.findViewById(R.id.input_layout_nomeVariavel)).setError(getText(R.string.err_msg_nomeVariavel));
                            if(v.findViewById(R.id.input_nomeVariavel).isFocusable()){
                                v.findViewById(R.id.input_nomeVariavel).requestFocus();
                                return;
                            }
                        }
                        else {
                            ((TextInputLayout) v.findViewById(R.id.input_layout_nomeVariavel)).setError(getText(R.string.err_msg_tipoVariavel));
                            if(v.findViewById(R.id.input_tipoVariavel).isFocusable()) {
                                v.findViewById(R.id.input_tipoVariavel).requestFocus();
                                return;
                            }
                            return;
                        }
                    }
                    else{
                        View v = list_viewVariaveis.getChildAt(i);
                        ((TextInputLayout) v.findViewById(R.id.input_layout_nomeVariavel)).setErrorEnabled(false);
                    }
                }

                for (int i=0; i<nomeVariaveisLista.length; i++){
                    Variavel variavel = new Variavel();
                    variavel.setNome_Variavel(nomeVariaveisLista[i]);
                    variavel.setTipo_Variavel(tipoVariaveisLista[i]);
                    variavel.setIdForm_Variavel(dic.getIdFormulario_DIC());
                    dbauxiliar.insertVariavel(variavel);
                }

                Intent irParaInicio = new Intent(CadastroVariaveis.this, Inicio.class);
                startActivity(irParaInicio);
            }

        });



    }

    private class VariaveisListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(dic.getQuantidadeVariaveis_DIC()!= null && dic.getQuantidadeVariaveis_DIC() != 0) {
                return dic.getQuantidadeVariaveis_DIC();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return nomeVariaveisLista[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final CadastroVariaveis.VariaveisListAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new CadastroVariaveis.VariaveisListAdapter.ViewHolder();
                LayoutInflater inflater = CadastroVariaveis.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.variaveis_linha_lista, null);
                holder.inputLayoutNome_Variavel = (TextInputLayout) convertView.findViewById(R.id.input_layout_nomeVariavel);
                holder.inputNome_Variavel = (TextInputEditText) convertView.findViewById(R.id.input_nomeVariavel);
                holder.inputTipo_Variavel = (Spinner) convertView.findViewById(R.id.input_tipoVariavel);
                convertView.setTag(holder);
            }
            else{
                holder = (CadastroVariaveis.VariaveisListAdapter.ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            if(nomeVariaveisLista.length>0){
                holder.inputNome_Variavel.setText(nomeVariaveisLista[position]);
            }

            holder.inputNome_Variavel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    nomeVariaveisLista[holder.ref] = s.toString();
                    validarNomeVariavel(holder.ref);
                }

            });

            holder.inputTipo_Variavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    tipoVariaveisLista[position] = parent.getSelectedItemPosition();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            return convertView;
        }
        private class ViewHolder {
            TextInputLayout inputLayoutNome_Variavel;
            TextInputEditText inputNome_Variavel;
            Spinner inputTipo_Variavel;
            int ref;
        }

    }

    private boolean validarNomeVariavel(Integer position) {

        return !nomeVariaveisLista[position].isEmpty() && tipoVariaveisLista[position] != 0;
    }

}
