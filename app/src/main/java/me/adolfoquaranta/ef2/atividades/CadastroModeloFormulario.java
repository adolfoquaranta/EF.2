package me.adolfoquaranta.ef2.atividades;

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

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.Modelo;

public class CadastroModeloFormulario extends AppCompatActivity {

    private TextInputEditText input_quantidadeTratamentos_DIC, input_quantidadeRepeticoes_DIC, input_quantidadeReplicacoes_DIC, input_quantidadeVariaveis_DIC;
    private TextInputLayout inputLayoutQuantidadeTratamentos_DIC, inputLayoutQuantidadeRepeticoes_DIC, inputLayoutQuantidadeReplicacoes_DIC, inputLayoutQuantidadeVariaveis_DIC;

    private Long id_Formulario, id_Modelo;
    private String modelo_Modelo;

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
        modelo_Modelo = cadastroModeloFormulario.getStringExtra("modelo_Modelo");

        input_quantidadeTratamentos_DIC = (TextInputEditText) findViewById(R.id.input_quantidadeTratatmentos_DIC);
        input_quantidadeRepeticoes_DIC = (TextInputEditText) findViewById(R.id.input_quantidadeRepeticoes_DIC);
        input_quantidadeReplicacoes_DIC = (TextInputEditText) findViewById(R.id.input_quantidadeReplicacoes_DIC);
        input_quantidadeVariaveis_DIC = (TextInputEditText) findViewById(R.id.input_quantidadeVariaveis_DIC);

        inputLayoutQuantidadeTratamentos_DIC = (TextInputLayout) findViewById(R.id.input_layout_quantidadeTratatmentos_DIC);
        inputLayoutQuantidadeRepeticoes_DIC = (TextInputLayout) findViewById(R.id.input_layout_quantidadeRepeticoes_DIC);
        inputLayoutQuantidadeReplicacoes_DIC = (TextInputLayout) findViewById(R.id.input_layout_quantidadeReplicacoes_DIC);
        inputLayoutQuantidadeVariaveis_DIC = (TextInputLayout) findViewById(R.id.input_layout_quantidadeVariaveis_DIC);

        input_quantidadeTratamentos_DIC.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeTratamentos_DIC));
        input_quantidadeRepeticoes_DIC.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeRepeticoes_DIC));
        input_quantidadeReplicacoes_DIC.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeReplicacoes_DIC));
        input_quantidadeVariaveis_DIC.addTextChangedListener(new FormularioDICTextWatcher(input_quantidadeVariaveis_DIC));

        Button btnSalvar_DIC = (Button) findViewById(R.id.btn_salvar_DIC);

        assert btnSalvar_DIC != null;
        btnSalvar_DIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDados();
            }
        });

    }

    private void salvarDados(){
        if (!validarQuantidadeTratamentos_DIC()) {
            return;
        }

        if (!validarQuantidadeRepeticoes_DIC()) {
            return;
        }

        if (!validarQuantidadeReplicacoes_DIC()) {
            return;
        }
        if (!validarQuantidadeVariaveis_DIC()){
            return;
        }


        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());


        Modelo modelo = new Modelo();
        modelo.setModelo_Modelo(modelo_Modelo);
        modelo.setIdFormulario_Modelo(id_Formulario);
        modelo.setQuantidadeTratamentos_Modelo(Integer.parseInt(input_quantidadeTratamentos_DIC.getText().toString()));
        modelo.setQuantidadeRepeticoes_Modelo(Integer.parseInt(input_quantidadeRepeticoes_DIC.getText().toString()));
        modelo.setQuantidadeReplicacoes_Modelo(Integer.parseInt(input_quantidadeReplicacoes_DIC.getText().toString()));
        modelo.setQuantidadeVariaveis_Modelo(Integer.parseInt(input_quantidadeVariaveis_DIC.getText().toString()));

        id_Modelo = dbAuxilar.inserirModelo(modelo);

        Intent cadastroTratamentos = new Intent(CadastroModeloFormulario.this, CadastroTratamentos.class);
        cadastroTratamentos.putExtra("id_Modelo", id_Modelo);
        cadastroTratamentos.putExtra("idFormulario_Modelo", id_Formulario);
        startActivity(cadastroTratamentos);


    }

    private boolean validarQuantidadeTratamentos_DIC(){
        if(input_quantidadeTratamentos_DIC.getText().toString().trim().isEmpty()){
            inputLayoutQuantidadeTratamentos_DIC.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeTratamentos_DIC);
            return false;
        }
        else{
            inputLayoutQuantidadeTratamentos_DIC.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeRepeticoes_DIC(){
        if(input_quantidadeRepeticoes_DIC.getText().toString().trim().isEmpty()){
            inputLayoutQuantidadeRepeticoes_DIC.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeRepeticoes_DIC);
            return false;
        }
        else{
            inputLayoutQuantidadeRepeticoes_DIC.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeReplicacoes_DIC(){
        if(input_quantidadeReplicacoes_DIC.getText().toString().trim().isEmpty()){
            inputLayoutQuantidadeReplicacoes_DIC.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeReplicacoes_DIC);
            return false;
        }
        else{
            inputLayoutQuantidadeReplicacoes_DIC.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeVariaveis_DIC(){
        if(input_quantidadeVariaveis_DIC.getText().toString().trim().isEmpty()){
            inputLayoutQuantidadeVariaveis_DIC.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeVariaveis_DIC);
            return false;
        }
        else{
            inputLayoutQuantidadeVariaveis_DIC.setErrorEnabled(false);
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
                case R.id.input_quantidadeTratatmentos_DIC:
                    validarQuantidadeTratamentos_DIC();
                    break;
                case R.id.input_quantidadeRepeticoes_DIC:
                    validarQuantidadeRepeticoes_DIC();
                    break;
                case R.id.input_quantidadeReplicacoes_DIC:
                    validarQuantidadeReplicacoes_DIC();
                    break;
                case R.id.input_quantidadeVariaveis_DIC:
                    validarQuantidadeVariaveis_DIC();
                    break;

            }

        }
    }

}
