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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Date;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.Coleta;
import me.adolfoquaranta.ef2.modelos.Formulario;

public class CadastroColeta extends AppCompatActivity {
    private TextInputEditText inputNome_Coleta, inputDescricao_Coleta;
    private TextInputLayout inputLayoutNome_Coleta, inputLayoutDescricao_Coleta;

    private Long id_Form;
    private String modelo_Form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_coleta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent cadastroColeta = getIntent();
        id_Form = cadastroColeta.getLongExtra("id_Form", 0);
        modelo_Form = cadastroColeta.getStringExtra("modelo_Form");

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

        Formulario formulario = dbAuxilar.lerFormulario(id_Form);

        Coleta coleta = new Coleta();
        coleta.setNome_Coleta(inputNome_Coleta.getText().toString());
        coleta.setDescricao_Coleta(inputDescricao_Coleta.getText().toString());
        coleta.setDataCriacao_Coleta(date.toString());
        coleta.setDataUltimaEdicao_Coleta(date.toString());
        coleta.setStatus_Coleta("");
        coleta.setTipo_Coleta(modelo_Form);
        coleta.setModeloForm_Coleta(formulario.getModelo_Form());
        coleta.setIdForm_Coleta(id_Form);


        Long id_Coleta = dbAuxilar.insertColeta(coleta);
        Log.d("id_Coleta", id_Coleta.toString());

        Intent coletaDados = new Intent(CadastroColeta.this, ColetaDados.class);
        coletaDados.putExtra("id_Form", id_Form);
        coletaDados.putExtra("id_Coleta", id_Coleta);
        startActivity(coletaDados);
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
                case R.id.input_nome_Form:
                    validarNome_Coleta();
                    break;
                case R.id.input_descricao_Form:
                    validarDescricao_Coleta();
                    break;
            }
        }
    }

}
