package me.adolfoquaranta.ef2.atividades;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.adolfoquaranta.ef2.componentes_recycler.DividerItemDecoration;
import me.adolfoquaranta.ef2.adaptadores.FormulariosAdapter;
import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.modelos.Formulario;

public class Formularios extends AppCompatActivity {
    private List<Formulario> formularioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FormulariosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios);
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new FormulariosAdapter(formularioList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        prepareFormularioData();
    }



    private void prepareFormularioData() {
        Date data = new Date();

        Formulario movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        movie = new Formulario(null,"Mad Max: Fury Road", "Action & Adventure", "Mad Max: Fury Road", "Action & Adventure", data);
        formularioList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

}
