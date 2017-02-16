package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import me.adolfoquaranta.ef2.R;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    //Botões
    private Button inicio_btnEXE, inicio_btnINV, inicio_btnFITO, inicio_btnSOLOS, inicio_btnFOLHA,
            inicio_btnINS;

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

        inicio_btnEXE.setOnClickListener(this);
        inicio_btnINV.setOnClickListener(this);
        inicio_btnFITO.setOnClickListener(this);
        inicio_btnSOLOS.setOnClickListener(this);
        inicio_btnFOLHA.setOnClickListener(this);
        inicio_btnINS.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Button tipoForm = (Button) v;
        Intent mostrarFormularios = new Intent(Inicio.this, ListarFormularios.class);
        mostrarFormularios.putExtra("tipo_Formulario", tipoForm.getText().toString());
        startActivity(mostrarFormularios);
    }
}
