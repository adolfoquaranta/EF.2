package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.modelos.Modelo;

public class CadastroModeloFormulario extends AppCompatActivity {

    private TextInputEditText input_quantidadeTratamentos_Modelo, input_quantidadeRepeticoes_Modelo, input_quantidadeReplicacoes_Modelo, input_quantidadeVariaveis_Modelo;
    private TextInputLayout inputLayoutQuantidadeTratamentos_Modelo, inputLayoutQuantidadeRepeticoes_Modelo, inputLayoutQuantidadeReplicacoes_Modelo, inputLayoutQuantidadeVariaveis_Modelo;

    private Long id_Formulario, id_Modelo;
    private Integer modelo_Modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_modelo_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent cadastroModeloFormulario = getIntent();
        id_Formulario = cadastroModeloFormulario.getLongExtra("id_Formulario", 0);
        modelo_Modelo = cadastroModeloFormulario.getIntExtra("modelo_Modelo", -1);

        input_quantidadeTratamentos_Modelo = (TextInputEditText) findViewById(R.id.input_quantidadeTratatmentos_Modelo);
        input_quantidadeRepeticoes_Modelo = (TextInputEditText) findViewById(R.id.input_quantidadeRepeticoes_Modelo);
        input_quantidadeReplicacoes_Modelo = (TextInputEditText) findViewById(R.id.input_quantidadeReplicacoes_Modelo);
        input_quantidadeVariaveis_Modelo = (TextInputEditText) findViewById(R.id.input_quantidadeVariaveis_Modelo);

        inputLayoutQuantidadeTratamentos_Modelo = (TextInputLayout) findViewById(R.id.input_layout_quantidadeTratatmentos_Modelo);
        inputLayoutQuantidadeRepeticoes_Modelo = (TextInputLayout) findViewById(R.id.input_layout_quantidadeRepeticoes_Modelo);
        inputLayoutQuantidadeReplicacoes_Modelo = (TextInputLayout) findViewById(R.id.input_layout_quantidadeReplicacoes_Modelo);
        inputLayoutQuantidadeVariaveis_Modelo = (TextInputLayout) findViewById(R.id.input_layout_quantidadeVariaveis_Modelo);

        input_quantidadeTratamentos_Modelo.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeTratamentos_Modelo));
        input_quantidadeRepeticoes_Modelo.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeRepeticoes_Modelo));
        input_quantidadeReplicacoes_Modelo.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeReplicacoes_Modelo));
        input_quantidadeVariaveis_Modelo.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeVariaveis_Modelo));

        Button btnSalvar_Modelo = (Button) findViewById(R.id.btn_salvar_Modelo);


        assert btnSalvar_Modelo != null;
        btnSalvar_Modelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDados();
            }
        });

    }

    private void salvarDados(){
        if (!validarQuantidadeTratamentos_Modelo()) {
            return;
        }

        if (!validarQuantidadeRepeticoes_Modelo()) {
            return;
        }

        if (!validarQuantidadeReplicacoes_Modelo()) {
            return;
        }
        if (!validarQuantidadeVariaveis_Modelo()) {
            return;
        }


        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());


        Modelo modelo = new Modelo();
        modelo.setModelo_Modelo(modelo_Modelo);
        modelo.setIdFormulario_Modelo(id_Formulario);
        modelo.setQuantidadeTratamentos_Modelo(Integer.parseInt(input_quantidadeTratamentos_Modelo.getText().toString()));
        modelo.setQuantidadeRepeticoes_Modelo(Integer.parseInt(input_quantidadeRepeticoes_Modelo.getText().toString()));
        modelo.setQuantidadeReplicacoes_Modelo(Integer.parseInt(input_quantidadeReplicacoes_Modelo.getText().toString()));
        modelo.setQuantidadeVariaveis_Modelo(Integer.parseInt(input_quantidadeVariaveis_Modelo.getText().toString()));

        id_Modelo = dbAuxilar.inserirModelo(modelo);

        Intent cadastroTratamentos = new Intent(CadastroModeloFormulario.this, CadastroTratamentos.class);
        cadastroTratamentos.putExtra("id_Modelo", id_Modelo);
        cadastroTratamentos.putExtra("idFormulario_Modelo", id_Formulario);
        cadastroTratamentos.putExtra("modelo_Modelo", modelo_Modelo);
        startActivity(cadastroTratamentos);


    }

    private boolean validarQuantidadeTratamentos_Modelo() {
        if (input_quantidadeTratamentos_Modelo.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeTratamentos_Modelo.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeTratamentos_Modelo);
            return false;
        }
        else{
            inputLayoutQuantidadeTratamentos_Modelo.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeRepeticoes_Modelo() {
        if (input_quantidadeRepeticoes_Modelo.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeRepeticoes_Modelo.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeRepeticoes_Modelo);
            return false;
        }
        else{
            inputLayoutQuantidadeRepeticoes_Modelo.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeReplicacoes_Modelo() {
        if (input_quantidadeReplicacoes_Modelo.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeReplicacoes_Modelo.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeReplicacoes_Modelo);
            return false;
        }
        else{
            inputLayoutQuantidadeReplicacoes_Modelo.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeVariaveis_Modelo() {
        if (input_quantidadeVariaveis_Modelo.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeVariaveis_Modelo.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeVariaveis_Modelo);
            return false;
        }
        else{
            inputLayoutQuantidadeVariaveis_Modelo.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public class FormularioDICTextWatcher implements TextWatcher{

        private View view;

        private FormularioDICTextWatcher(View view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.input_quantidadeTratatmentos_Modelo:
                    validarQuantidadeTratamentos_Modelo();
                    break;
                case R.id.input_quantidadeRepeticoes_Modelo:
                    validarQuantidadeRepeticoes_Modelo();
                    break;
                case R.id.input_quantidadeReplicacoes_Modelo:
                    validarQuantidadeReplicacoes_Modelo();
                    break;
                case R.id.input_quantidadeVariaveis_Modelo:
                    validarQuantidadeVariaveis_Modelo();
                    break;

            }

        }
    }

}
