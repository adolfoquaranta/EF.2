package me.adolfoquaranta.ef2.atividades;

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
import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.Formulario;
import me.adolfoquaranta.ef2.modelos.Modelo;
import me.adolfoquaranta.ef2.modelos.Variavel;

public class CadastroVariaveis extends AppCompatActivity {

    private Modelo modelo;
    private Formulario formulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_variaveis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Intent cadastroVariaveis = getIntent();

        final Long id_Modelo = cadastroVariaveis.getLongExtra("id_Modelo", 0);
        final Long idFormulario_Modelo = cadastroVariaveis.getLongExtra("idFormulario_Modelo", 0);

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());

        modelo = dbauxiliar.lerModelo(id_Modelo);

        formulario = dbauxiliar.lerFormulario(idFormulario_Modelo);

        final ArrayList<Variavel> variaveisPreenchidas = dbauxiliar.lerTodasVariaveis(idFormulario_Modelo, id_Modelo);

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


        for (int i = 0; i < modelo.getQuantidadeVariaveis_Modelo(); i++) {
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
            if (variaveisPreenchidas.size() != 0) {
                etNomeVariavel.setText(variaveisPreenchidas.get(i).getNome_Variavel());
            }
            layoutInterno.addView(etNomeVariavel);

            //tipo variavel
            MaterialSpinner spTipoVariavel = new MaterialSpinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoesSpinnerTipoVariaveis);
            spTipoVariavel.setAdapter(spinnerArrayAdapter);
            spTipoVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            spTipoVariavel.setId((i + modelo.getQuantidadeVariaveis_Modelo()));
            spTipoVariavel.setOnFocusChangeListener(validar);
            if (variaveisPreenchidas.size() != 0) {
                spTipoVariavel.setSelection(variaveisPreenchidas.get(i).getTipo_Variavel());
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
                ArrayList<Variavel> variaveis = new ArrayList<>(modelo.getQuantidadeVariaveis_Modelo());
                for (int i = 0; i < modelo.getQuantidadeVariaveis_Modelo(); i++) {
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
                    MaterialSpinner sp = (MaterialSpinner) findViewById((i + modelo.getQuantidadeVariaveis_Modelo()));
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
                    variavel.setIdModelo_Variavel(id_Modelo);
                    variaveis.add(i, variavel);
                }
                if (variaveis.size() == modelo.getQuantidadeVariaveis_Modelo()) {
                    Intent listarFormularios = new Intent(CadastroVariaveis.this, ListarFormularios.class);

                    if (variaveisPreenchidas.size() == 0) {
                        for (Variavel var : variaveis) {
                            dbauxiliar.insertVariavel(var);
                        }
                    } else {
                        int i = 0;
                        for (Variavel var : variaveis) {
                            var.setId_Variavel(variaveisPreenchidas.get(i).getId_Variavel());
                            dbauxiliar.updateVariavel(var);
                            i++;
                        }
                    }

                    listarFormularios.putExtra("tipo_Formulario", formulario.getTipo_Form());
                    listarFormularios.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(listarFormularios);
                }
            }
        });
    }

}
