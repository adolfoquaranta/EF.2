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

    private TextInputEditText inputNome_Formulario, inputDescricao_Formulario, inputCriador_Formulario, input_quantidadeTratamentos_Formulario, input_quantidadeRepeticoes_Formulario, input_quantidadeReplicacoes_Formulario, input_quantidadeVariaveis_Formulario, input_quantidadeBlocos_Formulario, input_quantidadeFatores_Formulario, input_quantidadeParcelas_Formulario;
    private TextInputLayout inputLayoutNome_Formulario, inputLayoutDescricao_Formulario, inputLayoutCriador_Formulario, inputLayoutQuantidadeTratamentos_Formulario, inputLayoutQuantidadeRepeticoes_Formulario, inputLayoutQuantidadeReplicacoes_Formulario, inputLayoutQuantidadeVariaveis_Formulario, inputLayoutQuantidadeBlocos_Formulario, inputLayoutQuantidadeFatores_Formulario, inputLayoutQuantidadeParcelas_Formulario;

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


        inputLayoutNome_Formulario = (TextInputLayout) findViewById(R.id.input_layout_nome_Formulario);
        inputLayoutDescricao_Formulario = (TextInputLayout) findViewById(R.id.input_layout_descricao_Formulario);
        inputLayoutCriador_Formulario = (TextInputLayout) findViewById(R.id.input_layout_criador_Formulario);
        inputLayoutQuantidadeTratamentos_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeTratatmentos_Formulario);
        inputLayoutQuantidadeRepeticoes_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeRepeticoes_Formulario);
        inputLayoutQuantidadeReplicacoes_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeReplicacoes_Formulario);
        inputLayoutQuantidadeVariaveis_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeVariaveis_Formulario);
        inputLayoutQuantidadeBlocos_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeBlocos_Formulario);
        inputLayoutQuantidadeFatores_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeFatores_Formulario);
        inputLayoutQuantidadeParcelas_Formulario = (TextInputLayout) findViewById(R.id.input_layout_quantidadeParcelas_Formulario);

        inputNome_Formulario = (TextInputEditText) findViewById(R.id.input_nome_Formulario);
        inputDescricao_Formulario = (TextInputEditText) findViewById(R.id.input_descricao_Formulario);
        inputCriador_Formulario = (TextInputEditText) findViewById(R.id.input_criador_Formulario);
        input_quantidadeTratamentos_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeTratatmentos_Formulario);
        input_quantidadeRepeticoes_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeRepeticoes_Formulario);
        input_quantidadeReplicacoes_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeReplicacoes_Formulario);
        input_quantidadeVariaveis_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeVariaveis_Formulario);
        input_quantidadeBlocos_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeBlocos_Formulario);
        input_quantidadeFatores_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeFatores_Formulario);
        input_quantidadeParcelas_Formulario = (TextInputEditText) findViewById(R.id.input_quantidadeParcelas_Formulario);


        inputNome_Formulario.addTextChangedListener(new FormularioTextWatcher(inputNome_Formulario));
        inputDescricao_Formulario.addTextChangedListener(new FormularioTextWatcher(inputDescricao_Formulario));
        inputCriador_Formulario.addTextChangedListener(new FormularioTextWatcher(inputCriador_Formulario));
        input_quantidadeTratamentos_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeTratamentos_Formulario));
        input_quantidadeRepeticoes_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeRepeticoes_Formulario));
        input_quantidadeReplicacoes_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeReplicacoes_Formulario));
        input_quantidadeVariaveis_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeVariaveis_Formulario));
        input_quantidadeBlocos_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeBlocos_Formulario));
        input_quantidadeFatores_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeFatores_Formulario));
        input_quantidadeParcelas_Formulario.addTextChangedListener(new FormularioTextWatcher(input_quantidadeParcelas_Formulario));


        inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.GONE);
        inputLayoutQuantidadeRepeticoes_Formulario.setVisibility(View.GONE);
        inputLayoutQuantidadeReplicacoes_Formulario.setVisibility(View.GONE);
        inputLayoutQuantidadeVariaveis_Formulario.setVisibility(View.GONE);
        inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.GONE);
        inputLayoutQuantidadeFatores_Formulario.setVisibility(View.GONE);
        inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.GONE);

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
                inputLayoutQuantidadeRepeticoes_Formulario.setVisibility(View.VISIBLE);
                inputLayoutQuantidadeReplicacoes_Formulario.setVisibility(View.VISIBLE);
                inputLayoutQuantidadeVariaveis_Formulario.setVisibility(View.VISIBLE);
                if (checkedId == R.id.radio_DIC) {
                    inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.VISIBLE);
                    inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.GONE);
                    inputLayoutQuantidadeFatores_Formulario.setVisibility(View.GONE);
                    inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.GONE);
                    modelo_Formulario = 0;
                } else if (checkedId == R.id.radio_DBC) {
                    inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.VISIBLE);
                    inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.VISIBLE);
                    inputLayoutQuantidadeFatores_Formulario.setVisibility(View.GONE);
                    inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.GONE);
                    modelo_Formulario = 1;
                } else if (checkedId == R.id.radio_FAT) {
                    inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.GONE);
                    inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.VISIBLE);
                    inputLayoutQuantidadeFatores_Formulario.setVisibility(View.VISIBLE);
                    inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.GONE);
                    modelo_Formulario = 2;
                } else if (checkedId == R.id.radio_SUB) {
                    inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.GONE);
                    inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.VISIBLE);
                    inputLayoutQuantidadeFatores_Formulario.setVisibility(View.GONE);
                    inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.VISIBLE);
                    modelo_Formulario = 3;
                }
            }
        });


    }

    private void salvarDados() {
        if (!validarTipo_Formulario() || !validarNome_Formulario() || !validarDescricao_Formulario() || !validarCriador_Formulario()) {
            return;
        }
        if (modelo_Formulario == 0) {
            if (!validarQuantidadeTratamentos_Formulario()) {
                return;
            }
            inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.VISIBLE);
            inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.GONE);
            inputLayoutQuantidadeFatores_Formulario.setVisibility(View.GONE);
            inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.GONE);
        }
        if (modelo_Formulario == 1) {
            if (!validarQuantidadeBlocos_Formulario() || !validarQuantidadeTratamentos_Formulario()) {
                return;
            }
            inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.VISIBLE);
            inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.VISIBLE);
            inputLayoutQuantidadeFatores_Formulario.setVisibility(View.GONE);
            inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.GONE);
        }
        if (modelo_Formulario == 2) {
            if (!validarQuantidadeFatores_Formulario() || !validarQuantidadeBlocos_Formulario()) {
                return;
            }
            inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.GONE);
            inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.VISIBLE);
            inputLayoutQuantidadeFatores_Formulario.setVisibility(View.VISIBLE);
            inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.GONE);
        }
        if (modelo_Formulario == 3) {
            if (!validarQuantidadeParcelas_Formulario() || !validarQuantidadeBlocos_Formulario()) {
                return;
            }
            inputLayoutQuantidadeTratamentos_Formulario.setVisibility(View.GONE);
            inputLayoutQuantidadeBlocos_Formulario.setVisibility(View.VISIBLE);
            inputLayoutQuantidadeFatores_Formulario.setVisibility(View.GONE);
            inputLayoutQuantidadeParcelas_Formulario.setVisibility(View.VISIBLE);
        }
        if (!validarQuantidadeRepeticoes_Formulario(modelo_Formulario) || !validarQuantidadeReplicacoes_Formulario(modelo_Formulario) || !validarQuantidadeVariaveis_Formulario()) {
            return;
        }

        Intent cadastroTratamentos = new Intent(CadastroFormulario.this, CadastroTratamentos.class);
        Intent cadastroFatores = new Intent(CadastroFormulario.this, CadastroFatores.class);

        Formulario formulario = new Formulario();
        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());

        formulario.setTipo_Formulario(tipoFormulario);
        formulario.setModelo_Formulario(modelo_Formulario);
        formulario.setNome_Formulario(inputNome_Formulario.getText().toString());
        formulario.setDescricao_Formulario(inputDescricao_Formulario.getText().toString());
        formulario.setCriador_Formulario(inputCriador_Formulario.getText().toString());
        formulario.setDataCriacao_Formulario(new Date().toString());

        if (modelo_Formulario == 0 || modelo_Formulario == 1) {
            formulario.setQuantidadeTratamentos_Formulario(Integer.valueOf(input_quantidadeTratamentos_Formulario.getText().toString()));
        }

        Integer repeticoes = Integer.valueOf(input_quantidadeRepeticoes_Formulario.getText().toString());
        if (repeticoes == 0) {
            formulario.setQuantidadeRepeticoes_Formulario(1);
        } else {
            formulario.setQuantidadeRepeticoes_Formulario(repeticoes);
        }

        Integer replicacoes = Integer.valueOf(input_quantidadeReplicacoes_Formulario.getText().toString());
        if (replicacoes == 0) {
            formulario.setQuantidadeReplicacoes_Formulario(1);
        } else {
            formulario.setQuantidadeReplicacoes_Formulario(replicacoes);
        }

        formulario.setQuantidadeVariaveis_Formulario(Integer.valueOf(input_quantidadeVariaveis_Formulario.getText().toString()));

        if (modelo_Formulario == 0) formulario.setQuantidadeBlocos_Formulario(-1);
        else
            formulario.setQuantidadeBlocos_Formulario(Integer.valueOf(input_quantidadeBlocos_Formulario.getText().toString()));

        if (modelo_Formulario == 2)
            formulario.setQuantidadeFatores_Formulario(Integer.valueOf(input_quantidadeFatores_Formulario.getText().toString()));
        else formulario.setQuantidadeFatores_Formulario(-1);

        if (modelo_Formulario == 3)
            formulario.setQuantidadeParcelas_Formulario(Integer.valueOf(input_quantidadeParcelas_Formulario.getText().toString()));
        else formulario.setQuantidadeParcelas_Formulario(-1);

        formulario.setStatus_Formulario(1);

        Long id_Formulario = dbAuxilar.inserirFormulario(formulario);

        cadastroTratamentos.putExtra("id_Formulario", id_Formulario);
        cadastroFatores.putExtra("id_Formulario", id_Formulario);

        finish();

        if (modelo_Formulario > 1) startActivity(cadastroFatores);
        else startActivity(cadastroTratamentos);

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
        } else if (input_quantidadeTratamentos_Formulario.getText().toString().trim().equals(String.valueOf(0))) {
            inputLayoutQuantidadeTratamentos_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeTratamentos_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeTratamentos_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeRepeticoes_Formulario(Integer modelo) {
        if (input_quantidadeRepeticoes_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeRepeticoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeRepeticoes_Formulario);
            return false;
        }
        if (input_quantidadeRepeticoes_Formulario.getText().toString().trim().equals(String.valueOf(0)) && modelo != 1) {
            input_quantidadeRepeticoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeRepeticoes_Formulario);
            return false;
        }
        inputLayoutQuantidadeRepeticoes_Formulario.setErrorEnabled(false);
        return true;
    }

    private boolean validarQuantidadeReplicacoes_Formulario(Integer modelo) {
        if (input_quantidadeReplicacoes_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeReplicacoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeReplicacoes_Formulario);
            return false;
        }
        if (input_quantidadeReplicacoes_Formulario.getText().toString().trim().equals(String.valueOf(0)) && (modelo == 2 || modelo == 3)) {
            inputLayoutQuantidadeReplicacoes_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeReplicacoes_Formulario);
            return false;
        }
        inputLayoutQuantidadeReplicacoes_Formulario.setErrorEnabled(false);
        return true;
    }

    private boolean validarQuantidadeVariaveis_Formulario() {
        if (input_quantidadeVariaveis_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeVariaveis_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeVariaveis_Formulario);
            return false;
        } else if (input_quantidadeVariaveis_Formulario.getText().toString().trim().equals(String.valueOf(0))) {
            inputLayoutQuantidadeVariaveis_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeVariaveis_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeVariaveis_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeBlocos_Formulario() {
        if (input_quantidadeBlocos_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeBlocos_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeBlocos_Formulario);
            return false;
        } else if (input_quantidadeBlocos_Formulario.getText().toString().trim().equals(String.valueOf(0))) {
            inputLayoutQuantidadeBlocos_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeBlocos_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeBlocos_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeFatores_Formulario() {
        if (input_quantidadeFatores_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeFatores_Formulario.setError(getString(R.string.err_msg_NumFatoresInvalido));
            requestFocus(input_quantidadeFatores_Formulario);
            return false;
        } else if (Integer.valueOf(input_quantidadeFatores_Formulario.getText().toString().trim()) < 2
                || Integer.valueOf(input_quantidadeFatores_Formulario.getText().toString().trim()) > 3) {
            inputLayoutQuantidadeFatores_Formulario.setError(getString(R.string.err_msg_NumFatoresInvalido));
            requestFocus(input_quantidadeFatores_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeFatores_Formulario.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validarQuantidadeParcelas_Formulario() {
        if (input_quantidadeParcelas_Formulario.getText().toString().trim().isEmpty()) {
            inputLayoutQuantidadeParcelas_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiro));
            requestFocus(input_quantidadeParcelas_Formulario);
            return false;
        } else if (input_quantidadeParcelas_Formulario.getText().toString().trim().equals(String.valueOf(0))) {
            inputLayoutQuantidadeParcelas_Formulario.setError(getString(R.string.err_msg_deveSerNumInteiroNaoNulo));
            requestFocus(input_quantidadeParcelas_Formulario);
            return false;
        } else {
            inputLayoutQuantidadeParcelas_Formulario.setErrorEnabled(false);
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
                    validarQuantidadeRepeticoes_Formulario(modelo_Formulario);
                    break;
                case R.id.input_quantidadeReplicacoes_Formulario:
                    validarQuantidadeReplicacoes_Formulario(modelo_Formulario);
                    break;
                case R.id.input_quantidadeVariaveis_Formulario:
                    validarQuantidadeVariaveis_Formulario();
                    break;
                case R.id.input_quantidadeBlocos_Formulario:
                    validarQuantidadeBlocos_Formulario();
                    break;
                case R.id.input_quantidadeFatores_Formulario:
                    validarQuantidadeFatores_Formulario();
                    break;
                case R.id.input_quantidadeParcelas_Formulario:
                    validarQuantidadeParcelas_Formulario();
                    break;
            }
        }
    }

}
