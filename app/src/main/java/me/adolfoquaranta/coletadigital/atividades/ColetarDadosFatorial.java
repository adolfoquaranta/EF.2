package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.modelos.Coleta;
import me.adolfoquaranta.coletadigital.modelos.Dado;
import me.adolfoquaranta.coletadigital.modelos.Fator;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.NivelFator;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class ColetarDadosFatorial extends AppCompatActivity {

    private DBAuxilar dbAuxilar;
    private List<Fator> fatores;
    private List<NivelFator> niveisFator;
    private List<Variavel> variaveis;
    private Integer fatorAtual, nivelFatorAtual, repeticaoAtual, replicacaoAtual, blocoAtual;
    private Long id_Coleta, id_Formulario;

    private Formulario formulario;
    private Coleta coleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar_dadosfatorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAuxilar = new DBAuxilar(getApplicationContext());

        final Intent coletarDadosFatorialIntent = getIntent();
        id_Formulario = coletarDadosFatorialIntent.getLongExtra("id_Formulario", 0);
        id_Coleta = coletarDadosFatorialIntent.getLongExtra("id_Coleta", 0);
        fatorAtual = coletarDadosFatorialIntent.getIntExtra("fatorAtual", 0);
        nivelFatorAtual = coletarDadosFatorialIntent.getIntExtra("nivelFatorAtual", 0);
        replicacaoAtual = coletarDadosFatorialIntent.getIntExtra("replicacaoAtual", 0);
        repeticaoAtual = coletarDadosFatorialIntent.getIntExtra("repeticaoAtual", 0);
        blocoAtual = coletarDadosFatorialIntent.getIntExtra("blocoAtual", 0);

        Log.d("fatorAtual", fatorAtual.toString());
        Log.d("nivelFatorAtual", nivelFatorAtual.toString());
        Log.d("repeticaoAtual", repeticaoAtual.toString());
        Log.d("replicacaoAtual", replicacaoAtual.toString());

        formulario = dbAuxilar.lerFormulario(id_Formulario);
        coleta = dbAuxilar.lerColeta(id_Coleta);
        fatores = dbAuxilar.lerTodosFatores(id_Formulario);
        niveisFator = dbAuxilar.lerTodosNiveisFator(fatores.get(fatorAtual).getId_Fator());
        variaveis = dbAuxilar.lerTodasVariaveis(id_Formulario);

        Log.d("coleta", coleta.toString());
        Log.d("fatores", fatores.toString());
        Log.d("niveisFator", niveisFator.toString());
        Log.d("variaveis", variaveis.toString());


        final RegexpValidator naoNulo = new RegexpValidator(getString(R.string.err_msg_valorVariavel), "^(?!\\s*$).+");

        View.OnFocusChangeListener validar = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v instanceof MaterialEditText && v.isEnabled() && v.hasFocus()) {
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

        InputFilter filterLetraOuDigito = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (source.charAt(i) == ',' || source.charAt(i) == '.') return ".";
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        InputFilter filterDigito = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        if (source.charAt(i) == ',' || source.charAt(i) == '.') return ".";
                        return "";
                    }
                }
                return null;
            }
        };
        InputFilter filterCaracter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_ColetarDadosFatorial);

        final TextView infoColetaAtual = (TextView) findViewById(R.id.tv_infoColetaAtualFatorial);


        String bloco = "Bloco " + (blocoAtual + 1), fator = fatores.get(fatorAtual).getNome_Fator(), nivelFator = niveisFator.get(nivelFatorAtual).getNome_NivelFator(), repeticao = "Repetição " + (repeticaoAtual + 1), replicacao = "Réplica " + (replicacaoAtual + 1), infoColeta = "";
        infoColeta = bloco + " | " + fator + " | " + nivelFator + " | " + repeticao + " | " + replicacao;
        infoColetaAtual.setText(infoColeta);


        for (int i = 0; i < variaveis.size(); i++) {
            final LinearLayout layoutInterno = new LinearLayout(this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(3);
            layoutInterno.setFocusable(true);

            final MaterialEditText etValorVariavel = new MaterialEditText(this); // Pass it an Activity or Context
            etValorVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etValorVariavel.setId(i);
            etValorVariavel.setOnFocusChangeListener(validar);
            etValorVariavel.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etValorVariavel.setHint(variaveis.get(i).getNome_Variavel());
            etValorVariavel.setFloatingLabelText(variaveis.get(i).getNome_Variavel());
            etValorVariavel.setFloatingLabelAnimating(true);
            if (variaveis.get(i).getTipo_Variavel() == 1) {
                etValorVariavel.setInputType(InputType.TYPE_CLASS_TEXT);
                etValorVariavel.setFilters(new InputFilter[]{filterLetraOuDigito});
            } else if (variaveis.get(i).getTipo_Variavel() == 2) {
                etValorVariavel.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                etValorVariavel.setFilters(new InputFilter[]{filterDigito});
            }

            try {
                etValorVariavel.setText(dbAuxilar.lerValorDadoFatorial(fatores.get(fatorAtual).getId_Fator(), niveisFator.get(nivelFatorAtual).getId_NivelFator(), blocoAtual, repeticaoAtual, replicacaoAtual, variaveis.get(i).getId_Variavel(), id_Coleta).getValor_Dado());
            } catch (Exception e) {
                Log.e(e.getLocalizedMessage(), e.getMessage());
            }

            layoutInterno.addView(etValorVariavel);

            final ToggleButton anularVariavel = new ToggleButton(getApplicationContext());
            anularVariavel.setText(R.string.btn_na);
            anularVariavel.setId(i + variaveis.size());
            anularVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            anularVariavel.setId(i + variaveis.size());
            anularVariavel.setTextOn(getString(R.string.btn_na));
            anularVariavel.setTextOff(getString(R.string.btn_na));
            layoutInterno.addView(anularVariavel);

            myLayout.addView(layoutInterno);

            final int finalI = i;
            anularVariavel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialEditText etAtual = (MaterialEditText) layoutInterno.findViewById(v.getId() - variaveis.size());
                    if (etAtual.isEnabled()) {
                        etAtual.setText(null);
                        etAtual.setEnabled(false);
                    } else {
                        etAtual.setHint(variaveis.get(finalI).getNome_Variavel());
                        etAtual.setEnabled(true);
                    }
                }
            });

            try {
                if (dbAuxilar.lerValorDadoFatorial(fatores.get(fatorAtual).getId_Fator(), niveisFator.get(nivelFatorAtual).getId_NivelFator(), blocoAtual, repeticaoAtual, replicacaoAtual, variaveis.get(i).getId_Variavel(), id_Coleta).getValor_Dado().equals(" ")) {
                    anularVariavel.toggle();
                    etValorVariavel.setEnabled(false);
                }
            } catch (Exception e) {
                Log.e(e.getLocalizedMessage(), e.getMessage());
            }

        }

        FloatingActionButton proximoDadoColetarFatorial = (FloatingActionButton) findViewById(R.id.fab_proximoDadoColetarFatorial);
        proximoDadoColetarFatorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recarregar = new Intent(ColetarDadosFatorial.this, ColetarDadosFatorial.class);
                recarregar.putExtra("id_Formulario", id_Formulario);
                recarregar.putExtra("id_Coleta", id_Coleta);


                ArrayList<Dado> dados = new ArrayList<>(variaveis.size());
                Integer id = 0;
                while (findViewById(id) instanceof MaterialEditText) {
                    Dado dado = new Dado();
                    dado.setIdColeta_Dado(id_Coleta);
                    MaterialEditText etVariavel = (MaterialEditText) findViewById(id);
                    if (etVariavel.isEnabled() && etVariavel.getText().toString().equals("")) {
                        Snackbar.make(view, getString(R.string.info_PreechaOuAnule), Snackbar.LENGTH_LONG).show();
                        return;
                    } else if (!etVariavel.isEnabled()) {
                        dado.setValor_Dado(" ");
                    } else if (etVariavel.isEnabled()) {
                        dado.setValor_Dado(etVariavel.getText().toString());
                    }
                    dados.add(dado);
                    id++;
                }

                Log.d("dadosSize", String.valueOf(dados.size()));
                Log.d("dados", dados.toString());

                if (replicacaoAtual + 1 == formulario.getQuantidadeReplicacoes_Formulario()) {
                    if (repeticaoAtual + 1 == formulario.getQuantidadeRepeticoes_Formulario()) {
                        if (fatorAtual + 1 == formulario.getQuantidadeFatores_Formulario()) {
                            if (formulario.getQuantidadeBlocos_Formulario() == -1) {
                                inserirDados(dados);
                                Intent listarColetas = new Intent(ColetarDadosFatorial.this, ListarColetas.class);
                                listarColetas.putExtra("id_Formulario", id_Formulario);
                                listarColetas.putExtra("tipo_Formulario", dbAuxilar.lerFormulario(id_Formulario).getTipo_Formulario());
                                coleta.setStatus_Coleta("ok");
                                dbAuxilar.updateStatusColeta(coleta);
                                finish();
                                startActivity(listarColetas);
                            } else {
                                if (blocoAtual + 1 == formulario.getQuantidadeBlocos_Formulario()) {
                                    inserirDados(dados);
                                    Intent listarColetas = new Intent(ColetarDadosFatorial.this, ListarColetas.class);
                                    listarColetas.putExtra("id_Formulario", id_Formulario);
                                    listarColetas.putExtra("tipo_Formulario", dbAuxilar.lerFormulario(id_Formulario).getTipo_Formulario());
                                    coleta.setStatus_Coleta("ok");
                                    dbAuxilar.updateStatusColeta(coleta);
                                    finish();
                                    startActivity(listarColetas);
                                } else if (blocoAtual + 1 < formulario.getQuantidadeBlocos_Formulario()) {
                                    inserirDados(dados);
                                    blocoAtual++;
                                    fatorAtual = 0;
                                    repeticaoAtual = 0;
                                    replicacaoAtual = 0;
                                    recarregar.putExtra("blocoAtual", blocoAtual);
                                    recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                                    recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                                    recarregar.putExtra("fatorAtual", fatorAtual);
                                    finish();
                                    startActivity(recarregar);
                                }
                            }
                        } else if (fatorAtual + 1 < formulario.getQuantidadeFatores_Formulario()) {
                            inserirDados(dados);
                            fatorAtual++;
                            repeticaoAtual = 0;
                            replicacaoAtual = 0;
                            recarregar.putExtra("blocoAtual", blocoAtual);
                            recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                            recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                            recarregar.putExtra("fatorAtual", fatorAtual);
                            finish();
                            startActivity(recarregar);
                        }
                    } else if (repeticaoAtual + 1 < formulario.getQuantidadeRepeticoes_Formulario()) {
                        inserirDados(dados);
                        repeticaoAtual++;
                        replicacaoAtual = 0;
                        recarregar.putExtra("blocoAtual", blocoAtual);
                        recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                        recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                        recarregar.putExtra("fatorAtual", fatorAtual);
                        finish();
                        startActivity(recarregar);
                    }
                } else if (replicacaoAtual + 1 < formulario.getQuantidadeReplicacoes_Formulario()) {
                    inserirDados(dados);
                    replicacaoAtual++;
                    recarregar.putExtra("blocoAtual", blocoAtual);
                    recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                    recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                    recarregar.putExtra("fatorAtual", fatorAtual);
                    finish();
                    startActivity(recarregar);
                }


            }
        });

        FloatingActionButton anteriorDadoColetarFatorial = (FloatingActionButton) findViewById(R.id.fab_anteriorDadoColetarFatorial);
        anteriorDadoColetarFatorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recarregar = new Intent(ColetarDadosFatorial.this, ColetarDadosFatorial.class);
                recarregar.putExtra("id_Formulario", id_Formulario);
                recarregar.putExtra("id_Coleta", id_Coleta);


                if (replicacaoAtual == 0) {
                    if (repeticaoAtual == 0) {
                        if (fatorAtual == 0) {
                            if (blocoAtual == 0) {
                                Toast.makeText(getApplicationContext(), getString(R.string.info_Retornar), Toast.LENGTH_SHORT).show();
                            } else if (blocoAtual > 0) {
                                blocoAtual--;
                                fatorAtual = formulario.getQuantidadeFatores_Formulario() - 1;
                                repeticaoAtual = formulario.getQuantidadeRepeticoes_Formulario() - 1;
                                replicacaoAtual = formulario.getQuantidadeReplicacoes_Formulario() - 1;
                                recarregar.putExtra("blocoAtual", blocoAtual);
                                recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                                recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                                recarregar.putExtra("fatorAtual", fatorAtual);
                                finish();
                                startActivity(recarregar);
                            }
                        } else if (fatorAtual > 0) {
                            fatorAtual--;
                            repeticaoAtual = formulario.getQuantidadeRepeticoes_Formulario() - 1;
                            replicacaoAtual = formulario.getQuantidadeReplicacoes_Formulario() - 1;
                            recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                            recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                            recarregar.putExtra("fatorAtual", fatorAtual);
                            recarregar.putExtra("blocoAtual", blocoAtual);
                            finish();
                            startActivity(recarregar);
                        }
                    } else if (repeticaoAtual > 0) {
                        repeticaoAtual--;
                        replicacaoAtual = formulario.getQuantidadeReplicacoes_Formulario() - 1;
                        recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                        recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                        recarregar.putExtra("fatorAtual", fatorAtual);
                        recarregar.putExtra("blocoAtual", blocoAtual);
                        finish();
                        startActivity(recarregar);
                    }
                } else if (replicacaoAtual > 0) {
                    replicacaoAtual--;
                    recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                    recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                    recarregar.putExtra("fatorAtual", fatorAtual);
                    recarregar.putExtra("blocoAtual", blocoAtual);
                    finish();
                    startActivity(recarregar);
                }
            }
        });
    }


    public void inserirDados(ArrayList<Dado> dados) {
        for (int i = 0; i < dados.size(); i++) {
            dados.get(i).setIdVariavel_Dado(variaveis.get(i).getId_Variavel());
            dados.get(i).setIdFator_Dado(fatores.get(fatorAtual).getId_Fator());
            dados.get(i).setIdNivelFator_Dado(niveisFator.get(nivelFatorAtual).getId_NivelFator());
            dados.get(i).setVariavel_Dado(i);
            dados.get(i).setBloco_Dado(blocoAtual);
            dados.get(i).setRepeticao_Dado(repeticaoAtual);
            dados.get(i).setReplicacao_Dado(replicacaoAtual);
            dados.get(i).setIdColeta_Dado(id_Coleta);
            Long checagem = dbAuxilar.checarDado(dados.get(i));
            if (checagem == 0) {
                dbAuxilar.inserirDado(dados.get(i));
            } else {
                dados.get(i).setId_Dado(checagem);
                dbAuxilar.updateDado(dados.get(i));
            }
        }
        coleta.setStatus_Coleta(String.valueOf(fatorAtual) + ","
                + String.valueOf(replicacaoAtual) + "," + String.valueOf(repeticaoAtual) + ","
                + String.valueOf(blocoAtual));
        dbAuxilar.updateStatusColeta(coleta);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent listarColetas = new Intent(ColetarDadosFatorial.this, ListarColetas.class);
        listarColetas.putExtra("id_Formulario", id_Formulario);
        listarColetas.putExtra("tipo_Formulario", dbAuxilar.lerFormulario(id_Formulario).getTipo_Formulario());
        finish();
        startActivity(listarColetas);
    }
}
