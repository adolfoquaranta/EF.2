package me.adolfoquaranta.ef2.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import fr.ganfra.materialspinner.MaterialSpinner;
import me.adolfoquaranta.ef2.R;
import me.adolfoquaranta.ef2.auxiliares.DBAuxilar;
import me.adolfoquaranta.ef2.modelos.DIC;
import com.rengwuxian.materialedittext.MaterialEditText;



public class CadastroTratamentos extends AppCompatActivity {


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

        DIC dic = dbauxiliar.lerDICdoFormulario(idFormulario_DIC);

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_Tratamentos);
        final Integer AuxiliarID = dic.getQuantidadeTratamentos_DIC();

        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);

        String[] opcoesSpinnerTipoTratamento = getResources().getStringArray(R.array.spinnerArray_tipoTratamento);


        View.OnFocusChangeListener validar = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v instanceof MaterialEditText){
                    MaterialEditText editText = (MaterialEditText) v;
                    editText.validate("^(?!\\s*$).+","Insira um nome");
                    if(v.hasFocus()){
                        editText.clearValidators();
                    }
                }
                if(v instanceof MaterialSpinner){
                    MaterialSpinner spinner = (MaterialSpinner) v;
                    if(spinner.getSelectedItemPosition()==0){
                        spinner.setError("Error");
                    }
                    else{
                        spinner.setError(null);
                    }
                }
            }
        };

        for (int i = 0; i < dic.getQuantidadeTratamentos_DIC(); i++) {
            LinearLayout layoutInterno= new LinearLayout(CadastroTratamentos.this);
            layoutInterno.setOrientation(LinearLayout.HORIZONTAL);
            layoutInterno.setWeightSum(3);

            //nome tratamento
            final MaterialEditText etNomeTratamento = new MaterialEditText(CadastroTratamentos.this); // Pass it an Activity or Context
            etNomeTratamento.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            etNomeTratamento.setPaddings(0,padding_5dp,0,0);
            etNomeTratamento.setId(i);
            etNomeTratamento.setOnFocusChangeListener(validar);
            etNomeTratamento.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
            etNomeTratamento.setHint(getString(R.string.hint_nomeTratamento)+" "+(i+1));
            etNomeTratamento.setFloatingLabelText(getString(R.string.hint_nomeTratamento) + " " + (i + 1));
            etNomeTratamento.setFloatingLabelAnimating(true);
            layoutInterno.addView(etNomeTratamento);

            //tipo tratamento
            MaterialSpinner spTipoTratamento = new MaterialSpinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesSpinnerTipoTratamento);
            spTipoTratamento.setAdapter(spinnerArrayAdapter);
            spTipoTratamento.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
            spTipoTratamento.setId((i+AuxiliarID));
            spTipoTratamento.setOnFocusChangeListener(validar);
            layoutInterno.addView(spTipoTratamento);
            myLayout.addView(layoutInterno);

        }



        Button btnSalvar_Tratamentos = (Button) findViewById(R.id.btn_salvar_Tratamentos);

        btnSalvar_Tratamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<AuxiliarID; i++){
                    MaterialEditText met = (MaterialEditText) findViewById(i);
                    Log.d("Nome Trat"+(i+1), met.getText().toString());
                    MaterialSpinner sp = (MaterialSpinner) findViewById((i+AuxiliarID));
                    Log.d("Tipo Trat"+(i+1), String.valueOf(sp.getSelectedItemPosition()));
                }
            }
        });





    }




}


