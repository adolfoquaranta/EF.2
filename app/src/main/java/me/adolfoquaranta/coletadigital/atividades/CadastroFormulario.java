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

import java.util.ArrayList;
import java.util.Date;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.Modelo;

public class CadastroFormulario extends AppCompatActivity {

    private TextInputEditText inputNome_Form, inputDescricao_Form, inputCriador_Form;
    private TextInputLayout inputLayoutNome_Form, inputLayoutDescricao_Form, inputLayoutCriador_Form;

    private TextView tv_modelo_Modelo;

    private RadioGroup radioGroup_modelo_Modelo;

    private RadioButton radioDIC, radioDBC, radioFAT, radioSUB;

    private String tipoFormulario;

    private Integer modelo_Modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent cadastroFormulario = getIntent();
        tipoFormulario = cadastroFormulario.getSerializableExtra("tipo_Formulario").toString();
        final Long idFormulario = cadastroFormulario.getLongExtra("id_Formulario", 0);
        final String acao = cadastroFormulario.getStringExtra("acao");

        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + tipoFormulario);

        radioGroup_modelo_Modelo = (RadioGroup) findViewById(R.id.input_modelo_Modelo);
        radioDIC = (RadioButton) findViewById(R.id.radio_DIC);
        radioDBC = (RadioButton) findViewById(R.id.radio_DBC);
        radioFAT = (RadioButton) findViewById(R.id.radio_FAT);
        radioSUB = (RadioButton) findViewById(R.id.radio_SUB);

        tv_modelo_Modelo = (TextView) findViewById(R.id.tv_radio_modelo_Modelo);

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

        radioGroup_modelo_Modelo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tv_modelo_Modelo.setError(null);
                tv_modelo_Modelo.setTextAppearance(getApplicationContext(), R.style.TextAppearence_App_TextInputLayout);
            }
        });

        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());


        if (idFormulario != 0) {
            Formulario formulario = dbAuxilar.lerFormulario(idFormulario);
            ArrayList<Modelo> modelos = dbAuxilar.lerTodosModelos(idFormulario);

            if (acao.equals("editar")) {
                radioDIC.setEnabled(false);
                radioDBC.setEnabled(false);
                radioFAT.setEnabled(false);
                radioSUB.setEnabled(false);
                ArrayList<Integer> radiosModelo = new ArrayList<>();
                for (Modelo m : modelos) {
                    radiosModelo.add(m.getModelo_Modelo());
                }
                for (int i = 0; i < radiosModelo.size(); i++) {
                    radioGroup_modelo_Modelo.getChildAt(radiosModelo.get(i)).setEnabled(true);
                }

                inputNome_Form.setText(formulario.getNome_Form());
                inputDescricao_Form.setText(formulario.getDescricao_Form());
                inputCriador_Form.setText(formulario.getCriador_Form());
            }
            if (acao.equals("cadastro")) {
                radioDIC.setEnabled(true);
                radioDBC.setEnabled(true);
                radioFAT.setEnabled(true);
                radioSUB.setEnabled(true);
                ArrayList<Integer> radiosModelo = new ArrayList<>();
                for (Modelo m : modelos) {
                    radiosModelo.add(m.getModelo_Modelo());
                }
                for (int i = 0; i < radiosModelo.size(); i++) {
                    radioGroup_modelo_Modelo.getChildAt(radiosModelo.get(i)).setEnabled(false);
                }
            }


        }


        btnSalvar_Formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDados(idFormulario, acao);
            }
        });

        radioGroup_modelo_Modelo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_DIC) {
                    modelo_Modelo = 0;
                } else if (checkedId == R.id.radio_DBC) {
                    modelo_Modelo = 1;
                } else if (checkedId == R.id.radio_FAT) {
                    modelo_Modelo = 2;
                } else if (checkedId == R.id.radio_SUB) {
                    modelo_Modelo = 3;
                }
            }
        });


    }


    private void salvarDados(Long id_Formulario, String acao) {

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

        formulario.setTipo_Form(tipoFormulario);
        formulario.setNome_Form(inputNome_Form.getText().toString());
        formulario.setDescricao_Form(inputDescricao_Form.getText().toString());
        formulario.setCriador_Form(inputCriador_Form.getText().toString());
        formulario.setDataCriacao_Form(date.toString());
        formulario.setStatus_Form("");

        Intent cadastroModeloFomulario = new Intent(CadastroFormulario.this, CadastroModeloFormulario.class);
        if (id_Formulario == 0) {
            id_Formulario = dbAuxilar.inserirFormulario(formulario);
            cadastroModeloFomulario.putExtra("id_Formulario", id_Formulario);
            cadastroModeloFomulario.putExtra("modelo_Modelo", modelo_Modelo);
            startActivity(cadastroModeloFomulario);
        } else {
            if (acao.equals("editar")) {
                Intent cadastroTratamentos = new Intent(CadastroFormulario.this, CadastroTratamentos.class);
                Modelo modelo = dbAuxilar.lerModeloDoFormulario(id_Formulario, modelo_Modelo);
                formulario.setId_Form(id_Formulario);
                dbAuxilar.updateFormulario(formulario);
                cadastroTratamentos.putExtra("idFormulario_Modelo", id_Formulario);
                cadastroTratamentos.putExtra("modelo_Modelo", modelo_Modelo);
                cadastroTratamentos.putExtra("id_Modelo", modelo.getId_Modelo());
                startActivity(cadastroTratamentos);
            } else if (acao.equals("cadastro")) {
                cadastroModeloFomulario.putExtra("id_Formulario", id_Formulario);
                cadastroModeloFomulario.putExtra("modelo_Modelo", modelo_Modelo);
                startActivity(cadastroModeloFomulario);
            }
        }


    }

    public boolean validarTipo_Form() {
        int checked = radioGroup_modelo_Modelo.getCheckedRadioButtonId();
        tv_modelo_Modelo.setError(null);
        switch (checked){
            case R.id.radio_DIC:
                modelo_Modelo = 0;
                return true;
            case R.id.radio_DBC:
                modelo_Modelo = 1;
                return true;
            case R.id.radio_FAT:
                modelo_Modelo = 2;
                return true;
            case R.id.radio_SUB:
                modelo_Modelo = 3;
                return true;
            default:
                tv_modelo_Modelo.setError(getText(R.string.err_msg_selecioneUmRadio));
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
