package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;


public class CadastroTratamentos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tratamentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent cadastroTratamentos = getIntent();
        final Long id_Formulario = cadastroTratamentos.getLongExtra("id_Formulario", 0);
        final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_nomeTratamento), "^(?!\\s*$).+");

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());
        final Formulario formulario = dbauxiliar.lerFormulario(id_Formulario);
        final ArrayList<Tratamento> tratamentosCadastrados = dbauxiliar.lerTodosTratamentos(id_Formulario);

        final View.OnFocusChangeListener validar = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v instanceof MaterialEditText){
                    MaterialEditText editText = (MaterialEditText) v;
                    editText.validateWith(naoNulo);
                }
                if(v instanceof MaterialSpinner){
                    MaterialSpinner spinner = (MaterialSpinner) v;
                    if(spinner.getSelectedItemPosition()==0){
                        spinner.setError("Error");
                    }
                    else{
                        spinner.setError(null);
                    }
                }
            }
        };

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_Tratamentos);
        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);
        String[] opcoesSpinnerTipoTratamento = getResources().getStringArray(R.array.spinnerArray_tipoTratamento);


        for (int i = 0; i < formulario.getQuantidadeTratamentos_Formulario(); i++) {
            LinearLayout layoutInterno= new LinearLayout(CadastroTratamentos.this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(3);

            //nome tratamento
            MaterialEditText etNomeTratamento = new MaterialEditText(CadastroTratamentos.this); // Pass it an Activity or Context
            etNomeTratamento.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNomeTratamento.setPaddings(0,padding_5dp,0,0);
            etNomeTratamento.setId(i);
            etNomeTratamento.setOnFocusChangeListener(validar);
            etNomeTratamento.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNomeTratamento.setHint(getString(R.string.hint_nomeTratamento)+" "+(i+1));
            etNomeTratamento.setFloatingLabelText(getString(R.string.hint_nomeTratamento) + " " + (i + 1));
            etNomeTratamento.setFloatingLabelAnimating(true);
            if (tratamentosCadastrados.size() != 0) {
                etNomeTratamento.setText(tratamentosCadastrados.get(i).getNome_Tratamento());
            }
            layoutInterno.addView(etNomeTratamento);

            //tipo tratamento
            MaterialSpinner spTipoTratamento = new MaterialSpinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesSpinnerTipoTratamento);
            spTipoTratamento.setAdapter(spinnerArrayAdapter);
            spTipoTratamento.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            spTipoTratamento.setId((i + formulario.getQuantidadeTratamentos_Formulario()));
            spTipoTratamento.setOnFocusChangeListener(validar);
            if (tratamentosCadastrados.size() != 0) {
                spTipoTratamento.setSelection(tratamentosCadastrados.get(i).getTipo_Tratamento());
            }
            layoutInterno.addView(spTipoTratamento);
            myLayout.addView(layoutInterno);

        }



        Button btnSalvar_Tratamentos = (Button) findViewById(R.id.btn_salvar_Tratamentos);


        btnSalvar_Tratamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Tratamento> tratamentos = new ArrayList<>(formulario.getQuantidadeTratamentos_Formulario());
                for (int i = 0; i < formulario.getQuantidadeTratamentos_Formulario(); i++) {
                    Tratamento tratamento = new Tratamento();
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    if (met.validateWith(naoNulo)) {
                        tratamento.setNome_Tratamento(met.getText().toString());
                    } else {
                        met.requestFocus();
                        Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .setAction("Action", null).show();
                        return;
                }
                    MaterialSpinner sp = (MaterialSpinner) findViewById((i + formulario.getQuantidadeTratamentos_Formulario()));
                    if (sp.getSelectedItemPosition() == 0) {
                        sp.setError("Error");
                        sp.requestFocus();
                        Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .setAction("Action", null).show();
                        return;
                    } else {
                        tratamento.setTipo_Tratamento(sp.getSelectedItemPosition());
                        sp.setError(null);
                }
                    tratamento.setIdFormulario_Tratamento(id_Formulario);
                    tratamentos.add(i, tratamento);
            }
                if (tratamentos.size() == formulario.getQuantidadeTratamentos_Formulario()) {
                    Intent cadastroVariaveis = new Intent(CadastroTratamentos.this, CadastroVariaveis.class);

                    if (tratamentosCadastrados.size() == 0) {
                        for (Tratamento trat : tratamentos) {
                            dbauxiliar.insertTratamento(trat);
                        }
                        cadastroVariaveis.putExtra("id_Formulario", id_Formulario);
                        finish();
                        startActivity(cadastroVariaveis);
                    } else {
                        int i = 0;
                        for (Tratamento trat : tratamentos) {
                            trat.setId_Tratamento(tratamentosCadastrados.get(i).getId_Tratamento());
                            dbauxiliar.updateTratamento(trat);
                            i++;
                        }
                        cadastroVariaveis.putExtra("id_Formulario", id_Formulario);
                        finish();
                        startActivity(cadastroVariaveis);
                    }
                }
            }
        });

    }

}


