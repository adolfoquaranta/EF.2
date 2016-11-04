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
import android.util.Log;
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
import me.adolfoquaranta.ef2.modelos.Tratamento;

public class CadastroTratamentos extends AppCompatActivity {
    private ListView list_viewTratamentos;
    private DIC dic;
    private String[] nomeTratamentosLista;
    private Integer[] tipoTratamentosLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tratamentos);
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


        final Intent cadastroTratamentos = getIntent();
        Long id_Formulario_DIC = cadastroTratamentos.getLongExtra("id_Formulario_DIC", 0);
        Long id_DIC = cadastroTratamentos.getLongExtra("id_DIC", 0);

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());

        dic = dbauxiliar.lerDIC(id_Formulario_DIC);

        nomeTratamentosLista = new String[dic.getQuantidadeTratamentos_DIC()];
        tipoTratamentosLista = new Integer[dic.getQuantidadeTratamentos_DIC()];

        TratamentosListAdapter tratamentosListAdapter = new TratamentosListAdapter();
        list_viewTratamentos = (ListView) findViewById(R.id.list_viewTratamentos);
        list_viewTratamentos.setAdapter(tratamentosListAdapter);
        //list_viewTratamentos.setFooterDividersEnabled(true);


        Button btn_salvar_Tratamentos = (Button) findViewById(R.id.btn_salvar_Tratamentos);
        btn_salvar_Tratamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < list_viewTratamentos.getCount(); i++) {
                    if(!validarNomeTratamento(i)) {
                        View v = list_viewTratamentos.getChildAt(i);
                        if(((TextInputEditText) v.findViewById(R.id.input_nomeTratamento)).getText().toString().isEmpty()){
                            ((TextInputLayout) v.findViewById(R.id.input_layout_nomeTratamento)).setError(getText(R.string.err_msg_nomeTratamento));
                            if(v.findViewById(R.id.input_nomeTratamento).isFocusable()){
                                v.findViewById(R.id.input_nomeTratamento).requestFocus();
                                return;
                            }
                        }
                        else {
                            ((TextInputLayout) v.findViewById(R.id.input_layout_nomeTratamento)).setError(getText(R.string.err_msg_tipoTratamento));
                            if(v.findViewById(R.id.input_tipoTratamento).isFocusable()) {
                                v.findViewById(R.id.input_tipoTratamento).requestFocus();
                                return;
                            }
                            return;
                        }
                    }
                    else{
                        View v = list_viewTratamentos.getChildAt(i);
                        ((TextInputLayout) v.findViewById(R.id.input_layout_nomeTratamento)).setErrorEnabled(false);
                    }
                }

                Log.i("Validação", "Validação OK!");

                for (int i=0; i<nomeTratamentosLista.length; i++){
                    Tratamento tratamento = new Tratamento();
                    tratamento.setNome_Tratamento(nomeTratamentosLista[i]);
                    tratamento.setTipo_Tratamento(tipoTratamentosLista[i]);
                    tratamento.setIdForm_Tratamento(dic.getIdFormulario_DIC());
                    dbauxiliar.insertTratamento(tratamento);
                }

                Intent cadastroVariaveis = new Intent(CadastroTratamentos.this, CadastroVariaveis.class);
                cadastroVariaveis.putExtra("idFormulario_DIC", dic.getIdFormulario_DIC());
                cadastroVariaveis.putExtra("id_DIC", dic.getId_DIC());
                startActivity(cadastroVariaveis);

            }

        });



    }

    private class TratamentosListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(dic.getQuantidadeTratamentos_DIC()!= null && dic.getQuantidadeTratamentos_DIC() != 0) {
                return dic.getQuantidadeTratamentos_DIC();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return nomeTratamentosLista[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = CadastroTratamentos.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.tratamentos_linha_lista, null);
                holder.inputLayoutNome_Tratamento = (TextInputLayout) convertView.findViewById(R.id.input_layout_nomeTratamento);
                holder.inputNome_Tratamento = (TextInputEditText) convertView.findViewById(R.id.input_nomeTratamento);
                holder.inputTipo_Tratamento = (Spinner) convertView.findViewById(R.id.input_tipoTratamento);
                holder.inputTipo_Tratamento.setFocusable(true);
                //holder.inputTipo_Tratamento.setFocusableInTouchMode(true);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            if(nomeTratamentosLista.length>0){
                holder.inputNome_Tratamento.setText(nomeTratamentosLista[position]);
            }

            holder.inputNome_Tratamento.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    nomeTratamentosLista[holder.ref] = s.toString();
                    validarNomeTratamento(holder.ref);
                }

            });

            holder.inputTipo_Tratamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    tipoTratamentosLista[position] = parent.getSelectedItemPosition();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            return convertView;
        }
        private class ViewHolder {
            TextInputLayout inputLayoutNome_Tratamento;
            TextInputEditText inputNome_Tratamento;
            Spinner inputTipo_Tratamento;
            int ref;
        }

    }

    private boolean validarNomeTratamento(Integer position) {

        return !nomeTratamentosLista[position].isEmpty() && tipoTratamentosLista[position] != 0;
    }
}


