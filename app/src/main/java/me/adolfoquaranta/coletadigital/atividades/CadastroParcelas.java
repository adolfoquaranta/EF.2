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
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.Parcela;


public class CadastroParcelas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_parcelas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent cadastroParcelas = getIntent();
        final Long id_Formulario = cadastroParcelas.getLongExtra("id_Formulario", 0);
        final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_nomeParcela), "^(?!\\s*$).+");
        final RegexpValidator numeroInteiroNaoNulo = new RegexpValidator(getString(R.string.err_msg_deveSerNumInteiroNaoNulo), "^0*[1-9]\\d*$");

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());
        final Formulario formulario = dbauxiliar.lerFormulario(id_Formulario);
        final ArrayList<Parcela> parcelasCadastrados = dbauxiliar.lerTodasParcelas(id_Formulario);
        String[] grauParcela = {"Parcela", "Sub Parcela", "Sub Sub Parcela"};

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

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_Parcelas);
        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);
        String[] opcoesSpinnerTipoParcela = getResources().getStringArray(R.array.spinnerArray_tipoParcela);


        for (int i = 0; i < formulario.getQuantidadeParcelas_Formulario(); i++) {
            LinearLayout layoutInterno = new LinearLayout(CadastroParcelas.this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(6);

            //nome parcela
            MaterialEditText etNomeParcela = new MaterialEditText(CadastroParcelas.this); // Pass it an Activity or Context
            etNomeParcela.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNomeParcela.setPaddings(0, padding_5dp, 0, 0);
            etNomeParcela.setId(i);
            etNomeParcela.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNomeParcela.setHint(getString(R.string.hint_nomeParcela) + " " + grauParcela[i]);
            etNomeParcela.setFloatingLabelText(getString(R.string.hint_nomeParcela) + " " + grauParcela[i]);
            etNomeParcela.setFloatingLabelAnimating(true);
            etNomeParcela.addValidator(naoNulo);
            etNomeParcela.setValidateOnFocusLost(true);
            if (parcelasCadastrados.size() != 0) {
                etNomeParcela.setText(parcelasCadastrados.get(i).getNome_Parcela());
            }
            layoutInterno.addView(etNomeParcela);

            //niveis parcela
            MaterialEditText etNiveisParcela = new MaterialEditText(CadastroParcelas.this); // Pass it an Activity or Context
            etNiveisParcela.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNiveisParcela.setPaddings(0, padding_5dp, 0, 0);
            etNiveisParcela.setId(i + (formulario.getQuantidadeParcelas_Formulario()));
            etNiveisParcela.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNiveisParcela.setHint(getString(R.string.hint_niveisParcela));
            etNiveisParcela.setFloatingLabelText(getString(R.string.hint_niveisParcela));
            etNiveisParcela.setInputType(InputType.TYPE_CLASS_NUMBER);
            etNiveisParcela.setFloatingLabelAnimating(true);
            etNiveisParcela.addValidator(numeroInteiroNaoNulo);
            etNiveisParcela.setValidateOnFocusLost(true);
            if (parcelasCadastrados.size() != 0) {
                etNiveisParcela.setText(parcelasCadastrados.get(i).getNome_Parcela());
            }
            layoutInterno.addView(etNiveisParcela);

            //tipo parcela
            MaterialSpinner spTipoParcela = new MaterialSpinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesSpinnerTipoParcela);
            spTipoParcela.setAdapter(spinnerArrayAdapter);
            spTipoParcela.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            spTipoParcela.setId(i + (formulario.getQuantidadeParcelas_Formulario() * 2));
            spTipoParcela.setFloatingLabelText("Tipo");
            spTipoParcela.setOnFocusChangeListener(validar);
            if (parcelasCadastrados.size() != 0) {
                spTipoParcela.setSelection(parcelasCadastrados.get(i).getTipo_Parcela());
            }
            layoutInterno.addView(spTipoParcela);


            myLayout.addView(layoutInterno);

        }


        Button btnSalvar_Parcelas = (Button) findViewById(R.id.btn_salvar_Parcelas);


        btnSalvar_Parcelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Parcela> parcelas = new ArrayList<>(formulario.getQuantidadeParcelas_Formulario());
                for (int i = 0; i < formulario.getQuantidadeParcelas_Formulario(); i++) {
                    Parcela parcela = new Parcela();
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    if (met.validateWith(naoNulo)) {
                        parcela.setNome_Parcela(met.getText().toString());
                    } else {
                        met.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //       .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    }
                    MaterialEditText metB = (MaterialEditText) findViewById(i + formulario.getQuantidadeParcelas_Formulario());
                    if (metB.validateWith(numeroInteiroNaoNulo)) {
                        parcela.setQuantidadeNiveis_Parcela(Integer.valueOf(metB.getText().toString()));
                    } else {
                        metB.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //       .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    }
                    MaterialSpinner sp = (MaterialSpinner) findViewById(i + (formulario.getQuantidadeParcelas_Formulario() * 2));
                    if (sp.getSelectedItemPosition() == 0) {
                        sp.setError("Error");
                        sp.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //        .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    } else {
                        parcela.setTipo_Parcela(sp.getSelectedItemPosition());
                        sp.setError(null);
                    }
                    parcela.setIdFormulario_Parcela(id_Formulario);
                    parcelas.add(i, parcela);
                }
                if (parcelas.size() == formulario.getQuantidadeParcelas_Formulario()) {
                    Intent cadastroNivelParcelas = new Intent(CadastroParcelas.this, CadastroNivelParcelas.class);

                    if (parcelasCadastrados.size() == 0) {
                        for (Parcela par : parcelas) {
                            dbauxiliar.insertParcela(par);
                        }
                        cadastroNivelParcelas.putExtra("id_Formulario", id_Formulario);
                        finish();
                        startActivity(cadastroNivelParcelas);
                    } else {
                        int i = 0;
                        for (Parcela par : parcelas) {
                            par.setId_Parcela(parcelasCadastrados.get(i).getId_Parcela());
                            dbauxiliar.updateParcela(par);
                            i++;
                        }
                        cadastroNivelParcelas.putExtra("id_Formulario", id_Formulario);
                        finish();
                        startActivity(cadastroNivelParcelas);
                    }
                }
            }
        });

    }

}


