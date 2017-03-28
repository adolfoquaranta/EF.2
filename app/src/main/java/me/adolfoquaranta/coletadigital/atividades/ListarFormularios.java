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

public class ListarFormularios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Formulario> formularioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FormulariosAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private Intent listarFormularios;

    private RelativeLayout mostrar_formularios_root;

    private String tipoFormulario;
    private NavigationView navigationView;
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


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        mostrar_formularios_root = (RelativeLayout) findViewById(R.id.mostrar_formularios_root);

        listarFormularios = getIntent();

        tipoFormulario = listarFormularios.getStringExtra("tipo_Formulario");

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String escolhaUsuario = item.getTitle().toString();

        if (dbAuxilar.lerTodosFormularios(escolhaUsuario).size() == 0) {
            Intent cadastroFormulario = new Intent(ListarFormularios.this, CadastroFormulario.class);
            cadastroFormulario.putExtra("tipo_Formulario", escolhaUsuario);
            finish();
            startActivity(cadastroFormulario);
        } else {
            Intent mostrarFormularios = new Intent(ListarFormularios.this, ListarFormularios.class);
            mostrarFormularios.putExtra("tipo_Formulario", escolhaUsuario);
            finish();
            startActivity(mostrarFormularios);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ListarFormularios.this);
            builder.setTitle(formularioList.get(position).getNome_Formulario())
                    .setItems(R.array.array_opcoes_formularios, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch (which) {
                                case 0:
                                    Intent listarColetas = new Intent(ListarFormularios.this, ListarColetas.class);
                                    listarColetas.putExtra("id_Formulario", formularioList.get(position).getId_Formulario());
                                    listarColetas.putExtra("tipo_Formulario", formularioList.get(position).getTipo_Formulario());
                                    startActivity(listarColetas);
                                    break;
                                case 1:
                                    Intent cadastroColeta = new Intent(ListarFormularios.this, CadastroColeta.class);
                                    cadastroColeta.putExtra("id_Formulario", formularioList.get(position).getId_Formulario());
                                    cadastroColeta.putExtra("tipo_Formulario", tipoFormulario);
                                    startActivity(cadastroColeta);
                                    break;
                                case 2:
                                    AlertDialog.Builder builderRemover = new AlertDialog.Builder(ListarFormularios.this);
                                    builderRemover.setTitle(R.string.dialog_removerFormulario)
                                            .setMessage(R.string.dialog_acaoPermanente)
                                            .setPositiveButton(R.string.dialog_remover, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dbAuxilar.deleteFormulario(formularioList.get(position).getId_Formulario());
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
                    }).create().show();
        }

    }
}
