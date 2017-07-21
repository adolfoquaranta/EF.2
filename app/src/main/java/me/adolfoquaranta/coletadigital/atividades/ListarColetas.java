package me.adolfoquaranta.coletadigital.atividades;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.adolfoquaranta.coletadigital.R;
import me.adolfoquaranta.coletadigital.adaptadores.ColetasAdapter;
import me.adolfoquaranta.coletadigital.auxiliares.CSVAuxiliar;
import me.adolfoquaranta.coletadigital.auxiliares.DBAuxilar;
import me.adolfoquaranta.coletadigital.componentes_recycler.DividerItemDecoration;
import me.adolfoquaranta.coletadigital.componentes_recycler.RecyclerItemClickListener;
import me.adolfoquaranta.coletadigital.modelos.Coleta;

public class ListarColetas extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x1;
    private Boolean WRITE_EXTERNAL_STORAGE_OK = true;
    private List<Coleta> coletasList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ColetasAdapter mAdapter;
    private DBAuxilar dbAuxilar;

    private RelativeLayout mostrar_coletas_root;

    private Long id_Formulario;
    private String tipo_Formulario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_coletas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mostrar_coletas_root = (RelativeLayout) findViewById(R.id.mostrar_coletas_root);

        Intent listarColetas = getIntent();
        id_Formulario = listarColetas.getLongExtra("id_Formulario", 0);
        tipo_Formulario = listarColetas.getStringExtra("tipo_Formulario");

        dbAuxilar = new DBAuxilar(getApplicationContext());

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getSupportActionBar().getTitle().toString() + " " + dbAuxilar.lerFormulario(id_Formulario).getNome_Formulario());


        coletasList = dbAuxilar.lerTodasColetas(id_Formulario);

        if (coletasList.isEmpty()) {
            RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(this);
            tv.setLayoutParams(lparams);
            String msg_naoExistemDados = getString(R.string.msg_naoExistemDados);
            tv.setText(msg_naoExistemDados);
            this.mostrar_coletas_root.addView(tv);
        } else {

            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarColetas);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarColetas);

            mAdapter = new ColetasAdapter(coletasList);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_viewListarColetas);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new ListarColetas.OnItemClickListener()));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_novaColeta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastroColeta = new Intent(ListarColetas.this, CadastroColeta.class);
                cadastroColeta.putExtra("id_Formulario", id_Formulario);
                cadastroColeta.putExtra("tipo_Formulario", tipo_Formulario);
                finish();
                startActivity(cadastroColeta);
            }
        });


    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(ListarColetas.this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ListarColetas.this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(ListarColetas.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(ListarColetas.this, new String[]{permission}, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    WRITE_EXTERNAL_STORAGE_OK = true;
                    Toast.makeText(this, "Por favor, escolha a coleta que deseja e toque em exportar.", Toast.LENGTH_LONG).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    WRITE_EXTERNAL_STORAGE_OK = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(final View childView, final int position) {
            // Do something when an item is clicked, or override something else.
            AlertDialog.Builder builder = new AlertDialog.Builder(ListarColetas.this);
            builder.setTitle(coletasList.get(position).getNome_Coleta())
                    .setItems(R.array.array_opcoes_coletas, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            switch (which) {
                                case 0:
                                    askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                                    if (WRITE_EXTERNAL_STORAGE_OK) {
                                        CSVAuxiliar csvAuxiliar = new CSVAuxiliar(id_Formulario, coletasList.get(position).getId_Coleta(), getApplicationContext());
                                        final String filePath = csvAuxiliar.exportarCSV(dbAuxilar.lerFormulario(id_Formulario).getModelo_Formulario());
                                        if (!filePath.equals("")) {
                                            Toast.makeText(ListarColetas.this, "Arquivo Exportado", Toast.LENGTH_SHORT).show();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ListarColetas.this);
                                            builder.setTitle(R.string.dialog_compartilharArquivo)
                                                    .setMessage(R.string.dialog_compartilharMensagem)
                                                    .setPositiveButton(R.string.dialog_sim, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                            sharingIntent.setType("text/plain");
                                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                                                            startActivity(Intent.createChooser(sharingIntent, getString(R.string.info_CompartilharUsando)));
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.dialog_nao, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            // User cancelled the dialog
                                                        }
                                                    }).create().show();
                                        } else
                                            Toast.makeText(ListarColetas.this, "Arquivo não Exportado", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(ListarColetas.this, "Para exportar o arquivo é necessário autorizar gravação de dados no armazenamento interno", Toast.LENGTH_LONG).show();
                                    break;
                                case 1:
                                    if (dbAuxilar.lerFormulario(coletasList.get(position).getIdFormulario_Coleta()).getModelo_Formulario() == 3) {
                                        Intent coletarDadosParcela = new Intent(ListarColetas.this, ColetarDadosParcela.class);
                                        coletarDadosParcela.putExtra("id_Formulario", id_Formulario);
                                        coletarDadosParcela.putExtra("id_Coleta", coletasList.get(position).getId_Coleta());
                                        if (!coletasList.get(position).getStatus_Coleta().equals("ok") &&
                                                !coletasList.get(position).getStatus_Coleta().equals("")) {
                                            String[] posicaoColeta = coletasList.get(position).getStatus_Coleta().split(",");
                                            coletarDadosParcela.putExtra("parcelaAtual", Integer.valueOf(posicaoColeta[0]));
                                            coletarDadosParcela.putExtra("nivelFatorAtual", Integer.valueOf(posicaoColeta[1]));
                                            coletarDadosParcela.putExtra("replicacaoAtual", Integer.valueOf(posicaoColeta[2]));
                                            coletarDadosParcela.putExtra("repeticaoAtual", Integer.valueOf(posicaoColeta[3]));
                                            coletarDadosParcela.putExtra("blocoAtual", Integer.valueOf(posicaoColeta[4]));
                                        }
                                        finish();
                                        startActivity(coletarDadosParcela);
                                    }
                                    if (dbAuxilar.lerFormulario(coletasList.get(position).getIdFormulario_Coleta()).getModelo_Formulario() == 2) {
                                        Intent coletarDadosFatorial = new Intent(ListarColetas.this, ColetarDadosFatorial.class);
                                        coletarDadosFatorial.putExtra("id_Formulario", id_Formulario);
                                        coletarDadosFatorial.putExtra("id_Coleta", coletasList.get(position).getId_Coleta());
                                        if (!coletasList.get(position).getStatus_Coleta().equals("ok") &&
                                                !coletasList.get(position).getStatus_Coleta().equals("")) {
                                            String[] posicaoColeta = coletasList.get(position).getStatus_Coleta().split(",");
                                            coletarDadosFatorial.putExtra("fatorAtual", Integer.valueOf(posicaoColeta[0]));
                                            coletarDadosFatorial.putExtra("nivelFatorAtual", Integer.valueOf(posicaoColeta[1]));
                                            coletarDadosFatorial.putExtra("replicacaoAtual", Integer.valueOf(posicaoColeta[2]));
                                            coletarDadosFatorial.putExtra("repeticaoAtual", Integer.valueOf(posicaoColeta[3]));
                                            coletarDadosFatorial.putExtra("blocoAtual", Integer.valueOf(posicaoColeta[4]));
                                        }
                                        finish();
                                        startActivity(coletarDadosFatorial);
                                    } else {
                                        Intent coletarDados = new Intent(ListarColetas.this, ColetarDados.class);
                                        coletarDados.putExtra("id_Formulario", id_Formulario);
                                        coletarDados.putExtra("id_Coleta", coletasList.get(position).getId_Coleta());
                                        if (!coletasList.get(position).getStatus_Coleta().equals("ok") &&
                                                !coletasList.get(position).getStatus_Coleta().equals("")) {
                                            String[] posicaoColeta = coletasList.get(position).getStatus_Coleta().split(",");
                                            coletarDados.putExtra("tratamentoAtual", Integer.valueOf(posicaoColeta[0]));
                                            coletarDados.putExtra("replicacaoAtual", Integer.valueOf(posicaoColeta[1]));
                                            coletarDados.putExtra("repeticaoAtual", Integer.valueOf(posicaoColeta[2]));
                                            coletarDados.putExtra("blocoAtual", Integer.valueOf(posicaoColeta[3]));
                                        }
                                        finish();
                                        startActivity(coletarDados);
                                    }
                                    break;
                                case 2:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ListarColetas.this);
                                    builder.setTitle(R.string.dialog_removerColeta)
                                            .setMessage(R.string.dialog_acaoPermanente)
                                            .setPositiveButton(R.string.dialog_remover, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Log.d("idColeta", coletasList.get(position).getId_Coleta().toString());
                                                    dbAuxilar.deleteColeta(coletasList.get(position).getId_Coleta());
                                                    Intent recarregar = new Intent(ListarColetas.this, ListarColetas.class);
                                                    recarregar.putExtra("tipo_Formulario", tipo_Formulario);
                                                    recarregar.putExtra("id_Formulario", id_Formulario);
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
                                    Toast.makeText(ListarColetas.this, getString(R.string.info_EmBreve), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }).create().show();
        }

    }
}
