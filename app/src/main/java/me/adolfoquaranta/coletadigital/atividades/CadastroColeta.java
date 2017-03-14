package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Date;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.modelos.Coleta;

public class CadastroColeta extends AppCompatActivity {
    private TextInputEditText inputNome_Coleta, inputDescricao_Coleta;
    private TextInputLayout inputLayoutNome_Coleta, inputLayoutDescricao_Coleta;

    private Long id_Formulario;
    private String tipo_Formulario;
    private Integer modelo_Modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_coleta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent cadastroColeta = getIntent();
        id_Formulario = cadastroColeta.getLongExtra("id_Formulario", 0);

        inputNome_Coleta = (TextInputEditText) findViewById(R.id.input_nomeColeta);
        inputDescricao_Coleta = (TextInputEditText) findViewById(R.id.input_descricaoColeta);


        inputLayoutNome_Coleta = (TextInputLayout) findViewById(R.id.input_layout_nomeColeta);
        inputLayoutDescricao_Coleta = (TextInputLayout) findViewById(R.id.input_layout_descricaoColeta);


        inputNome_Coleta.addTextChangedListener(new CadastroColeta.ColetaTextWatcher(inputNome_Coleta));
        inputDescricao_Coleta.addTextChangedListener(new CadastroColeta.ColetaTextWatcher(inputDescricao_Coleta));


        final Button btnSalvar_Coleta = (Button) findViewById(R.id.btn_salvar_Coleta);

        btnSalvar_Coleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDados();
            }
        });


    }

    private void salvarDados() {

        if(!validarNome_Coleta()){
            return;
        }

        if (!validarDescricao_Coleta()) {
            return;
        }

        Date date = new Date();
        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());

        Coleta coleta = new Coleta();
        coleta.setNome_Coleta(inputNome_Coleta.getText().toString());
        coleta.setDescricao_Coleta(inputDescricao_Coleta.getText().toString());
        coleta.setDataCriacao_Coleta(date.toString());
        coleta.setDataUltimaEdicao_Coleta(date.toString());
        coleta.setTipo_Coleta(tipo_Formulario);
        coleta.setIdFormulario_Coleta(id_Formulario);


        Long id_Coleta = dbAuxilar.insertColeta(coleta);
        Log.d("id_Coleta", id_Coleta.toString());

        Intent coletarDados = new Intent(CadastroColeta.this, ColetarDados.class);
        coletarDados.putExtra("id_Formulario", id_Formulario);
        coletarDados.putExtra("id_Coleta", id_Coleta);
        coletarDados.putExtra("tratamentoAtual", 0);
        coletarDados.putExtra("replicacaoAtual", 0);
        coletarDados.putExtra("repeticaoAtual", 0);
        coletarDados.putExtra("modelo_Modelo", modelo_Modelo);
        startActivity(coletarDados);
    }


    private boolean validarNome_Coleta() {
        if (inputNome_Coleta.getText().toString().trim().isEmpty()) {
            inputLayoutNome_Coleta.setError(getString(R.string.err_msg_nomeColeta));
            requestFocus(inputNome_Coleta);
            return false;
        } else {
            inputLayoutNome_Coleta.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarDescricao_Coleta() {

        if (inputDescricao_Coleta.getText().toString().trim().isEmpty()) {
            inputLayoutDescricao_Coleta.setError(getString(R.string.err_msg_descricaoColeta));
            requestFocus(inputDescricao_Coleta);
            return false;
        } else {
            inputLayoutDescricao_Coleta.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class ColetaTextWatcher implements TextWatcher {

        private View view;

        private ColetaTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_nome_Formulario:
                    validarNome_Coleta();
                    break;
                case R.id.input_descricao_Formulario:
                    validarDescricao_Coleta();
                    break;
            }
        }
    }

}
