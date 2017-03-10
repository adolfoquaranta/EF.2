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
import android.widget.ArrayAdapter;
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
import me.adolfoquaranta.coletadigital.modelos.Modelo;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class ListarColetas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Coleta> coletaList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ColetasAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private RelativeLayout mostrar_coletas_root;

    private Integer escolhaUsuario = 0, modelo_Modelo;
    private Long idFormulario;
    private String tipoFormulario;

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

        drawer.openDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mostrar_coletas_root = (RelativeLayout) findViewById(R.id.mostrar_coletas_root);

        Intent listarColetas = getIntent();
        idFormulario = listarColetas.getLongExtra("id_Formulario", 0);
        tipoFormulario = listarColetas.getStringExtra("tipo_Formulario");
        modelo_Modelo = listarColetas.getIntExtra("modelo_Modelo", -1);

        dbAuxilar = new DBAuxilar(getApplicationContext());

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + dbAuxilar.lerFormulario(idFormulario).getNome_Form());

        (navigationView.getMenu()).findItem(R.id.itemDrawer_nomeModelo_formulario).setTitle(dbAuxilar.lerFormulario(idFormulario).getTipo_Form() + " " + dbAuxilar.lerFormulario(idFormulario).getNome_Form());


        coletaList = dbAuxilar.lerTodasColetas(idFormulario);


        if (coletaList.isEmpty()) {
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

            mAdapter = new ColetasAdapter(coletaList);
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
                ArrayList<Modelo> modelos = dbAuxilar.lerTodosModelos(idFormulario);
                ArrayList<String> modelosExistentes = new ArrayList<>(4);

                for (Modelo m : modelos) {
                    if (m.getModelo_Modelo() == 0) {
                        modelosExistentes.add("DIC");
                    }
                    if (m.getModelo_Modelo() == 1) {
                        modelosExistentes.add("DBC");
                    }
                    if (m.getModelo_Modelo() == 2) {
                        modelosExistentes.add("FAT");
                    }
                    if (m.getModelo_Modelo() == 3) {
                        modelosExistentes.add("SUB");
                    }
                }


                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dialog_modelos, modelosExistentes);

                AlertDialog.Builder builderNovaColeta = new AlertDialog.Builder(ListarColetas.this);
                builderNovaColeta.setTitle(R.string.dialog_modelo)
                        .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                modelo_Modelo = which;
                                String txtModelo = arrayAdapter.getItem(which);
                                if (txtModelo.equals("DIC")) {
                                    modelo_Modelo = 0;
                                }
                                if (txtModelo.equals("DBC")) {
                                    modelo_Modelo = 1;
                                }
                                if (txtModelo.equals("FAT")) {
                                    modelo_Modelo = 2;
                                }
                                if (txtModelo.equals("SUB")) {
                                    modelo_Modelo = 3;
                                }
                                Intent cadastroColeta = new Intent(ListarColetas.this, CadastroColeta.class);
                                cadastroColeta.putExtra("id_Formulario", idFormulario);
                                cadastroColeta.putExtra("tipo_Formulario", tipoFormulario);
                                cadastroColeta.putExtra("modelo_Modelo", modelo_Modelo);
                                startActivity(cadastroColeta);
                            }
                        }).create().show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listar_coletas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_coletaExportar) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_coletaContinuar) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_coletaRemedir) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_coletaEditar) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_coletaRemover) {
            escolhaUsuario = id;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarColetas);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(View childView, final int position) {
            // Do something when an item is clicked, or override something else.
            switch (escolhaUsuario) {
                case 0:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarColetas);
                    assert drawer != null;
                    drawer.openDrawer(GravityCompat.START);
                    break;
                case R.id.nav_coletaExportar:

                    Modelo modelo = dbAuxilar.lerModeloDaColeta(coletaList.get(position).getIdForm_Coleta());


                    Log.d("modelo", modelo.toString());


                    ArrayList<Variavel> variaveis = dbAuxilar.lerTodasVariaveis(modelo.getIdFormulario_Modelo(), modelo.getId_Modelo());
                    ArrayList<Tratamento> tratamentos = dbAuxilar.lerTodosTratamentos(modelo.getIdFormulario_Modelo(), modelo.getId_Modelo());

                    Log.d("variaveis", variaveis.toString());

                    Log.d("tratamentos", tratamentos.toString());

                    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                    String fileDir = "COLETA DIGITAL";
                    String fileName = (coletaList.get(position).getNome_Coleta()) + ".csv";
                    String filePath = baseDir + File.separator + fileDir + File.separator + fileName;

                    File folder = new File(baseDir + File.separator + fileDir);
                    Boolean success = true;
                    if(!folder.exists()){
                        success = folder.mkdirs();
                    }
                    if(success){
                        CSVWriter writer;
                        try {
                            writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
                            List<String[]> linhas = new ArrayList<>();
                            String[] cabecalhoPosicoes, cabecalhoNomeVariaveis = new String[modelo.getQuantidadeVariaveis_Modelo()];
                            cabecalhoPosicoes = new String[]{"TRATAMENTO", "REPETICAO", "REPLICACAO"};
                            for (int v = 0; v < modelo.getQuantidadeVariaveis_Modelo(); v++) {
                                cabecalhoNomeVariaveis[v] = variaveis.get(v).getNome_Variavel();
                            }
                            linhas.add(ArrayUtils.addAll(cabecalhoPosicoes, cabecalhoNomeVariaveis));
                            for (int trat = 0; trat < modelo.getQuantidadeTratamentos_Modelo(); trat++) {
                                for (int rep = 0; rep < modelo.getQuantidadeRepeticoes_Modelo(); rep++) {
                                    for (int repli = 0; repli < modelo.getQuantidadeReplicacoes_Modelo(); repli++) {
                                        String[] posicoes = new String[]{String.valueOf(trat + 1), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                        String[] valores = new String[modelo.getQuantidadeVariaveis_Modelo()];
                                        for (int var = 0; var < modelo.getQuantidadeVariaveis_Modelo(); var++) {
                                            valores[var] = dbAuxilar.lerValorDado(tratamentos.get(trat).getId_Tratamento(), rep, repli, variaveis.get(var).getId_Variavel(), coletaList.get(position).getId_Coleta()).getValor_Dado();
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

                case R.id.nav_coletaRemover:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListarColetas.this);
                    builder.setTitle(R.string.dialog_removerColeta)
                            .setMessage(R.string.dialog_acaoPermanente)
                            .setPositiveButton(R.string.dialog_remover, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d("idColeta", coletaList.get(position).getId_Coleta().toString());
                                    dbAuxilar.deleteColeta(coletaList.get(position).getId_Coleta());
                                    Intent recarregar = new Intent(ListarColetas.this, ListarColetas.class);
                                    recarregar.putExtra("tipo_Formulario", tipoFormulario);
                                    recarregar.putExtra("id_Formulario", idFormulario);
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

    }

}
