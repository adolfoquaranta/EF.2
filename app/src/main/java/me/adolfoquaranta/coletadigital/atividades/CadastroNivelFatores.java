package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.ArrayList;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.modelos.Fator;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.NivelFator;


public class CadastroNivelFatores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nivelfatores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent cadastroNivelFatores = getIntent();
        final Long id_Formulario = cadastroNivelFatores.getLongExtra("id_Formulario", 0);
        final Integer fatorAtual = cadastroNivelFatores.getIntExtra("fatorAtual", 0);
        final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_nomeNivelFator), "^(?!\\s*$).+");

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());
        final Formulario formulario = dbauxiliar.lerFormulario(id_Formulario);
        final ArrayList<Fator> fatores = dbauxiliar.lerTodosFatores(id_Formulario);
        final Fator fator = dbauxiliar.lerFator(fatores.get(fatorAtual).getId_Fator());
        final ArrayList<NivelFator> niveisCadastrados = dbauxiliar.lerTodosNiveisFator(fator.getId_Fator());

        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + fator.getNome_Fator());


        //Log.d("formulario", formulario.toString());

        //Log.d("fatores", fatores.toString());

        //Log.d("fator", fator.toString());

        //Log.d("nivelFator", niveisCadastrados.toString());


        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_nivelFatores);
        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);


        for (int i = 0; i < fator.getQuantidadeNiveis_Fator(); i++) {
            LinearLayout layoutInterno = new LinearLayout(CadastroNivelFatores.this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(1);

            //nome fator
            MaterialEditText etNomeNivelFator = new MaterialEditText(CadastroNivelFatores.this); // Pass it an Activity or Context
            etNomeNivelFator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNomeNivelFator.setPaddings(0, padding_5dp, 0, 0);
            etNomeNivelFator.setId(i);
            etNomeNivelFator.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNomeNivelFator.setHint(getString(R.string.hint_nomeNivelFator) + " " + (i + 1));
            etNomeNivelFator.setFloatingLabelText(getString(R.string.hint_nomeNivelFator) + " " + (i + 1));
            etNomeNivelFator.setFloatingLabelAnimating(true);
            etNomeNivelFator.addValidator(naoNulo);
            etNomeNivelFator.setValidateOnFocusLost(true);
            if (niveisCadastrados.size() != 0) {
                etNomeNivelFator.setText(niveisCadastrados.get(i).getNome_NivelFator());
            }
            layoutInterno.addView(etNomeNivelFator);


            myLayout.addView(layoutInterno);

        }


        Button btnSalvar_NiveisFator = (Button) findViewById(R.id.btn_salvar_niveisFator);


        btnSalvar_NiveisFator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NivelFator> niveisFator = new ArrayList<>(fator.getQuantidadeNiveis_Fator());
                for (int i = 0; i < fator.getQuantidadeNiveis_Fator(); i++) {
                    NivelFator nivelFator = new NivelFator();
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    if (met.validateWith(naoNulo)) {
                        fator.setNome_Fator(met.getText().toString());
                    } else {
                        met.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //        .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    }
                    nivelFator.setIdFator_NivelFator(fator.getId_Fator());
                    niveisFator.add(i, nivelFator);
                }
                if (niveisFator.size() == fator.getQuantidadeNiveis_Fator()) {
                    Intent cadastroVariaveis = new Intent(CadastroNivelFatores.this, CadastroVariaveis.class);

                    if (niveisCadastrados.size() == 0) {
                        for (NivelFator nivFat : niveisFator) {
                            dbauxiliar.insertNivelFator(nivFat);
                        }
                        if (fatores.size() == fatorAtual + 1) {
                            cadastroVariaveis.putExtra("id_Formulario", id_Formulario);
                            finish();
                            startActivity(cadastroVariaveis);
                        } else {
                            Intent recarregar = new Intent(CadastroNivelFatores.this, CadastroNivelFatores.class);
                            recarregar.putExtra("id_Formulario", id_Formulario);
                            recarregar.putExtra("fatorAtual", fatorAtual + 1);
                            finish();
                            startActivity(recarregar);
                        }

                    } else {
                        int i = 0;
                        for (NivelFator nivFat : niveisFator) {
                            nivFat.setId_NivelFator(niveisCadastrados.get(i).getId_NivelFator());
                            dbauxiliar.updateNiveisFator(nivFat);
                            i++;
                        }
                        if (fatores.size() == fatorAtual + 1) {
                            cadastroVariaveis.putExtra("id_Formulario", id_Formulario);
                            finish();
                            startActivity(cadastroVariaveis);
                        } else {
                            Intent recarregar = new Intent(CadastroNivelFatores.this, CadastroNivelFatores.class);
                            recarregar.putExtra("id_Formulario", id_Formulario);
                            recarregar.putExtra("fatorAtual", fatorAtual + 1);
                            finish();
                            startActivity(recarregar);
                        }
                    }
                }
            }
        });

    }

}


