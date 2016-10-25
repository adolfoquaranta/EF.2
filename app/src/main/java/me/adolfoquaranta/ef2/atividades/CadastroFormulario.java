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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.Formulario;

public class CadastroFormulario extends AppCompatActivity {

    private TextInputEditText inputNome_Form, inputDescricao_Form, inputCriador_Form;
    private TextInputLayout inputLayoutNome_Form, inputLayoutDescricao_Form, inputLayoutCriador_Form;

    private TextView tv_tipo_Form;

    private RadioGroup radioGroup_tipo_Form;

    private String modelo_Form;

    private String tipo_Form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent cadastroFormulario = getIntent();
        modelo_Form = cadastroFormulario.getSerializableExtra("modelo_Form").toString();

        getSupportActionBar().setTitle( getSupportActionBar().getTitle().toString() + " " + modelo_Form);

        radioGroup_tipo_Form = (RadioGroup) findViewById(R.id.input_tipo_Form);
        tv_tipo_Form = (TextView) findViewById(R.id.tv_radio_tipo_Form);

        inputNome_Form = (TextInputEditText) findViewById(R.id.input_nome_Form);
        inputDescricao_Form = (TextInputEditText) findViewById(R.id.input_descricao_Form);
        inputCriador_Form = (TextInputEditText) findViewById(R.id.input_criador_Form);

        inputLayoutNome_Form = (TextInputLayout) findViewById(R.id.input_layout_nome_Form);
        inputLayoutDescricao_Form = (TextInputLayout) findViewById(R.id.input_layout_descricao_Form);
        inputLayoutCriador_Form = (TextInputLayout) findViewById(R.id.input_layout_criador_Form);

        inputNome_Form.addTextChangedListener(new FormularioTextWatcher(inputNome_Form));
        inputDescricao_Form.addTextChangedListener(new FormularioTextWatcher(inputDescricao_Form));
        inputCriador_Form.addTextChangedListener(new FormularioTextWatcher(inputCriador_Form));

        Button btnSalvar_Formulario = (Button) findViewById(R.id.btn_salvar_Formulario);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        radioGroup_tipo_Form.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tv_tipo_Form.setError(null);
                tv_tipo_Form.setTextAppearance(getApplicationContext(), R.style.TextAppearence_App_TextInputLayout);
            }
        });

        btnSalvar_Formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDados();
            }
        });


    }


    private void salvarDados() {

        if(!validarTipo_Form()){
            return;
        }

        if (!validarNome_Form()) {
            return;
        }

        if (!validarDescricao_Form()) {
            return;
        }

        if (!validarCriador_Form()) {
            return;
        }

        Date date = new Date();
        Formulario formulario = new Formulario();
        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());

        formulario.setModelo_Form(modelo_Form);
        formulario.setNome_Form(inputNome_Form.getText().toString());
        formulario.setDescricao_Form(inputDescricao_Form.getText().toString());
        formulario.setCriador_Form(inputCriador_Form.getText().toString());
        formulario.setDataCriacao_Form(date.toString());
        formulario.setStatus_Form("Incompleto");

        Long id_Form = dbAuxilar.inserirFormulario(formulario);

        if(tipo_Form.equals(getString(R.string.radio_dic))) {
            Intent cadastroFomularioDIC = new Intent(CadastroFormulario.this, CadastroFormularioDIC.class);
            cadastroFomularioDIC.putExtra("id_Form", id_Form);
            startActivity(cadastroFomularioDIC);
        }
        else Toast.makeText(getApplicationContext(), "Em Breve", Toast.LENGTH_SHORT).show();


    }

    public boolean validarTipo_Form() {
        int checked = radioGroup_tipo_Form.getCheckedRadioButtonId();
        tv_tipo_Form.setError(null);
        switch (checked){
            case R.id.radio_DIC:
                tipo_Form = getText(R.string.radio_dic).toString();
                return true;
            case R.id.radio_DBC:
                tipo_Form = getText(R.string.radio_dbc).toString();
                return true;
            case R.id.radio_FAT:
                tipo_Form = getText(R.string.radio_fat).toString();
                return true;
            case R.id.radio_SUB:
                tipo_Form = getText(R.string.radio_sub).toString();
                return true;
            default:
                tv_tipo_Form.setError(getText(R.string.err_msg_selecioneUmRadio));
                return false;
        }
    }

    private boolean validarNome_Form() {
        if (inputNome_Form.getText().toString().trim().isEmpty()) {
            inputLayoutNome_Form.setError(getString(R.string.err_msg_nome_Form));
            requestFocus(inputNome_Form);
            return false;
        } else {
            inputLayoutNome_Form.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarDescricao_Form() {

        if (inputDescricao_Form.getText().toString().trim().isEmpty()) {
            inputLayoutDescricao_Form.setError(getString(R.string.err_msg_descricao_Form));
            requestFocus(inputDescricao_Form);
            return false;
        } else {
            inputLayoutDescricao_Form.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validarCriador_Form() {
        if (inputCriador_Form.getText().toString().trim().isEmpty()) {
            inputLayoutCriador_Form.setError(getString(R.string.err_msg_criador_Form));
            requestFocus(inputCriador_Form);
            return false;
        } else {
            inputLayoutCriador_Form.setErrorEnabled(false);
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
                case R.id.input_nome_Form:
                    validarNome_Form();
                    break;
                case R.id.input_descricao_Form:
                    validarDescricao_Form();
                    break;
                case R.id.input_criador_Form:
                    validarCriador_Form();
                    break;
            }
        }
    }

}
