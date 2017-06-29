package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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
import me.adolfoquaranta.coletadigital.modelos.Fator;
import me.adolfoquaranta.coletadigital.modelos.Formulario;


public class CadastroFatores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fatores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent cadastroFatores = getIntent();
        final Long id_Formulario = cadastroFatores.getLongExtra("id_Formulario", 0);
        final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_nomeFator), "^(?!\\s*$).+");
        final RegexpValidator numeroInteiroNaoNulo = new RegexpValidator(getString(R.string.err_msg_deveSerNumInteiroNaoNulo), "^0*[1-9]\\d*$");

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());
        final Formulario formulario = dbauxiliar.lerFormulario(id_Formulario);
        final ArrayList<Fator> fatoresCadastrados = dbauxiliar.lerTodosFatores(id_Formulario);

        final View.OnFocusChangeListener validar = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v instanceof MaterialSpinner) {
                    MaterialSpinner spinner = (MaterialSpinner) v;
                    if (spinner.getSelectedItemPosition() == 0) {
                        spinner.setError("Error");
                    } else {
                        spinner.setError(null);
                    }
                }
            }
        };

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_Fatores);
        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);
        String[] opcoesSpinnerTipoFator = getResources().getStringArray(R.array.spinnerArray_tipoFator);


        for (int i = 0; i < formulario.getQuantidadeFatores_Formulario(); i++) {
            LinearLayout layoutInterno = new LinearLayout(CadastroFatores.this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(6);

            //nome fator
            MaterialEditText etNomeFator = new MaterialEditText(CadastroFatores.this); // Pass it an Activity or Context
            etNomeFator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNomeFator.setPaddings(0, padding_5dp, 0, 0);
            etNomeFator.setId(i);
            etNomeFator.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNomeFator.setHint(getString(R.string.hint_nomeFator) + " " + (i + 1));
            etNomeFator.setFloatingLabelText(getString(R.string.hint_nomeFator) + " " + (i + 1));
            etNomeFator.setFloatingLabelAnimating(true);
            etNomeFator.addValidator(naoNulo);
            etNomeFator.setValidateOnFocusLost(true);
            if (fatoresCadastrados.size() != 0) {
                etNomeFator.setText(fatoresCadastrados.get(i).getNome_Fator());
            }
            layoutInterno.addView(etNomeFator);

            //niveis fator
            MaterialEditText etNiveisFator = new MaterialEditText(CadastroFatores.this); // Pass it an Activity or Context
            etNiveisFator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNiveisFator.setPaddings(0, padding_5dp, 0, 0);
            etNiveisFator.setId(i + (formulario.getQuantidadeFatores_Formulario()));
            etNiveisFator.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNiveisFator.setHint(getString(R.string.hint_niveisFator));
            etNiveisFator.setFloatingLabelText(getString(R.string.hint_niveisFator));
            etNiveisFator.setInputType(InputType.TYPE_CLASS_NUMBER);
            etNiveisFator.setFloatingLabelAnimating(true);
            etNiveisFator.addValidator(numeroInteiroNaoNulo);
            etNiveisFator.setValidateOnFocusLost(true);
            if (fatoresCadastrados.size() != 0) {
                etNiveisFator.setText(fatoresCadastrados.get(i).getNome_Fator());
            }
            layoutInterno.addView(etNiveisFator);

            //tipo fator
            MaterialSpinner spTipoFator = new MaterialSpinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesSpinnerTipoFator);
            spTipoFator.setAdapter(spinnerArrayAdapter);
            spTipoFator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            spTipoFator.setId(i + (formulario.getQuantidadeFatores_Formulario() * 2));
            spTipoFator.setFloatingLabelText("Tipo");
            spTipoFator.setOnFocusChangeListener(validar);
            if (fatoresCadastrados.size() != 0) {
                spTipoFator.setSelection(fatoresCadastrados.get(i).getTipo_Fator());
            }
            layoutInterno.addView(spTipoFator);


            myLayout.addView(layoutInterno);

        }


        Button btnSalvar_Fatores = (Button) findViewById(R.id.btn_salvar_Fatores);


        btnSalvar_Fatores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Fator> fatores = new ArrayList<>(formulario.getQuantidadeFatores_Formulario());
                for (int i = 0; i < formulario.getQuantidadeFatores_Formulario(); i++) {
                    Fator fator = new Fator();
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    if (met.validateWith(naoNulo)) {
                        fator.setNome_Fator(met.getText().toString());
                    } else {
                        met.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //       .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    }
                    MaterialEditText metB = (MaterialEditText) findViewById(i + formulario.getQuantidadeFatores_Formulario());
                    if (metB.validateWith(numeroInteiroNaoNulo)) {
                        fator.setQuantidadeNiveis_Fator(Integer.valueOf(metB.getText().toString()));
                    } else {
                        metB.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //       .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    }
                    MaterialSpinner sp = (MaterialSpinner) findViewById(i + (formulario.getQuantidadeFatores_Formulario() * 2));
                    if (sp.getSelectedItemPosition() == 0) {
                        sp.setError("Error");
                        sp.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //        .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    } else {
                        fator.setTipo_Fator(sp.getSelectedItemPosition());
                        sp.setError(null);
                    }
                    fator.setIdFormulario_Fator(id_Formulario);
                    fatores.add(i, fator);
                }
                if (fatores.size() == formulario.getQuantidadeFatores_Formulario()) {
                    Intent cadastroNivelFatores = new Intent(CadastroFatores.this, CadastroNivelFatores.class);

                    if (fatoresCadastrados.size() == 0) {
                        for (Fator fat : fatores) {
                            dbauxiliar.insertFator(fat);
                        }
                        cadastroNivelFatores.putExtra("id_Formulario", id_Formulario);
                        finish();
                        startActivity(cadastroNivelFatores);
                    } else {
                        int i = 0;
                        for (Fator fat : fatores) {
                            fat.setId_Fator(fatoresCadastrados.get(i).getId_Fator());
                            dbauxiliar.updateFator(fat);
                            i++;
                        }
                        cadastroNivelFatores.putExtra("id_Formulario", id_Formulario);
                        finish();
                        startActivity(cadastroNivelFatores);
                    }
                }
            }
        });

    }

}


