package me.adolfoquaranta.ef2.atividades;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class ListarFormularios extends AppCompatActivity {
    private List<Formulario> formularioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FormulariosAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private Intent mostrarFormularios;

    private RelativeLayout mostrar_formularios_root;

    private String modelo_Form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_formularios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mostrar_formularios_root = (RelativeLayout) findViewById(R.id.mostrar_formularios_root);

        mostrarFormularios = getIntent();

        modelo_Form = mostrarFormularios.getSerializableExtra("modelo_Form").toString();

        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + modelo_Form);

        dbAuxilar = new DBAuxilar(getApplicationContext());
        formularioList = new ArrayList<Formulario>(dbAuxilar.lerTodosFormularios(modelo_Form));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


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

            mAdapter.notifyDataSetChanged();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new OnItemClickListener()));
        }

    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(View childView, int position) {
            // Do something when an item is clicked, or override something else.
            Toast.makeText(ListarFormularios.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        }

    }

}
