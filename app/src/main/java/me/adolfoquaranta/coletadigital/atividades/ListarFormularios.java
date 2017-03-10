package me.adolfoquaranta.coletadigital.atividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.adaptadores.FormulariosAdapter;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.componentes_recycler.DividerItemDecoration;
import me.adolfoquaranta.coletadigital.componentes_recycler.RecyclerItemClickListener;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.Modelo;

public class ListarFormularios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Formulario> formularioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FormulariosAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private Intent listarFormularios;

    private RelativeLayout mostrar_formularios_root;

    private String tipoFormulario;
    private Integer escolhaUsuario = 0, modelo_Modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_formularios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarFormularios);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        drawer.openDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        mostrar_formularios_root = (RelativeLayout) findViewById(R.id.mostrar_formularios_root);

        listarFormularios = getIntent();

        tipoFormulario = listarFormularios.getStringExtra("tipo_Formulario");

        (navigationView.getMenu()).findItem(R.id.itemDrawer_tipoFormulario).setTitle(tipoFormulario);


        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + tipoFormulario);

        dbAuxilar = new DBAuxilar(getApplicationContext());

        formularioList = dbAuxilar.lerTodosFormularios(tipoFormulario);

        if(formularioList.isEmpty()){
            RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(this);
            tv.setLayoutParams(lparams);
            String msg_naoExistemDados = getString(R.string.msg_naoExistemDados);
            tv.setText(msg_naoExistemDados);
            this.mostrar_formularios_root.addView(tv);
        }
        else{
            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarFormularios);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarFormularios);

            mAdapter = new FormulariosAdapter(formularioList);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarFormularios);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new OnItemClickListener()));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_adicionarFormulario);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastroFormulario = new Intent(ListarFormularios.this, CadastroFormulario.class);
                cadastroFormulario.putExtra("tipo_Formulario", tipoFormulario);
                startActivity(cadastroFormulario);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarFormularios);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        if (id == R.id.nav_formularioListarColetas) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_formularioEditar) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_formularioAddModelo) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_formularioNovaColeta) {
            escolhaUsuario = id;
        } else if (id == R.id.nav_formularioRemover) {
            escolhaUsuario = id;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarFormularios);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(View childView, final int position) {
            // Do something when an item is clicked, or override something else.

            ArrayList<Modelo> modelos = dbAuxilar.lerTodosModelos(formularioList.get(position).getId_Form());
            ArrayList<String> modelosExistentes = new ArrayList<>(4);

            if (escolhaUsuario == R.id.nav_formularioAddModelo) {
                modelosExistentes.add("DIC");
                modelosExistentes.add("DBC");
                modelosExistentes.add("FAT");
                modelosExistentes.add("SUB");

                for (Modelo m : modelos) {
                    if (m.getModelo_Modelo() == 0) {
                        modelosExistentes.remove(0);
                    }
                    if (m.getModelo_Modelo() == 1) {
                        modelosExistentes.remove(1);
                    }
                    if (m.getModelo_Modelo() == 2) {
                        modelosExistentes.remove(2);
                    }
                    if (m.getModelo_Modelo() == 3) {
                        modelosExistentes.remove(3);
                    }
                }
            } else {

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
            }


            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dialog_modelos, modelosExistentes);

            switch (escolhaUsuario) {
                case 0:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarFormularios);
                    assert drawer != null;
                    drawer.openDrawer(GravityCompat.START);
                    break;
                case R.id.nav_formularioListarColetas:
                    Intent listarColetas = new Intent(ListarFormularios.this, ListarColetas.class);
                    listarColetas.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                    listarColetas.putExtra("tipo_Formulario", formularioList.get(position).getTipo_Form());
                    listarColetas.putExtra("modelo_Modelo", modelo_Modelo);
                    startActivity(listarColetas);
                    break;
                case R.id.nav_formularioEditar:
                    AlertDialog.Builder builderEditar = new AlertDialog.Builder(ListarFormularios.this);
                    builderEditar.setTitle(R.string.dialog_modelo)
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
                                    Intent cadastroFormulario = new Intent(ListarFormularios.this, CadastroFormulario.class);
                                    cadastroFormulario.putExtra("tipo_Formulario", tipoFormulario);
                                    cadastroFormulario.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                                    cadastroFormulario.putExtra("modelo_Modelo", modelo_Modelo);
                                    cadastroFormulario.putExtra("acao", "editar");
                                    startActivity(cadastroFormulario);
                                }
                            }).create().show();
                    break;
                case R.id.nav_formularioAddModelo:
                    AlertDialog.Builder builderAddModelo = new AlertDialog.Builder(ListarFormularios.this);
                    builderAddModelo.setTitle(R.string.dialog_modelo)
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
                                    Intent cadastroModelo = new Intent(ListarFormularios.this, CadastroModeloFormulario.class);
                                    cadastroModelo.putExtra("tipo_Formulario", tipoFormulario);
                                    cadastroModelo.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                                    cadastroModelo.putExtra("modelo_Modelo", modelo_Modelo);
                                    startActivity(cadastroModelo);
                                }
                            }).create().show();
                    break;
                case R.id.nav_formularioNovaColeta:
                    AlertDialog.Builder builderNovaColeta = new AlertDialog.Builder(ListarFormularios.this);
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
                                    Intent cadastroColeta = new Intent(ListarFormularios.this, CadastroColeta.class);
                                    cadastroColeta.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                                    cadastroColeta.putExtra("tipo_Formulario", tipoFormulario);
                                    cadastroColeta.putExtra("modelo_Modelo", modelo_Modelo);
                                    startActivity(cadastroColeta);
                                }
                            }).create().show();

                    break;
                case R.id.nav_formularioRemover:
                    AlertDialog.Builder builderRemover = new AlertDialog.Builder(ListarFormularios.this);
                    builderRemover.setTitle(R.string.dialog_removerFormulario)
                            .setMessage(R.string.dialog_acaoPermanente)
                            .setPositiveButton(R.string.dialog_remover, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dbAuxilar.deleteFormulario(formularioList.get(position).getId_Form());
                                    Intent recarregar = new Intent(ListarFormularios.this, ListarFormularios.class);
                                    recarregar.putExtra("tipo_Formulario", tipoFormulario);
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
                    Toast.makeText(ListarFormularios.this, getString(R.string.info_EmBreve), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
