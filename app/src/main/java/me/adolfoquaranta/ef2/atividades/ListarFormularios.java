package me.adolfoquaranta.ef2.atividades;

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

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.adaptadores.FormulariosAdapter;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.componentes_recycler.DividerItemDecoration;
import me.adolfoquaranta.ef2.componentes_recycler.RecyclerItemClickListener;
import me.adolfoquaranta.ef2.modelos.Formulario;

public class ListarFormularios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Formulario> formularioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FormulariosAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private Intent listarFormularios;

    private RelativeLayout mostrar_formularios_root;

    private String tipoFormulario;
    private Integer escolhaUsuario = 0;

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
            Intent cadastroColeta = new Intent(ListarFormularios.this, CadastroColeta.class);
            Intent listarColetas = new Intent(ListarFormularios.this, ListarColetas.class);
            Intent cadastroFormulario = new Intent(ListarFormularios.this, CadastroFormulario.class);
            switch (escolhaUsuario) {
                case 0:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutListarFormularios);
                    assert drawer != null;
                    drawer.openDrawer(GravityCompat.START);
                    break;
                case R.id.nav_formularioListarColetas:
                    listarColetas.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                    listarColetas.putExtra("tipo_Formulario", formularioList.get(position).getTipo_Form());
                    startActivity(listarColetas);
                    break;
                case R.id.nav_formularioEditar:
                    cadastroFormulario.putExtra("tipo_Formulario", tipoFormulario);
                    cadastroFormulario.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                    cadastroFormulario.putExtra("acao", "editar");
                    startActivity(cadastroFormulario);
                    break;
                case R.id.nav_formularioAddModelo:
                    cadastroFormulario.putExtra("tipo_Formulario", tipoFormulario);
                    cadastroFormulario.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                    cadastroFormulario.putExtra("acao", "cadastro");
                    startActivity(cadastroFormulario);
                    break;
                case R.id.nav_formularioNovaColeta:
                    cadastroColeta.putExtra("id_Formulario", formularioList.get(position).getId_Form());
                    cadastroColeta.putExtra("tipo_Formulario", tipoFormulario);
                    startActivity(cadastroColeta);
                    break;
                case R.id.nav_formularioRemover:

                    AlertDialog.Builder builder = new AlertDialog.Builder(ListarFormularios.this);
                    builder.setTitle(R.string.dialog_removerFormulario)
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
