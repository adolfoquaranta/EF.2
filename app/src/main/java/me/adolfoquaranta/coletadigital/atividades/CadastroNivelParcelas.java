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
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.NivelParcela;
import me.adolfoquaranta.coletadigital.modelos.Parcela;


public class CadastroNivelParcelas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nivelparcelas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent cadastroNivelParcelas = getIntent();
        final Long id_Formulario = cadastroNivelParcelas.getLongExtra("id_Formulario", 0);
        final Integer parcelaAtual = cadastroNivelParcelas.getIntExtra("parcelaAtual", 0);
        final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_nomeNivelParcela), "^(?!\\s*$).+");

        final DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());
        final Formulario formulario = dbauxiliar.lerFormulario(id_Formulario);
        final ArrayList<Parcela> parcelas = dbauxiliar.lerTodasParcelas(id_Formulario);
        final Parcela parcela = dbauxiliar.lerParcela(parcelas.get(parcelaAtual).getId_Parcela());
        final ArrayList<NivelParcela> niveisCadastrados = dbauxiliar.lerTodosNiveisParcela(parcela.getId_Parcela());
        String[] grauParcela = {"Parcela", "Sub Parcela", "Sub Sub Parcela"};

        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + grauParcela[parcelaAtual] + " " + parcela.getNome_Parcela());


        //Log.d("formulario", formulario.toString());

        //Log.d("parcelas", parcelas.toString());

        //Log.d("parcela", parcela.toString());

        //Log.d("nivelParcela", niveisCadastrados.toString());


        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_nivelParcelas);
        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);


        for (int i = 0; i < parcela.getQuantidadeNiveis_Parcela(); i++) {
            LinearLayout layoutInterno = new LinearLayout(CadastroNivelParcelas.this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(1);

            //nome parcela
            MaterialEditText etNomeNivelParcela = new MaterialEditText(CadastroNivelParcelas.this); // Pass it an Activity or Context
            etNomeNivelParcela.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNomeNivelParcela.setPaddings(0, padding_5dp, 0, 0);
            etNomeNivelParcela.setId(i);
            etNomeNivelParcela.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNomeNivelParcela.setHint(getString(R.string.hint_nomeNivelParcela) + " " + (i + 1));
            etNomeNivelParcela.setFloatingLabelText(getString(R.string.hint_nomeNivelParcela) + " " + (i + 1));
            etNomeNivelParcela.setFloatingLabelAnimating(true);
            etNomeNivelParcela.addValidator(naoNulo);
            etNomeNivelParcela.setValidateOnFocusLost(true);
            if (niveisCadastrados.size() != 0) {
                etNomeNivelParcela.setText(niveisCadastrados.get(i).getNome_NivelParcela());
            }
            layoutInterno.addView(etNomeNivelParcela);


            myLayout.addView(layoutInterno);

        }


        Button btnSalvar_NiveisParcela = (Button) findViewById(R.id.btn_salvar_niveisParcela);


        btnSalvar_NiveisParcela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NivelParcela> niveisParcela = new ArrayList<>(parcela.getQuantidadeNiveis_Parcela());
                for (int i = 0; i < parcela.getQuantidadeNiveis_Parcela(); i++) {
                    NivelParcela nivelParcela = new NivelParcela();
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    if (met.validateWith(naoNulo)) {
                        parcela.setNome_Parcela(met.getText().toString());
                    } else {
                        met.requestFocus();
                        //Snackbar.make(v, getString(R.string.err_msg_preenchaTodosCampos), Snackbar.LENGTH_LONG)
                        //        .setActionTextColor(Color.RED)
                        //        .setAction("Action", null).show();
                        return;
                    }
                    nivelParcela.setIdParcela_NivelParcela(parcela.getId_Parcela());
                    nivelParcela.setNumero_NivelParcela(i);
                    nivelParcela.setNome_NivelParcela(met.getText().toString());
                    niveisParcela.add(i, nivelParcela);
                }
                if (niveisParcela.size() == parcela.getQuantidadeNiveis_Parcela()) {
                    Intent cadastroVariaveis = new Intent(CadastroNivelParcelas.this, CadastroVariaveis.class);

                    if (niveisCadastrados.size() == 0) {
                        for (NivelParcela nivPar : niveisParcela) {
                            dbauxiliar.insertNivelParcela(nivPar);
                        }
                        if (parcelas.size() == parcelaAtual + 1) {
                            cadastroVariaveis.putExtra("id_Formulario", id_Formulario);
                            finish();
                            startActivity(cadastroVariaveis);
                        } else {
                            Intent recarregar = new Intent(CadastroNivelParcelas.this, CadastroNivelParcelas.class);
                            recarregar.putExtra("id_Formulario", id_Formulario);
                            recarregar.putExtra("parcelaAtual", parcelaAtual + 1);
                            finish();
                            startActivity(recarregar);
                        }

                    } else {
                        int i = 0;
                        for (NivelParcela nivPar : niveisParcela) {
                            nivPar.setId_NivelParcela(niveisCadastrados.get(i).getId_NivelParcela());
                            dbauxiliar.updateNiveisParcela(nivPar);
                            i++;
                        }
                        if (parcelas.size() == parcelaAtual + 1) {
                            cadastroVariaveis.putExtra("id_Formulario", id_Formulario);
                            finish();
                            startActivity(cadastroVariaveis);
                        } else {
                            Intent recarregar = new Intent(CadastroNivelParcelas.this, CadastroNivelParcelas.class);
                            recarregar.putExtra("id_Formulario", id_Formulario);
                            recarregar.putExtra("parcelaAtual", parcelaAtual + 1);
                            finish();
                            startActivity(recarregar);
                        }
                    }
                }
            }
        });

    }

}


