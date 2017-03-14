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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.modelos.Formulario;

public class CadastroFormulario extends AppCompatActivity {

    private TextInputEditText inputNome_Formulario, inputDescricao_Formulario, inputCriador_Formulario, input_quantidadeTratamentos_Formulario, input_quantidadeRepeticoes_Formulario, input_quantidadeReplicacoes_Formulario, input_quantidadeVariaveis_Formulario;
    private TextInputLayout inputLayoutNome_Formulario, inputLayoutDescricao_Formulario, inputLayoutCriador_Formulario, inputLayoutQuantidadeTratamentos_Formulario, inputLayoutQuantidadeRepeticoes_Formulario, inputLayoutQuantidadeReplicacoes_Formulario, inputLayoutQuantidadeVariaveis_Formulario;

    private TextView tv_modelo_Formulario;

    private RadioGroup radioGroup_modelo_Formulario;

    private RadioButton radioDIC, radioDBC, radioFAT, radioSUB;

    private String tipoFormulario;

    private Integer modelo_Formulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent cadastroFormulario = getIntent();
        tipoFormulario = cadastroFormulario.getSerializableExtra("tipo_Formulario").toString();
        final Long id_Formulario = cadastroFormulario.getLongExtra("id_Formulario", 0);

        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + tipoFormulario);

        radioGroup_modelo_Formulario = (RadioGroup) findViewById(R.id.input_modelo_Formulario);
        radioDIC = (RadioButton) findViewById(R.id.radio_DIC);
        radioDBC = (RadioButton) findViewById(R.id.radio_DBC);
        radioFAT = (RadioButton) findViewById(R.id.radio_FAT);
        radioSUB = (RadioButton) findViewById(R.id.radio_SUB);

        tv_modelo_Formulario = (TextView) findViewById(R.id.tv_radio_modelo_Formulario);

        inputNome_Formulario = (TextInputEditText) findViewById(R.id.input_nome_Formulario);
        inputDescricao_Formulario = (TextInputEditText) findViewById(R.id.input_descricao_Formulario);
        inputCriador_Formulario = (TextInputEditText) findViewById(R.id.input_criador_Formulario);
        input_quantidadeTratamentos_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeTratatmentos_Formulario);
        input_quantidadeRepeticoes_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeRepeticoes_Formulario);
        input_quantidadeReplicacoes_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeReplicacoes_Formulario);
        input_quantidadeVariaveis_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeVariaveis_Formulario);

        inputLayoutNome_Formulario = (TextInputLayout) findViewById(R.id.input_layout_nome_Formulario);
        inputLayoutDescricao_Formulario = (TextInputLayout) findViewById(R.id.input_layout_descricao_Formulario);
        inputLayoutCriador_Formulario = (TextInputLayout) findViewById(R.id.input_layout_criador_Formulario);
        inputLayoutQuantidadeTratamentos_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeTratatmentos_Formulario);
        inputLayoutQuantidadeRepeticoes_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeRepeticoes_Formulario);
        inputLayoutQuantidadeReplicacoes_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeReplicacoes_Formulario);
        inputLayoutQuantidadeVariaveis_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeVariaveis_Formulario);

        inputNome_Formulario.addTextChangedListener(new FormularioTextWatcher(inputNome_Formulario));
        inputDescricao_Formulario.addTextChangedListener(new FormularioTextWatcher(inputDescricao_Formulario));
        inputCriador_Formulario.addTextChangedListener(new FormularioTextWatcher(inputCriador_Formulario));
        input_quantidadeTratamentos_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeTratamentos_Formulario));
        input_quantidadeRepeticoes_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeRepeticoes_Formulario));
        input_quantidadeReplicacoes_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeReplicacoes_Formulario));
        input_quantidadeVariaveis_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeVariaveis_Formulario));

        Button btnSalvar_Formulario = (Button) findViewById(R.id.btn_salvar_Formulario);

        radioGroup_modelo_Formulario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tv_modelo_Formulario.setError(null);
                tv_modelo_Formulario.setTextAppearance(getApplicationContext(), R.style.TextAppearence_App_TextInputLayout);
            }
        });

        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());


        if (id_Formulario != 0) {
            Formulario formulario = dbAuxilar.lerFormulario(id_Formulario);
            radioGroup_modelo_Formulario.getChildAt(formulario.getModelo_Formulario()).setSelected(true);
            inputNome_Formulario.setText(formulario.getNome_Formulario());
            inputDescricao_Formulario.setText(formulario.getDescricao_Formulario());
            inputCriador_Formulario.setText(formulario.getCriador_Formulario());
        }


        btnSalvar_Formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDados();
            }
        });

        radioGroup_modelo_Formulario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_DIC) {
                    modelo_Formulario = 0;
                } else if (checkedId == R.id.radio_DBC) {
                    modelo_Formulario = 1;
                } else if (checkedId == R.id.radio_FAT) {
                    modelo_Formulario = 2;
                } else if (checkedId == R.id.radio_SUB) {
                    modelo_Formulario = 3;
                }
            }
        });


    }


    private void salvarDados() {
        if (!validarTipo_Formulario() || !validarNome_Formulario() || !validarDescricao_Formulario() ||
                !validarCriador_Formulario() || !validarQuantidadeTratamentos_Formulario() ||
                !validarQuantidadeRepeticoes_Formulario() || !validarQuantidadeReplicacoes_Formulario() ||
                !validarQuantidadeVariaveis_Formulario()) {
            return;
        }

        Intent cadastroTratamentos = new Intent(CadastroFormulario.this, CadastroTratamentos.class);
        Formulario formulario = new Formulario();
        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());

        formulario.setTipo_Formulario(tipoFormulario);
        formulario.setModelo_Formulario(modelo_Formulario);
        formulario.setNome_Formulario(inputNome_Formulario.getText().toString());
        formulario.setDescricao_Formulario(inputDescricao_Formulario.getText().toString());
        formulario.setCriador_Formulario(inputCriador_Formulario.getText().toString());
        formulario.setDataCriacao_Formulario(new Date().toString());
        formulario.setQuantidadeTratamentos_Formulario(Integer.valueOf(input_quantidadeTratamentos_Formulario.getText().toString()));
        formulario.setQuantidadeRepeticoes_Formulario(Integer.valueOf(input_quantidadeRepeticoes_Formulario.getText().toString()));
        formulario.setQuantidadeReplicacoes_Formulario(Integer.valueOf(input_quantidadeReplicacoes_Formulario.getText().toString()));
        formulario.setQuantidadeVariaveis_Formulario(Integer.valueOf(input_quantidadeVariaveis_Formulario.getText().toString()));
        //formulario.setQuantidadeBlocos_Formulario(Integer.valueOf());
        //formulario.setQuantidadeFatores_Formulario(Integer.valueOf());
        //formulario.setQuantidadeDivisoes_Formulario(Integer.valueOf());
        formulario.setStatus_Formulario(1);

        Long id_Formulario = dbAuxilar.inserirFormulario(formulario);

        cadastroTratamentos.putExtra("id_Formulario", id_Formulario);
        startActivity(cadastroTratamentos);

    }

    public boolean validarTipo_Formulario() {
        int checked = radioGroup_modelo_Formulario.getCheckedRadioButtonId();
        tv_modelo_Formulario.setError(null);
        switch (checked){
            case R.id.radio_DIC:
                modelo_Formulario = 0;
                return true;
            case R.id.radio_DBC:
                modelo_Formulario = 1;
                return true;
            case R.id.radio_FAT:
                modelo_Formulario = 2;
                return true;
            case R.id.radio_SUB:
                modelo_Formulario = 3;
                return true;
            default:
                tv_modelo_Formulario.setError(getText(R.string.err_msg_selecioneUmRadio));
                return false;
        }
    }

    private boolean validarNome_Formulario() {
        if (inputNome_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutNome_Formulario.setError(getString(R.string.err_msg_nome_Formulario));
            requestFocus(inputNome_Formulario);
            return false;
        } else {
            inputLayoutNome_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarDescricao_Formulario() {

        if (inputDescricao_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutDescricao_Formulario.setError(getString(R.string.err_msg_descricao_Formulario));
            requestFocus(inputDescricao_Formulario);
            return false;
        } else {
            inputLayoutDescricao_Formulario.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarCriador_Formulario() {
        if (inputCriador_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutCriador_Formulario.setError(getString(R.string.err_msg_criador_Formulario));
            requestFocus(inputCriador_Formulario);
            return false;
        } else {
            inputLayoutCriador_Formulario.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarQuantidadeTratamentos_Formulario() {
        if (input_quantidadeTratamentos_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeTratamentos_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeTratamentos_Formulario);
            return false;
        } else if (Integer.valueOf(input_quantidadeTratamentos_Formulario.getText().toString()) < 1) {
            inputLayoutQuantidadeTratamentos_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeTratamentos_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeTratamentos_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeRepeticoes_Formulario() {
        if (input_quantidadeRepeticoes_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeRepeticoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeRepeticoes_Formulario);
            return false;
        } else if (Integer.valueOf(input_quantidadeRepeticoes_Formulario.getText().toString()) < 1) {
            if (modelo_Formulario != 1) {
                inputLayoutQuantidadeRepeticoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
                requestFocus(input_quantidadeRepeticoes_Formulario);
                return false;
            }
        } else {
            inputLayoutQuantidadeRepeticoes_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeReplicacoes_Formulario() {
        if (Integer.valueOf(input_quantidadeReplicacoes_Formulario.getText().toString()) < 1) {
            if (modelo_Formulario == 2 || modelo_Formulario == 3) {
                inputLayoutQuantidadeReplicacoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
                requestFocus(input_quantidadeReplicacoes_Formulario);
                return false;
            }
        } else if (input_quantidadeReplicacoes_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeReplicacoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeReplicacoes_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeReplicacoes_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeVariaveis_Formulario() {
        if (Integer.valueOf(input_quantidadeVariaveis_Formulario.getText().toString()) < 1) {
            inputLayoutQuantidadeVariaveis_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeVariaveis_Formulario);
            return false;
        } else if (input_quantidadeVariaveis_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeVariaveis_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeVariaveis_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeVariaveis_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class FormularioTextWatcher implements TextWatcher {

        private View view;

        private FormularioTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_nome_Formulario:
                    validarNome_Formulario();
                    break;
                case R.id.input_descricao_Formulario:
                    validarDescricao_Formulario();
                    break;
                case R.id.input_criador_Formulario:
                    validarCriador_Formulario();
                    break;
                case R.id.input_quantidadeTratatmentos_Formulario:
                    validarQuantidadeTratamentos_Formulario();
                    break;
                case R.id.input_quantidadeRepeticoes_Formulario:
                    validarQuantidadeRepeticoes_Formulario();
                    break;
                case R.id.input_quantidadeReplicacoes_Formulario:
                    validarQuantidadeReplicacoes_Formulario();
                    break;
                case R.id.input_quantidadeVariaveis_Formulario:
                    validarQuantidadeVariaveis_Formulario();
                    break;
            }
        }
    }

}
