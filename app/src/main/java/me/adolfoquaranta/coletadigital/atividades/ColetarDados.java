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
import me.adolfoquaranta.coletadigital.modelos.Modelo;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class ColetarDados extends AppCompatActivity {

    private DBAuxilar dbAuxilar;
    private List<Tratamento> tratamentos;
    private List<Variavel> variaveis;
    private Modelo modelo;
    private Integer tratamentoAtual, repeticaoAtual, replicacaoAtual, modelo_Modelo;
    private Long id_Coleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar_dados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAuxilar = new DBAuxilar(getApplicationContext());

        final Intent coletarDadosIntent = getIntent();
        final Long id_Formulario = coletarDadosIntent.getLongExtra("id_Formulario", 0);
        id_Coleta = coletarDadosIntent.getLongExtra("id_Coleta", 0);
        tratamentoAtual = coletarDadosIntent.getIntExtra("tratamentoAtual", 0);
        replicacaoAtual = coletarDadosIntent.getIntExtra("replicacaoAtual", 0);
        repeticaoAtual = coletarDadosIntent.getIntExtra("repeticaoAtual", 0);
        modelo_Modelo = coletarDadosIntent.getIntExtra("modelo_Modelo", -1);

        Log.d("tratamentoAtual", tratamentoAtual.toString());
        Log.d("repeticaoAtual", repeticaoAtual.toString());
        Log.d("replicacaoAtual", replicacaoAtual.toString());

        modelo = dbAuxilar.lerModeloDoFormulario(id_Formulario, modelo_Modelo);
        Coleta coleta = dbAuxilar.lerColeta(id_Coleta);
        tratamentos = dbAuxilar.lerTodosTratamentos(id_Formulario, coleta.getIdModelo_Coleta());
        variaveis = dbAuxilar.lerTodasVariaveis(id_Formulario, coleta.getIdModelo_Coleta());

        Log.d("modelo", modelo.toString());
        Log.d("coleta", coleta.toString());
        Log.d("tratamentos", tratamentos.toString());
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

        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_ColetarDados);

        final TextView infoColetaAtual = (TextView) findViewById(R.id.tv_infoColetaAtual);
        infoColetaAtual.setText("TRAT " + (tratamentoAtual + 1) + " | REP " + (repeticaoAtual + 1) + " | REPLI " + (replicacaoAtual + 1));

        for (int i = 0; i < variaveis.size(); i++) {
            final LinearLayout layoutInterno = new LinearLayout(this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(3);
            layoutInterno.setFocusable(true);

            //nome tratamento
            final MaterialEditText etValorVariavel = new MaterialEditText(this); // Pass it an Activity or Context
            etValorVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etValorVariavel.setId(i);
            etValorVariavel.setOnFocusChangeListener(validar);
            etValorVariavel.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etValorVariavel.setHint(variaveis.get(i).getNome_Variavel());
            etValorVariavel.setFloatingLabelText(variaveis.get(i).getNome_Variavel());
            etValorVariavel.setFloatingLabelAnimating(true);
            if (variaveis.get(i).getTipo_Variavel() == 1) {
                etValorVariavel.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                etValorVariavel.setFilters(new InputFilter[]{filterDigito});
            } else if (variaveis.get(i).getTipo_Variavel() == 2) {
                etValorVariavel.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                etValorVariavel.setFilters(new InputFilter[]{filterCaracter});
            }
            layoutInterno.addView(etValorVariavel);

            final ToggleButton anularVariavel = new ToggleButton(getApplicationContext());
            anularVariavel.setText(R.string.anular);
            anularVariavel.setId(i + variaveis.size());
            anularVariavel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            anularVariavel.setId(i + variaveis.size());
            anularVariavel.setTextOn(getString(R.string.nulo));
            anularVariavel.setTextOff(getString(R.string.anular));
            layoutInterno.addView(anularVariavel);
            myLayout.addView(layoutInterno);

            final int finalI = i;
            anularVariavel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialEditText etAtual = (MaterialEditText) layoutInterno.findViewById(v.getId() - variaveis.size());
                    if (etAtual.isEnabled()) {
                        etAtual.setText(null);
                        etAtual.setHint(R.string.nulo);
                        etAtual.setEnabled(false);
                    } else {
                        etAtual.setHint(variaveis.get(finalI).getNome_Variavel());
                        etAtual.setEnabled(true);
                    }
                }
            });

        }

        FloatingActionButton proximoDado = (FloatingActionButton) findViewById(R.id.fab_proximoDado);
        proximoDado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recarregar = new Intent(ColetarDados.this, ColetarDados.class);
                recarregar.putExtra("id_Formulario", id_Formulario);
                recarregar.putExtra("id_Coleta", id_Coleta);
                recarregar.putExtra("modelo_Modelo", modelo_Modelo);


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


                if (replicacaoAtual + 1 == modelo.getQuantidadeReplicacoes_Modelo()) {
                    if (repeticaoAtual + 1 == modelo.getQuantidadeRepeticoes_Modelo()) {
                        if (tratamentoAtual + 1 == modelo.getQuantidadeTratamentos_Modelo()) {
                            Intent listarColetas = new Intent(ColetarDados.this, ListarColetas.class);
                            listarColetas.putExtra("id_Formulario", id_Formulario);
                            listarColetas.putExtra("tipo_Formulario", dbAuxilar.lerFormulario(id_Formulario).getTipo_Form());
                            listarColetas.getIntExtra("modelo_Modelo", modelo_Modelo);

                            inserirDados(dados);
                            finish();

                            startActivity(listarColetas);
                        } else if (tratamentoAtual + 1 < modelo.getQuantidadeTratamentos_Modelo()) {
                            inserirDados(dados);
                            tratamentoAtual++;
                            repeticaoAtual = 0;
                            replicacaoAtual = 0;
                            recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                            recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                            recarregar.putExtra("tratamentoAtual", tratamentoAtual);
                            finish();
                            startActivity(recarregar);
                        }
                    } else if (repeticaoAtual + 1 < modelo.getQuantidadeRepeticoes_Modelo()) {
                        inserirDados(dados);
                        repeticaoAtual++;
                        replicacaoAtual = 0;
                        recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                        recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                        recarregar.putExtra("tratamentoAtual", tratamentoAtual);
                        finish();
                        startActivity(recarregar);
                    }
                } else if (replicacaoAtual + 1 < modelo.getQuantidadeReplicacoes_Modelo()) {
                    inserirDados(dados);
                    replicacaoAtual++;
                    recarregar.putExtra("replicacaoAtual", replicacaoAtual);
                    recarregar.putExtra("repeticaoAtual", repeticaoAtual);
                    recarregar.putExtra("tratamentoAtual", tratamentoAtual);
                    finish();
                    startActivity(recarregar);
                }


            }
        });

    }

    public void inserirDados(ArrayList<Dado> dados) {
        for (int i = 0; i < dados.size(); i++) {
            dados.get(i).setIdVariavel_Dado(variaveis.get(i).getId_Variavel());
            dados.get(i).setIdTratamento_Dado(tratamentos.get(tratamentoAtual).getId_Tratamento());
            dados.get(i).setVariavel_Dado(i);
            dados.get(i).setTratamento_Dado(tratamentoAtual);
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
    }

    public void onBackPressed() {
        Toast.makeText(this, getString(R.string.info_EmBreve), Toast.LENGTH_SHORT).show();
    }


}
