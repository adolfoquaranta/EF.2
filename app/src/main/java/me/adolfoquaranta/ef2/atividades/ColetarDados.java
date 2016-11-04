package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Tratamento;
import me.adolfoquaranta.ef2.modelos.Variavel;

public class ColetarDados extends AppCompatActivity {
    private DBAuxilar dbAuxilar;
    private List<Tratamento> tratamentos;
    private List<Variavel> variaveis;
    private DIC dic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        dbAuxilar = new DBAuxilar(getApplicationContext());

        Intent coletarDados = getIntent();
        Long id_Form = coletarDados.getLongExtra("id_Form", 0);
        Long id_Coleta = coletarDados.getLongExtra("id_Coleta", 0);

        dic = dbAuxilar.lerDIC(id_Form);
        tratamentos = dbAuxilar.lerTodosTratamentos(id_Form);
        variaveis = dbAuxilar.lerTodasVariaveis(id_Form);


        Log.d("id_Coleta", String.valueOf(id_Coleta));

        Log.d("dic", dic.toString());

        for (int i=0; i<tratamentos.size(); i++){
            Log.d("Tratamento "+ i +" Nome", tratamentos.get(i).getNome_Tratamento());
            Log.d("Tratamento "+ i +" Tipo", String.valueOf(tratamentos.get(i).getTipo_Tratamento()));
        }

        for (int i=0; i<variaveis.size(); i++){
            Log.d("Variavel "+ i +" Nome", variaveis.get(i).getNome_Variavel());
            Log.d("Variavel "+ i +" Tipo", String.valueOf(variaveis.get(i).getTipo_Variavel()));
        }




    }

}
