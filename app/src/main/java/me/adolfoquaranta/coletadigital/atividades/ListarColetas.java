package me.adolfoquaranta.coletadigital.atividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.adaptadores.ColetasAdapter;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.componentes_recycler.DividerItemDecoration;
import me.adolfoquaranta.coletadigital.componentes_recycler.RecyclerItemClickListener;
import me.adolfoquaranta.coletadigital.modelos.Coleta;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class ListarColetas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Coleta> coletasList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ColetasAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private RelativeLayout mostrar_coletas_root;

    private Long id_Formulario;
    private String tipo_Formulario;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_coletas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarColetas);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
                startActivity(cadastroColeta);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarColetas);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String escolhaUsuario = item.getTitle().toString();

        if (dbAuxilar.lerTodosFormularios(escolhaUsuario).size() == 0) {
            Intent cadastroFormulario = new Intent(ListarColetas.this, CadastroFormulario.class);
            cadastroFormulario.putExtra("tipo_Formulario", escolhaUsuario);
            finish();
            startActivity(cadastroFormulario);
        } else {
            Intent mostrarFormularios = new Intent(ListarColetas.this, ListarFormularios.class);
            mostrarFormularios.putExtra("tipo_Formulario", escolhaUsuario);
            finish();
            startActivity(mostrarFormularios);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarColetas);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;

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

                                    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                                    String fileDir = "COLETA DIGITAL";
                                    String fileName = (coletasList.get(position).getNome_Coleta()) + ".csv";
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
                                            String[] cabecalhoPosicoes, cabecalhoNomeVariaveis = new String[formulario.getQuantidadeVariaveis_Formulario()];

                                            if (formulario.getQuantidadeReplicacoes_Formulario() == 1) {
                                                cabecalhoPosicoes = new String[]{"TRATAMENTO", "REPETICAO"};
                                            } else {
                                                cabecalhoPosicoes = new String[]{"TRATAMENTO", "REPETICAO", "REPLICA"};
                                            }

                                            for (int v = 0; v < formulario.getQuantidadeVariaveis_Formulario(); v++) {
                                                cabecalhoNomeVariaveis[v] = variaveis.get(v).getNome_Variavel();
                                            }
                                            linhas.add(ArrayUtils.addAll(cabecalhoPosicoes, cabecalhoNomeVariaveis));
                                            for (int trat = 0; trat < formulario.getQuantidadeTratamentos_Formulario(); trat++) {
                                                for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                                                    for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                                        String[] posicoes;
                                                        if (formulario.getQuantidadeReplicacoes_Formulario() == 1) {
                                                            posicoes = new String[]{String.valueOf(trat + 1), String.valueOf(rep + 1)};
                                                        } else {
                                                            posicoes = new String[]{String.valueOf(trat + 1), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                                        }
                                                        String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                                        for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                                            valores[var] = dbAuxilar.lerValorDado(tratamentos.get(trat).getId_Tratamento(), rep, repli, variaveis.get(var).getId_Variavel(), coletasList.get(position).getId_Coleta()).getValor_Dado();
                                                        }
                                                        String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                                        linhas.add(colunas);
                                                    }
                                                }
                                            }
                                            writer.writeAll(linhas);
                                            writer.close();
                                            Log.i("Witter", "ArquivoGerado");
                                            Snackbar.make(childView, getString(R.string.info_ArquivoGerado), Snackbar.LENGTH_LONG).show();

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case 1:
                                    Intent coletarDados = new Intent(ListarColetas.this, ColetarDados.class);
                                    coletarDados.putExtra("id_Formulario", id_Formulario);
                                    coletarDados.putExtra("id_Coleta", coletasList.get(position).getId_Coleta());
                                    startActivity(coletarDados);
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
