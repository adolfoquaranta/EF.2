package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Variavel;

public class CadastroVariaveis extends AppCompatActivity {

    private DIC dic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_variaveis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final Intent cadastroVariaveis = getIntent();
        final Long idFormulario_DIC = cadastroVariaveis.getLongExtra("idFormulario_DIC", 0);

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());

        dic = dbauxiliar.lerDICdoFormulario(idFormulario_DIC);

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


        for (int i = 0; i < dic.getQuantidadeVariaveis_DIC(); i++) {
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
            layoutInterno.addView(etNomeVariavel);

            //tipo variavel
            MaterialSpinner spTipoVariavel = new MaterialSpinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesSpinnerTipoVariaveis);
            spTipoVariavel.setAdapter(spinnerArrayAdapter);
            spTipoVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            spTipoVariavel.setId((i + dic.getQuantidadeVariaveis_DIC()));
            spTipoVariavel.setOnFocusChangeListener(validar);
            layoutInterno.addView(spTipoVariavel);
            myLayout.addView(layoutInterno);

        }


        Button btnSalvar_Variaveis = (Button) findViewById(R.id.btn_salvar_Variaveis);


        btnSalvar_Variaveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Variavel> tratamentos = new ArrayList<>(dic.getQuantidadeVariaveis_DIC());
                for (int i = 0; i < dic.getQuantidadeVariaveis_DIC(); i++) {
                    Variavel tratamento = new Variavel();
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    if (met.validateWith(naoNulo)) {
                        tratamento.setNome_Variavel(met.getText().toString());
                    } else {
                        met.requestFocus();
                        Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .setAction("Action", null).show();
                        return;
                    }
                    MaterialSpinner sp = (MaterialSpinner) findViewById((i + dic.getQuantidadeVariaveis_DIC()));
                    if (sp.getSelectedItemPosition() == 0) {
                        sp.setError("Error");
                        sp.requestFocus();
                        Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .setAction("Action", null).show();
                        return;
                    } else {
                        tratamento.setTipo_Variavel(sp.getSelectedItemPosition());
                        sp.setError(null);
                    }
                    tratamento.setIdForm_Variavel(idFormulario_DIC);
                    tratamentos.add(i, tratamento);
                }
                if (tratamentos.size() == dic.getQuantidadeVariaveis_DIC()) {
                    for (Variavel var : tratamentos) {
                        dbauxiliar.insertVariavel(var);
                        Intent inicio = new Intent(CadastroVariaveis.this, Inicio.class);
                        startActivity(inicio);
                    }
                }
            }
        });
    }

}
