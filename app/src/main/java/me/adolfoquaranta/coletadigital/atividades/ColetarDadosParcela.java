package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.NivelParcela;
import me.adolfoquaranta.coletadigital.modelos.Parcela;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class ColetarDadosParcela extends AppCompatActivity {

    private DBAuxilar dbAuxilar;
    private List<Parcela> parcelas;
    private List<NivelParcela> niveisParcela;
    private List<Variavel> variaveis;
    private Integer parcelaAtual, nivelParcelaAtual, repeticaoAtual, replicacaoAtual, blocoAtual;
    private Long id_Coleta, id_Formulario;

    private Formulario formulario;
    private Coleta coleta;

    private Integer quantidadeTotalDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar_dadosparcela);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAuxilar = new DBAuxilar(getApplicationContext());

        final Intent coletarDadosParcelaIntent = getIntent();
        id_Formulario = coletarDadosParcelaIntent.getLongExtra("id_Formulario", 0);
        id_Coleta = coletarDadosParcelaIntent.getLongExtra("id_Coleta", 0);
        parcelaAtual = coletarDadosParcelaIntent.getIntExtra("parcelaAtual", 0);
        nivelParcelaAtual = coletarDadosParcelaIntent.getIntExtra("nivelParcelaAtual", 0);
        replicacaoAtual = coletarDadosParcelaIntent.getIntExtra("replicacaoAtual", 0);
        repeticaoAtual = coletarDadosParcelaIntent.getIntExtra("repeticaoAtual", 0);
        blocoAtual = coletarDadosParcelaIntent.getIntExtra("blocoAtual", 0);

        Log.d("parcelaAtual", parcelaAtual.toString());
        Log.d("nivelParcelaAtual", nivelParcelaAtual.toString());
        Log.d("repeticaoAtual", repeticaoAtual.toString());
        Log.d("replicacaoAtual", replicacaoAtual.toString());

        formulario = dbAuxilar.lerFormulario(id_Formulario);
        coleta = dbAuxilar.lerColeta(id_Coleta);
        parcelas = dbAuxilar.lerTodasParcelas(id_Formulario);
        niveisParcela = dbAuxilar.lerTodosNiveisParcela(parcelas.get(parcelaAtual).getId_Parcela());
        variaveis = dbAuxilar.lerTodasVariaveis(id_Formulario);

        Log.d("formulario", formulario.toString());
        Log.d("coleta", coleta.toString());
        Log.d("parcelas", parcelas.toString());
        Log.d("niveisParcela", niveisParcela.toString());
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

        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_ColetarDadosParcela);

        final TextView infoColetaAtual = (TextView) findViewById(R.id.tv_infoColetaAtualParcela);


        final String bloco = "Bloco " + (blocoAtual + 1), parcela = parcelas.get(parcelaAtual).getNome_Parcela(), nivelParcela = niveisParcela.get(nivelParcelaAtual).getNome_NivelParcela(), repeticao = "Repetição " + (repeticaoAtual + 1), replicacao = "Réplica " + (replicacaoAtual + 1);
        String infoColeta = "";
        if (formulario.getQuantidadeBlocos_Formulario() <= 1 && formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
            infoColeta = parcela + " | " + nivelParcela + " | " + repeticao;
            quantidadeTotalDados = parcelas.size() * niveisParcela.size() * formulario.getQuantidadeRepeticoes_Formulario() * variaveis.size();
        } else if (formulario.getQuantidadeBlocos_Formulario() <= 1) {
            infoColeta = parcela + " | " + nivelParcela + " | " + repeticao + " | " + replicacao;
            quantidadeTotalDados = parcelas.size() * niveisParcela.size() * formulario.getQuantidadeRepeticoes_Formulario() * formulario.getQuantidadeRepeticoes_Formulario() * variaveis.size();
        } else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
            infoColeta = bloco + " | " + parcela + " | " + nivelParcela;
            quantidadeTotalDados = formulario.getQuantidadeBlocos_Formulario() * parcelas.size() * niveisParcela.size() * variaveis.size();
        } else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
            infoColeta = bloco + " | " + parcela + " | " + nivelParcela + " | " + repeticao;
            quantidadeTotalDados = formulario.getQuantidadeBlocos_Formulario() * parcelas.size() * niveisParcela.size() * formulario.getQuantidadeRepeticoes_Formulario() * variaveis.size();
        } else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
            infoColeta = bloco + " | " + parcela + " | " + nivelParcela + " | " + replicacao;
            quantidadeTotalDados = formulario.getQuantidadeBlocos_Formulario() * parcelas.size() * niveisParcela.size() * formulario.getQuantidadeReplicacoes_Formulario() * variaveis.size();
        } else {
            infoColeta = bloco + " | " + parcela + " | " + nivelParcela + " | " + repeticao + " | " + replicacao;
            quantidadeTotalDados = formulario.getQuantidadeBlocos_Formulario() * parcelas.size() * niveisParcela.size() * formulario.getQuantidadeRepeticoes_Formulario() * formulario.getQuantidadeRepeticoes_Formulario() * variaveis.size();

        }

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

            try {
                etValorVariavel.setText(dbAuxilar.lerValorDadoParcela(parcelas.get(parcelaAtual).getId_Parcela(), niveisParcela.get(nivelParcelaAtual).getId_NivelParcela(), blocoAtual, repeticaoAtual, replicacaoAtual, variaveis.get(i).getId_Variavel(), id_Coleta).getValor_Dado());
            } catch (Exception e) {
                Log.e(e.getLocalizedMessage(), e.getMessage());
            }


            if (variaveis.get(i).getTipo_Variavel() == 1) {
                etValorVariavel.setInputType(InputType.TYPE_CLASS_TEXT);
                etValorVariavel.setFilters(new InputFilter[]{filterLetraOuDigito});
            } else if (variaveis.get(i).getTipo_Variavel() == 2) {
                etValorVariavel.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                etValorVariavel.setFilters(new InputFilter[]{filterDigito});
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
                if (dbAuxilar.lerValorDadoParcela(parcelas.get(parcelaAtual).getId_Parcela(), niveisParcela.get(nivelParcelaAtual).getId_NivelParcela(), blocoAtual, repeticaoAtual, replicacaoAtual, variaveis.get(i).getId_Variavel(), id_Coleta).getValor_Dado().equals(" ")) {
                    anularVariavel.toggle();
                    etValorVariavel.setEnabled(false);
                }
            } catch (Exception e) {
                Log.e(e.getLocalizedMessage(), e.getMessage());
            }

        }

        FloatingActionButton proximoDadoColetarParcela = (FloatingActionButton) findViewById(R.id.fab_proximoDadoColetarParcela);
        proximoDadoColetarParcela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recarregar = new Intent(ColetarDadosParcela.this, ColetarDadosParcela.class);
                recarregar.putExtra("id_Formulario", id_Formulario);
                recarregar.putExtra("id_Coleta", id_Coleta);

                if (!inserirDados()) {
                    return;
                }

                if (replicacaoAtual + 1 == formulario.getQuantidadeReplicacoes_Formulario()) {
                    if (repeticaoAtual + 1 == formulario.getQuantidadeRepeticoes_Formulario()) {
                        if (parcelaAtual + 1 == formulario.getQuantidadeParcelas_Formulario()) {
                            if (nivelParcelaAtual + 1 == parcelas.get(parcelaAtual).getQuantidadeNiveis_Parcela()) {
                                if (formulario.getQuantidadeBlocos_Formulario() == -1) {
                                    Intent listarColetas = new Intent(ColetarDadosParcela.this, ListarColetas.class);
                                    listarColetas.putExtra("id_Formulario", id_Formulario);
                                    listarColetas.putExtra("tipo_Formulario", dbAuxilar.lerFormulario(id_Formulario).getTipo_Formulario());
                                    coleta.setStatus_Coleta("ok");
                                    dbAuxilar.updateStatusColeta(coleta);
                                    finish();
                                    startActivity(listarColetas);
                                } else {
                                    if (blocoAtual + 1 == formulario.getQuantidadeBlocos_Formulario()) {
                                        Intent listarColetas = new Intent(ColetarDadosParcela.this, ListarColetas.class);
                                        listarColetas.putExtra("id_Formulario", id_Formulario);
                                        listarColetas.putExtra("tipo_Formulario", dbAuxilar.lerFormulario(id_Formulario).getTipo_Formulario());
                                        coleta.setStatus_Coleta("ok");
                                        dbAuxilar.updateStatusColeta(coleta);
                                        finish();
                                        startActivity(listarColetas);
                                    } else if (blocoAtual + 1 < formulario.getQuantidadeBlocos_Formulario()) {
                                        blocoAtual++;
                                        parcelaAtual = 0;
                                        nivelParcelaAtual = 0;
                                        repeticaoAtual = 0;
                                        replicacaoAtual = 0;
                                        recarregar.putExtra("blocoAtual", blocoAtual);
                                        recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                                        recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                                        recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                                        recarregar.putExtra("parcelaAtual", parcelaAtual);
                                        finish();
                                        startActivity(recarregar);
                                    }
                                }
                            } else if (nivelParcelaAtual + 1 < parcelas.get(parcelaAtual).getQuantidadeNiveis_Parcela()) {
                                nivelParcelaAtual++;
                                repeticaoAtual = 0;
                                replicacaoAtual = 0;
                                recarregar.putExtra("blocoAtual", blocoAtual);
                                recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                                recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                                recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                                recarregar.putExtra("parcelaAtual", parcelaAtual);
                                finish();
                                startActivity(recarregar);
                            }
                        } else if (parcelaAtual + 1 < formulario.getQuantidadeParcelas_Formulario()) {
                            parcelaAtual++;
                            nivelParcelaAtual = 0;
                            repeticaoAtual = 0;
                            replicacaoAtual = 0;
                            recarregar.putExtra("blocoAtual", blocoAtual);
                            recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                            recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                            recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                            recarregar.putExtra("parcelaAtual", parcelaAtual);
                            finish();
                            startActivity(recarregar);
                        }
                    } else if (repeticaoAtual + 1 < formulario.getQuantidadeRepeticoes_Formulario()) {
                        repeticaoAtual++;
                        replicacaoAtual = 0;
                        recarregar.putExtra("blocoAtual", blocoAtual);
                        recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                        recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                        recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                        recarregar.putExtra("parcelaAtual", parcelaAtual);
                        finish();
                        startActivity(recarregar);
                    }
                } else if (replicacaoAtual + 1 < formulario.getQuantidadeReplicacoes_Formulario()) {
                    replicacaoAtual++;
                    recarregar.putExtra("blocoAtual", blocoAtual);
                    recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                    recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                    recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                    recarregar.putExtra("parcelaAtual", parcelaAtual);
                    finish();
                    startActivity(recarregar);
                }


            }
        });

        FloatingActionButton anteriorDadoColetarParcela = (FloatingActionButton) findViewById(R.id.fab_anteriorDadoColetarParcela);
        anteriorDadoColetarParcela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recarregar = new Intent(ColetarDadosParcela.this, ColetarDadosParcela.class);
                recarregar.putExtra("id_Formulario", id_Formulario);
                recarregar.putExtra("id_Coleta", id_Coleta);

                if (!inserirDados()) {
                    return;
                }

                if (replicacaoAtual == 0) {
                    if (repeticaoAtual == 0) {
                        if (parcelaAtual == 0) {
                            if (nivelParcelaAtual == 0) {
                                if (blocoAtual == 0) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.info_Retornar), Toast.LENGTH_SHORT).show();
                                } else if (blocoAtual > 0) {
                                    blocoAtual--;
                                    nivelParcelaAtual = parcelas.get(parcelaAtual).getQuantidadeNiveis_Parcela() - 1;
                                    parcelaAtual = formulario.getQuantidadeParcelas_Formulario() - 1;
                                    repeticaoAtual = formulario.getQuantidadeRepeticoes_Formulario() - 1;
                                    replicacaoAtual = formulario.getQuantidadeReplicacoes_Formulario() - 1;
                                    recarregar.putExtra("blocoAtual", blocoAtual);
                                    recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                                    recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                                    recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                                    recarregar.putExtra("parcelaAtual", parcelaAtual);
                                    finish();
                                    startActivity(recarregar);
                                }
                            } else if (nivelParcelaAtual > 0) {
                                nivelParcelaAtual--;
                                repeticaoAtual = formulario.getQuantidadeRepeticoes_Formulario() - 1;
                                replicacaoAtual = formulario.getQuantidadeReplicacoes_Formulario() - 1;
                                recarregar.putExtra("blocoAtual", blocoAtual);
                                recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                                recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                                recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                                recarregar.putExtra("parcelaAtual", parcelaAtual);
                                finish();
                                startActivity(recarregar);
                            }
                        } else if (parcelaAtual > 0) {
                            parcelaAtual--;
                            nivelParcelaAtual = parcelas.get(parcelaAtual).getQuantidadeNiveis_Parcela() - 1;
                            repeticaoAtual = formulario.getQuantidadeRepeticoes_Formulario() - 1;
                            replicacaoAtual = formulario.getQuantidadeReplicacoes_Formulario() - 1;
                            recarregar.putExtra("blocoAtual", blocoAtual);
                            recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                            recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                            recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                            recarregar.putExtra("parcelaAtual", parcelaAtual);
                            finish();
                            startActivity(recarregar);
                        }
                    } else if (repeticaoAtual > 0) {
                        repeticaoAtual--;
                        replicacaoAtual = formulario.getQuantidadeReplicacoes_Formulario() - 1;
                        recarregar.putExtra("blocoAtual", blocoAtual);
                        recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                        recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                        recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                        recarregar.putExtra("parcelaAtual", parcelaAtual);
                        finish();
                        startActivity(recarregar);
                    }
                } else if (replicacaoAtual > 0) {
                    replicacaoAtual--;
                    recarregar.putExtra("blocoAtual", blocoAtual);
                    recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                    recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                    recarregar.putExtra("nivelParcelaAtual", nivelParcelaAtual);
                    recarregar.putExtra("parcelaAtual", parcelaAtual);
                    finish();
                    startActivity(recarregar);
                }
            }
        });
    }

    private boolean inserirDados() {
        ArrayList<Dado> dados = new ArrayList<>(variaveis.size());
        Integer id = 0;
        while (findViewById(id) instanceof MaterialEditText) {
            Dado dado = new Dado();
            dado.setIdColeta_Dado(id_Coleta);
            MaterialEditText etVariavel = (MaterialEditText) findViewById(id);
            if (etVariavel.isEnabled() && etVariavel.getText().toString().equals("")) {
                //Snackbar.make(view, getString(R.string.info_PreechaOuAnule), Snackbar.LENGTH_LONG).show();
                etVariavel.setError(getString(R.string.err_msg_preenchaOuAnule));
                return false;
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
        Log.d("formulario", formulario.toString());


        for (int i = 0; i < dados.size(); i++) {
            dados.get(i).setIdVariavel_Dado(variaveis.get(i).getId_Variavel());
            dados.get(i).setIdParcela_Dado(parcelas.get(parcelaAtual).getId_Parcela());
            dados.get(i).setIdNivelParcela_Dado(niveisParcela.get(nivelParcelaAtual).getId_NivelParcela());
            dados.get(i).setVariavel_Dado(i);
            dados.get(i).setBloco_Dado(blocoAtual);
            dados.get(i).setRepeticao_Dado(repeticaoAtual);
            dados.get(i).setReplicacao_Dado(replicacaoAtual);
            dados.get(i).setIdColeta_Dado(id_Coleta);
            Long checagem = dbAuxilar.checarDadoPar(dados.get(i));
            if (checagem == 0) {
                dbAuxilar.inserirDado(dados.get(i));
            } else {
                dados.get(i).setId_Dado(checagem);
                dbAuxilar.updateDado(dados.get(i));
            }
        }
        if (dbAuxilar.lerTodosDadoDe(id_Coleta).size() != quantidadeTotalDados)
            coleta.setStatus_Coleta(String.valueOf(parcelaAtual) + "," + String.valueOf(nivelParcelaAtual) + ","
                    + String.valueOf(replicacaoAtual) + "," + String.valueOf(repeticaoAtual) + ","
                    + String.valueOf(blocoAtual));
        else coleta.setStatus_Coleta("ok");
        dbAuxilar.updateStatusColeta(coleta);


        return true;
    }


    @Override
    public void onBackPressed() {
        /*super.onBackPressed();
        if(!checarDados()){
            return;
        }
        inserirDados();*/
        Intent listarColetas = new Intent(ColetarDadosParcela.this, ListarColetas.class);
        listarColetas.putExtra("id_Formulario", id_Formulario);
        listarColetas.putExtra("tipo_Formulario", dbAuxilar.lerFormulario(id_Formulario).getTipo_Formulario());
        finish();
        startActivity(listarColetas);
    }
}
