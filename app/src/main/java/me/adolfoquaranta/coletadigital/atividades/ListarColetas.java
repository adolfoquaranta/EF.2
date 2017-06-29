package me.adolfoquaranta.coletadigital.atividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.adaptadores.ColetasAdapter;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.componentes_recycler.DividerItemDecoration;
import me.adolfoquaranta.coletadigital.componentes_recycler.RecyclerItemClickListener;
import me.adolfoquaranta.coletadigital.modelos.Coleta;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class ListarColetas extends AppCompatActivity {

    private List<Coleta> coletasList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ColetasAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private RelativeLayout mostrar_coletas_root;

    private Long id_Formulario;
    private String tipo_Formulario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_coletas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mostrar_coletas_root = (RelativeLayout) findViewById(R.id.mostrar_coletas_root);

        Intent listarColetas = getIntent();
        id_Formulario = listarColetas.getLongExtra("id_Formulario", 0);
        tipo_Formulario = listarColetas.getStringExtra("tipo_Formulario");

        dbAuxilar = new DBAuxilar(getApplicationContext());

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + dbAuxilar.lerFormulario(id_Formulario).getNome_Formulario());


        coletasList = dbAuxilar.lerTodasColetas(id_Formulario);

        if (coletasList.isEmpty()) {
            RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(this);
            tv.setLayoutParams(lparams);
            String msg_naoExistemDados = getString(R.string.msg_naoExistemDados);
            tv.setText(msg_naoExistemDados);
            this.mostrar_coletas_root.addView(tv);
        } else {

            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarColetas);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarColetas);

            mAdapter = new ColetasAdapter(coletasList);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarColetas);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new ListarColetas.OnItemClickListener()));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_novaColeta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastroColeta = new Intent(ListarColetas.this, CadastroColeta.class);
                cadastroColeta.putExtra("id_Formulario", id_Formulario);
                cadastroColeta.putExtra("tipo_Formulario", tipo_Formulario);
                finish();
                startActivity(cadastroColeta);
            }
        });


    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(final View childView, final int position) {
            // Do something when an item is clicked, or override something else.
            AlertDialog.Builder builder = new AlertDialog.Builder(ListarColetas.this);
            builder.setTitle(coletasList.get(position).getNome_Coleta())
                    .setItems(R.array.array_opcoes_coletas, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch (which) {
                                case 0:
                                    Formulario formulario = dbAuxilar.lerFormulario(id_Formulario);
                                    ArrayList<Variavel> variaveis = dbAuxilar.lerTodasVariaveis(id_Formulario);
                                    ArrayList<Tratamento> tratamentos = dbAuxilar.lerTodosTratamentos(id_Formulario);

                                    Log.d("variaveis", variaveis.toString());

                                    Log.d("tratamentos", tratamentos.toString());

                                    String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                                    String fileDir = "COLETA DIGITAL";
                                    String fileName = (formulario.getNome_Formulario()
                                            + "_"
                                            + coletasList.get(position).getNome_Coleta()
                                            + "_"
                                            + new SimpleDateFormat("dd-MM-yyyy_HHmmss", new Locale("pt", "BR")).format(new Date()))
                                            + ".csv";
                                    String filePath = baseDir + File.separator + fileDir + File.separator + fileName;

                                    File folder = new File(baseDir + File.separator + fileDir);
                                    Boolean success = true;
                                    if (!folder.exists()) {
                                        success = folder.mkdirs();
                                    }
                                    if (success) {
                                        CSVWriter writer;
                                        try {
                                            writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
                                            List<String[]> linhas = new ArrayList<>();
                                            String[] cabecalhoPosicoes = new String[]{}, cabecalhoNomeVariaveis = new String[formulario.getQuantidadeVariaveis_Formulario()];

                                            String bloco = "BLOCO" , tratamento = "TRATAMENTO", repeticao = "REPETICAO" , replicacao = "REPLICA";

                                            if(formulario.getModelo_Formulario()==0) {
                                                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
                                                    cabecalhoPosicoes = new String[]{tratamento, repeticao};
                                                } else {
                                                    cabecalhoPosicoes = new String[]{tratamento, repeticao, replicacao};
                                                }
                                            }
                                            if(formulario.getModelo_Formulario()==1){
                                                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
                                                    cabecalhoPosicoes = new String[]{bloco, tratamento};
                                                }
                                                else if(formulario.getQuantidadeReplicacoes_Formulario()<=1) {
                                                    cabecalhoPosicoes = new String[]{bloco, tratamento, repeticao};
                                                }
                                                else if(formulario.getQuantidadeRepeticoes_Formulario()<=1) {
                                                    cabecalhoPosicoes = new String[]{bloco, tratamento, replicacao};
                                                }
                                                else {
                                                    cabecalhoPosicoes = new String[]{bloco, tratamento, repeticao, replicacao};
                                                }
                                            }

                                            for (int v = 0; v < formulario.getQuantidadeVariaveis_Formulario(); v++) {
                                                cabecalhoNomeVariaveis[v] = variaveis.get(v).getNome_Variavel();
                                            }
                                            linhas.add(ArrayUtils.addAll(cabecalhoPosicoes, cabecalhoNomeVariaveis));

                                            if(formulario.getQuantidadeBlocos_Formulario()!=-1){
                                                for(int bloc = 0; bloc < formulario.getQuantidadeBlocos_Formulario(); bloc++) {
                                                     for (int trat = 0; trat < formulario.getQuantidadeTratamentos_Formulario(); trat++) {
                                                         for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                                                             for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                                                 String[] posicoes;

                                                                 if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
                                                                     posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento()};
                                                                 }
                                                                 else if(formulario.getQuantidadeReplicacoes_Formulario()<=1) {
                                                                     posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1)};
                                                                 }
                                                                 else if(formulario.getQuantidadeRepeticoes_Formulario()<=1) {
                                                                     posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento(), String.valueOf(repli + 1)};
                                                                 }
                                                                 else {
                                                                     posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                                                 }

                                                                 String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                                                 for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                                                     valores[var] = dbAuxilar.lerValorDado(tratamentos.get(trat).getId_Tratamento(), bloc, rep, repli, variaveis.get(var).getId_Variavel(), coletasList.get(position).getId_Coleta()).getValor_Dado();
                                                                 }

                                                                 String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                                                 linhas.add(colunas);
                                                             }
                                                         }
                                                     }
                                                }
                                            }
                                            else{
                                                for (int trat = 0; trat < formulario.getQuantidadeTratamentos_Formulario(); trat++) {
                                                    for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                                                        for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                                            String[] posicoes;

                                                            if (formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
                                                                posicoes = new String[]{tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1)};
                                                            } else {
                                                                posicoes = new String[]{tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                                            }

                                                            String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                                            for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                                                valores[var] = dbAuxilar.lerValorDado(tratamentos.get(trat).getId_Tratamento(), 0, rep, repli, variaveis.get(var).getId_Variavel(), coletasList.get(position).getId_Coleta()).getValor_Dado();
                                                            }

                                                            String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                                            linhas.add(colunas);
                                                        }
                                                    }
                                                }
                                            }
                                            writer.writeAll(linhas);
                                            writer.close();
                                            Log.i("Witter", "ArquivoGerado");

                                            //Snackbar.make(childView, getString(R.string.info_ArquivoGerado), Snackbar.LENGTH_LONG).show();

                                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                            sharingIntent.setType("text/plain");
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                                            startActivity(Intent.createChooser(sharingIntent, getString(R.string.info_CompartilharUsando)));

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case 1:
                                    if (dbAuxilar.lerFormulario(coletasList.get(position).getIdFormulario_Coleta()).getModelo_Formulario() == 2) {
                                        Intent coletarDadosFatorial = new Intent(ListarColetas.this, ColetarDadosFatorial.class);
                                        coletarDadosFatorial.putExtra("id_Formulario", id_Formulario);
                                        coletarDadosFatorial.putExtra("id_Coleta", coletasList.get(position).getId_Coleta());
                                        if (!coletasList.get(position).getStatus_Coleta().equals("ok") &&
                                                !coletasList.get(position).getStatus_Coleta().equals("")) {
                                            String[] posicaoColeta = coletasList.get(position).getStatus_Coleta().split(",");
                                            coletarDadosFatorial.putExtra("fatorAtual", Integer.valueOf(posicaoColeta[0]));
                                            coletarDadosFatorial.putExtra("replicacaoAtual", Integer.valueOf(posicaoColeta[1]));
                                            coletarDadosFatorial.putExtra("repeticaoAtual", Integer.valueOf(posicaoColeta[2]));
                                            coletarDadosFatorial.putExtra("blocoAtual", Integer.valueOf(posicaoColeta[3]));
                                        }
                                        finish();
                                        startActivity(coletarDadosFatorial);
                                    } else {
                                        Intent coletarDados = new Intent(ListarColetas.this, ColetarDados.class);
                                        coletarDados.putExtra("id_Formulario", id_Formulario);
                                        coletarDados.putExtra("id_Coleta", coletasList.get(position).getId_Coleta());
                                        if (!coletasList.get(position).getStatus_Coleta().equals("ok") &&
                                                !coletasList.get(position).getStatus_Coleta().equals("")) {
                                            String[] posicaoColeta = coletasList.get(position).getStatus_Coleta().split(",");
                                            coletarDados.putExtra("tratamentoAtual", Integer.valueOf(posicaoColeta[0]));
                                            coletarDados.putExtra("replicacaoAtual", Integer.valueOf(posicaoColeta[1]));
                                            coletarDados.putExtra("repeticaoAtual", Integer.valueOf(posicaoColeta[2]));
                                            coletarDados.putExtra("blocoAtual", Integer.valueOf(posicaoColeta[3]));
                                        }
                                        finish();
                                        startActivity(coletarDados);
                                    }
                                    break;
                                case 2:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ListarColetas.this);
                                    builder.setTitle(R.string.dialog_removerColeta)
                                            .setMessage(R.string.dialog_acaoPermanente)
                                            .setPositiveButton(R.string.dialog_remover, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Log.d("idColeta", coletasList.get(position).getId_Coleta().toString());
                                                    dbAuxilar.deleteColeta(coletasList.get(position).getId_Coleta());
                                                    Intent recarregar = new Intent(ListarColetas.this, ListarColetas.class);
                                                    recarregar.putExtra("tipo_Formulario", tipo_Formulario);
                                                    recarregar.putExtra("id_Formulario", id_Formulario);
                                                    finish();
                                                    startActivity(recarregar);
                                                }
                                            })
                                            .setNegativeButton(R.string.dialog_cancelar, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // User cancelled the dialog
                                                }
                                            }).create().show();
                                    break;
                                default:
                                    Toast.makeText(ListarColetas.this, getString(R.string.info_EmBreve), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }).create().show();
        }

    }

}
