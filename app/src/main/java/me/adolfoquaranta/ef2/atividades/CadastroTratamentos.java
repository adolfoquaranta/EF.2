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
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.DIC;

public class CadastroTratamentos extends AppCompatActivity {
    private DIC dic;

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


        Intent cadastroTratamentos = getIntent();
        Long id_Formulario_DIC = cadastroTratamentos.getLongExtra("id_Formulario_DIC", 0);
        Long id_DIC = cadastroTratamentos.getLongExtra("id_DIC", 0);

        DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());

        dic = dbauxiliar.lerDIC(id_DIC, id_Formulario_DIC);


        final TratamentosListAdapter tratamentosListAdapter = new TratamentosListAdapter();
        final ListView list_viewTratamentos = (ListView) findViewById(R.id.list_viewTratamentos);
        list_viewTratamentos.setAdapter(tratamentosListAdapter);
        list_viewTratamentos.setFooterDividersEnabled(true);


        Button btn_salvar_Tratamentos = (Button) findViewById(R.id.btn_salvar_Tratamentos);



        btn_salvar_Tratamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v;
                ArrayList<String> nomeTratamentosLista = new ArrayList<String>();
                ArrayList<Integer> tipoTratamentosLista = new ArrayList<Integer>();
                TextInputEditText nomeTratamento;
                Spinner tipo_Tratamento;
                TextInputLayout textInputLayoutNomeTratamento;
                for (int i = 0; i < list_viewTratamentos.getCount(); i++) {
                    v = list_viewTratamentos.getChildAt(i);
                    nomeTratamento = (TextInputEditText) v.findViewById(R.id.input_nomeTratamento);
                    tipo_Tratamento = (Spinner) v.findViewById(R.id.input_tipoTratamento);
                    textInputLayoutNomeTratamento = (TextInputLayout) v.findViewById(R.id.input_layout_nomeTratamento);
                    if(validarNomeTratamento(nomeTratamento,textInputLayoutNomeTratamento)) {
                        nomeTratamentosLista.add(nomeTratamento.getText().toString());
                        tipoTratamentosLista.add(tipo_Tratamento.getSelectedItemPosition());
                    }
                    else Log.i("Validação", "nada feito");
                }
                Log.d("TRATAMENTOS_nome",nomeTratamentosLista.toString());
                Log.d("TRATAMENTOS_tipo",tipoTratamentosLista.toString());
            }
        });





    }

    private class TratamentosListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dic.getQuantidadeTratamentos_DIC();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = CadastroTratamentos.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.tratamentos_linha_lista, null);
                holder.inputLayoutNome_Tratamento = (TextInputLayout) convertView.findViewById(R.id.input_layout_nomeTratamento);
                holder.inputNome_Tratamento = (TextInputEditText) convertView.findViewById(R.id.input_nomeTratamento);
                holder.inputTipo_Tratamento = (Spinner) convertView.findViewById(R.id.input_tipoTratamento);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.inputNome_Tratamento.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i("afterTextChanged", "OK");
                    validarNomeTratamento(holder.inputNome_Tratamento, holder.inputLayoutNome_Tratamento);
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

    private boolean validarNomeTratamento(EditText nome_Tratamento, TextInputLayout textInputLayoutNome_Tratamento){
        Log.i("DentroDaFuncao","OK");
        if(nome_Tratamento.getText().toString().trim().isEmpty()){
            textInputLayoutNome_Tratamento.setError(getString(R.string.err_msg_nomeTratamento));
            requestFocus(nome_Tratamento);
            return false;
        }
        else {
            textInputLayoutNome_Tratamento.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}


