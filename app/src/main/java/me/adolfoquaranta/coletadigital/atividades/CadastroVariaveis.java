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
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class CadastroVariaveis extends AppCompatActivity {

    private Formulario formulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_variaveis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Intent cadastroVariaveis = getIntent();

        final Long id_Formulario = cadastroVariaveis.getLongExtra("id_Formulario", 0);

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());

        formulario = dbauxiliar.lerFormulario(id_Formulario);

        final ArrayList<Variavel> variaveisCadastradas = dbauxiliar.lerTodasVariaveis(id_Formulario);

        final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_nomeVariavel), "^(?!\\s*$).+");

        final View.OnFocusChangeListener validar = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v instanceof MaterialEditText) {
                    MaterialEditText editText = (MaterialEditText) v;
                    editText.validateWith(naoNulo);
                }
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

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_Variaveis);
        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);
        String[] opcoesSpinnerTipoVariaveis = getResources().getStringArray(R.array.spinnerArray_tipoVariavel);


        for (int i = 0; i < formulario.getQuantidadeVariaveis_Formulario(); i++) {
            LinearLayout layoutInterno = new LinearLayout(CadastroVariaveis.this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(3);

            //nome variavel
            MaterialEditText etNomeVariavel = new MaterialEditText(CadastroVariaveis.this); // Pass it an Activity or Context
            etNomeVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNomeVariavel.setPaddings(0, padding_5dp, 0, 0);
            etNomeVariavel.setId(i);
            etNomeVariavel.setOnFocusChangeListener(validar);
            etNomeVariavel.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNomeVariavel.setHint(getString(R.string.hint_nomeVariavel) + " " + (i + 1));
            etNomeVariavel.setFloatingLabelText(getString(R.string.hint_nomeVariavel) + " " + (i + 1));
            etNomeVariavel.setFloatingLabelAnimating(true);
            if (variaveisCadastradas.size() != 0) {
                etNomeVariavel.setText(variaveisCadastradas.get(i).getNome_Variavel());
            }
            layoutInterno.addView(etNomeVariavel);

            //tipo variavel
            MaterialSpinner spTipoVariavel = new MaterialSpinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoesSpinnerTipoVariaveis);
            spTipoVariavel.setAdapter(spinnerArrayAdapter);
            spTipoVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            spTipoVariavel.setId((i + formulario.getQuantidadeVariaveis_Formulario()));
            spTipoVariavel.setOnFocusChangeListener(validar);
            if (variaveisCadastradas.size() != 0) {
                spTipoVariavel.setSelection(variaveisCadastradas.get(i).getTipo_Variavel());
            }
            layoutInterno.addView(spTipoVariavel);
            assert myLayout != null;
            myLayout.addView(layoutInterno);

        }


        Button btnSalvar_Variaveis = (Button) findViewById(R.id.btn_salvar_Variaveis);


        assert btnSalvar_Variaveis != null;
        btnSalvar_Variaveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Variavel> variaveis = new ArrayList<>(formulario.getQuantidadeVariaveis_Formulario());
                for (int i = 0; i < formulario.getQuantidadeVariaveis_Formulario(); i++) {
                    Variavel variavel = new Variavel();
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    assert met != null;
                    if (met.validateWith(naoNulo)) {
                        variavel.setNome_Variavel(met.getText().toString());
                    } else {
                        met.requestFocus();
                        Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .setAction("Action", null).show();
                        return;
                    }
                    MaterialSpinner sp = (MaterialSpinner) findViewById((i + formulario.getQuantidadeVariaveis_Formulario()));
                    assert sp != null;
                    if (sp.getSelectedItemPosition() == 0) {
                        sp.setError("Error");
                        sp.requestFocus();
                        Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .setAction("Action", null).show();
                        return;
                    } else {
                        variavel.setTipo_Variavel(sp.getSelectedItemPosition());
                        sp.setError(null);
                    }
                    variavel.setIdFormulario_Variavel(id_Formulario);
                    variaveis.add(i, variavel);
                }
                if (variaveis.size() == formulario.getQuantidadeVariaveis_Formulario()) {
                    Intent listarFormularios = new Intent(CadastroVariaveis.this, ListarFormularios.class);

                    if (variaveisCadastradas.size() == 0) {
                        for (Variavel var : variaveis) {
                            dbauxiliar.insertVariavel(var);
                        }
                    } else {
                        int i = 0;
                        for (Variavel var : variaveis) {
                            var.setId_Variavel(variaveisCadastradas.get(i).getId_Variavel());
                            dbauxiliar.updateVariavel(var);
                            i++;
                        }
                    }

                    listarFormularios.putExtra("tipo_Formulario", formulario.getTipo_Formulario());
                    finish();
                    startActivity(listarFormularios);
                }
            }
        });
    }

}
