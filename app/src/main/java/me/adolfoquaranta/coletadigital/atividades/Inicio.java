package me.adolfoquaranta.coletadigital.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    //Botões
    private Button inicio_btnEXP, inicio_btnINV, inicio_btnFITO, inicio_btnSOLOS, inicio_btnFOLHA,
            inicio_btnINS;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        inicio_btnEXP = (Button) findViewById(R.id.inicio_btnEXP);
        inicio_btnINV = (Button) findViewById(R.id.inicio_btnINV);
        inicio_btnFITO = (Button) findViewById(R.id.inicio_btnFITO);
        inicio_btnSOLOS = (Button) findViewById(R.id.inicio_btnSOLOS);
        inicio_btnFOLHA = (Button) findViewById(R.id.inicio_btnFOLHA);
        inicio_btnINS = (Button) findViewById(R.id.inicio_btnINS);

        inicio_btnEXP.setOnClickListener(this);
        inicio_btnINV.setOnClickListener(this);
        inicio_btnFITO.setOnClickListener(this);
        inicio_btnSOLOS.setOnClickListener(this);
        inicio_btnFOLHA.setOnClickListener(this);
        inicio_btnINS.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Button tipoForm = (Button) v;
        DBAuxilar dbAuxilar = new DBAuxilar(getApplicationContext());
        if (v != inicio_btnEXP) {
            Toast.makeText(this, R.string.info_EmBreve, Toast.LENGTH_SHORT).show();
            return;
        }
        if (dbAuxilar.lerTodosFormularios(((Button) v).getText().toString()).size() == 0) {
            Intent cadastroFormulario = new Intent(Inicio.this, CadastroFormulario.class);
            cadastroFormulario.putExtra("tipo_Formulario", tipoForm.getText().toString());
            startActivity(cadastroFormulario);
        } else {
            Intent mostrarFormularios = new Intent(Inicio.this, ListarFormularios.class);
            mostrarFormularios.putExtra("tipo_Formulario", tipoForm.getText().toString());
            startActivity(mostrarFormularios);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, getString(R.string.info_SairApp), Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
