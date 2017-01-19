package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Tratamento;

import com.rengwuxian.materialedittext.MaterialEditText;

public class CadastroTratamentos extends AppCompatActivity {
    private ListView list_viewTratamentos;
    //private TratamentosListAdapter tratamentosListAdapter;
    private DIC dic;
    private String[] nomeTratamentosLista;
    private Integer[] tipoTratamentosLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tratamentos);
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


        Intent cadastroTratamentos = getIntent();
        Long id_DIC = cadastroTratamentos.getLongExtra("id_DIC", 0);
        Long idFormulario_DIC = cadastroTratamentos.getLongExtra("idFormulario_DIC", 0);

        DBAuxilar dbauxiliar = new DBAuxilar(getApplicationContext());

        dic = dbauxiliar.lerDICdoFormulario(idFormulario_DIC);

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_Tratamentos);

        for (int i = 0; i < dic.getQuantidadeTratamentos_DIC(); i++) {
            MaterialEditText myEditText = new MaterialEditText(CadastroTratamentos.this); // Pass it an Activity or Context
            myEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            myEditText.setId(i);
            myEditText.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            myEditText.setHint(getString(R.string.hint_nomeTratamento)+" "+(i+1));
            myEditText.setFloatingLabelText(getString(R.string.hint_nomeTratamento) + " " + (i + 1));
            myEditText.setFloatingLabelAnimating(true);
            myLayout.addView(myEditText);
        }
    }

}


