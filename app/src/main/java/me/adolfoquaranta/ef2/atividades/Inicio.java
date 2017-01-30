package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.adolfoquaranta.ef2.R;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Variaveis
    private Integer opcaoMenu_Usuario = R.id.nav_formularioNovo;

    //Botões
    private Button inicio_btnEXE, inicio_btnINV, inicio_btnFITO, inicio_btnSOLOS, inicio_btnFOLHA,
            inicio_btnINS;

    //drawer
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicio_btnEXE = (Button) findViewById(R.id.inicio_btnEXE);
        inicio_btnINV = (Button) findViewById(R.id.inicio_btnINV);
        inicio_btnFITO = (Button) findViewById(R.id.inicio_btnFITO);
        inicio_btnSOLOS = (Button) findViewById(R.id.inicio_btnSOLOS);
        inicio_btnFOLHA = (Button) findViewById(R.id.inicio_btnFOLHA);
        inicio_btnINS = (Button) findViewById(R.id.inicio_btnINS);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        inicio_btnEXE.setOnClickListener(this);
        inicio_btnINV.setOnClickListener(this);
        inicio_btnFITO.setOnClickListener(this);
        inicio_btnSOLOS.setOnClickListener(this);
        inicio_btnFOLHA.setOnClickListener(this);
        inicio_btnINS.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
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
        opcaoMenu_Usuario = id;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Button tipoForm = (Button) v;
        switch (opcaoMenu_Usuario){
            case R.id.nav_formularioNovo:
                Intent cadastroFormulario = new Intent(Inicio.this, CadastroFormulario.class);
                cadastroFormulario.putExtra("tipo_Formulario", tipoForm.getText().toString());
                startActivity(cadastroFormulario);
                break;
            case R.id.nav_coletaContinuar:
                Intent mostrarFormularios = new Intent(Inicio.this, ListarFormularios.class);
                mostrarFormularios.putExtra("tipo_Formulario", tipoForm.getText().toString());
                startActivity(mostrarFormularios);
                break;
            default:
                Toast.makeText(this, R.string.info_EmBreve, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
