package me.adolfoquaranta.ef2.auxiliares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Formulario;

/**
 * Created by adolfo on 13/10/16.
 */

public class DBAuxilar extends SQLiteOpenHelper {

    public DBAuxilar(Context context) {
        super(context, DATABASE_NOME , null, 1);
    }

    private static final String LOG = "DBAuxiliar";

    public static final String DATABASE_NOME = "ef2.db";

    //
    public static final String FORMULARIO_TABELA = "Formulario";

    public static final String FORMULARIO_COL_ID = "id_Form";
    public static final String FORMULARIO_COL_MODELO = "modelo_Form";
    public static final String FORMULARIO_COL_NOME = "nome_Form";
    public static final String FORMULARIO_COL_DESCRICAO = "descricao_Form";
    public static final String FORMULARIO_COL_CRIADOR = "criador_Form";
    public static final String FORMULARIO_COL_DATA_CRIACAO = "dataCriacao_Form";
    public static final String FORMULARIO_COL_STATUS = "status_Form";


    public static final String DIC_TABELA = "DIC";

    public static final String DIC_COL_ID = "id_DIC";
    public static final String DIC_COL_QUANTIDADE_TRATAMENTOS = "quantidadeTratamentos_DIC";
    public static final String DIC_COL_QUANTIDADE_REPETICOES = "quantidadeRepeticoes_DIC";
    public static final String DIC_COL_QUANTIDADE_REPLICACOES = "quantidadeReplicacoes_DIC";
    public static final String DIC_COL_QUANTIDADE_VARIAVEIS = "quantidadeVariaveis_DIC";
    public static final String DIC_COL_ID_FORMULARIO_DIC = "idFormulario_DIC";
    public static final String DIC_CONSTRAINT_FK_DIC_FORMULARIO = "FK_DIC_Formulario";


    private static final String CRIAR_TABELA_FORMULARIO = "CREATE TABLE "
            + FORMULARIO_TABELA + "(" + FORMULARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + FORMULARIO_COL_MODELO + " TEXT NOT NULL, "
            + FORMULARIO_COL_NOME + " TEXT, "
            + FORMULARIO_COL_DESCRICAO + " TEXT, "
            + FORMULARIO_COL_CRIADOR + " TEXT, "
            + FORMULARIO_COL_DATA_CRIACAO + " TEXT, "
            + FORMULARIO_COL_STATUS + " TEXT "
            + ")";


    private static final String CRIAR_TABELA_DIC = "CREATE TABLE "
            + DIC_TABELA + "(" + DIC_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + DIC_COL_QUANTIDADE_TRATAMENTOS + " INTEGER, "
            + DIC_COL_QUANTIDADE_REPETICOES + " INTEGER, "
            + DIC_COL_QUANTIDADE_REPLICACOES + " INTEGER, "
            + DIC_COL_QUANTIDADE_VARIAVEIS + " INTEGER, "
            + DIC_COL_ID_FORMULARIO_DIC + " INTEGER, "
            + "CONSTRAINT '" + DIC_CONSTRAINT_FK_DIC_FORMULARIO +"' FOREIGN KEY ('"+ DIC_COL_ID_FORMULARIO_DIC +"') REFERENCES "+ FORMULARIO_TABELA + " ('"+ FORMULARIO_COL_ID + "'), "
            + "UNIQUE " + "('"+ DIC_COL_ID +"') "
            + ")";




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA_FORMULARIO);
        db.execSQL(CRIAR_TABELA_DIC);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DIC_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + FORMULARIO_TABELA);
    }

    //Formulario
    public Long inserirFormulario(Formulario formulario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FORMULARIO_COL_MODELO, formulario.getModelo_Form());
        values.put(FORMULARIO_COL_NOME, formulario.getNome_Form());
        values.put(FORMULARIO_COL_DESCRICAO, formulario.getDescricao_Form());
        values.put(FORMULARIO_COL_CRIADOR, formulario.getCriador_Form());
        values.put(FORMULARIO_COL_DATA_CRIACAO, formulario.getDataCriacao_Form());
        values.put(FORMULARIO_COL_STATUS, formulario.getStatus_Form());


        return db.insert(FORMULARIO_TABELA, null, values);
    }

    public Formulario lerFormulario(Long id_Form){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + FORMULARIO_TABELA + " WHERE " + FORMULARIO_COL_ID + " = " + id_Form;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) c.moveToFirst();

        Formulario formulario = new Formulario();
        formulario.setId_Form(c.getLong(c.getColumnIndex(FORMULARIO_COL_ID)));
        formulario.setModelo_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_MODELO)));
        formulario.setNome_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_NOME)));
        formulario.setDescricao_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_DESCRICAO)));
        formulario.setCriador_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_CRIADOR)));
        formulario.setDataCriacao_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_DATA_CRIACAO)));
        formulario.setStatus_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_STATUS)));

        return formulario;
    }


    //ler todos os formularios
    public List<Formulario> lerTodosFormularios(String modelo_Form){
        List<Formulario> formularios = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + FORMULARIO_TABELA + " WHERE " + FORMULARIO_COL_MODELO + " = '" + modelo_Form + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Formulario formulario = new Formulario();
                formulario.setId_Form(c.getLong(c.getColumnIndex(FORMULARIO_COL_ID)));
                formulario.setModelo_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_MODELO)));
                formulario.setNome_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_NOME)));
                formulario.setDescricao_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_DESCRICAO)));
                formulario.setCriador_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_CRIADOR)));
                formulario.setDataCriacao_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_DATA_CRIACAO)));
                formulario.setStatus_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_STATUS)));
                formularios.add(formulario);

            }while (c.moveToNext());
        }

        return formularios;
    }


    //DIC
    public Long inserirDIC(DIC dic){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DIC_COL_QUANTIDADE_TRATAMENTOS, dic.getQuantidadeTratamentos_DIC());
        values.put(DIC_COL_QUANTIDADE_REPETICOES, dic.getQuantidadeRepeticoes_DIC());
        values.put(DIC_COL_QUANTIDADE_REPLICACOES, dic.getQuantidadeReplicacoes_DIC());
        values.put(DIC_COL_QUANTIDADE_VARIAVEIS, dic.getQuantidadeVariaveis_DIC());
        values.put(DIC_COL_ID_FORMULARIO_DIC, dic.getIdFormulario_DIC());


        return db.insert(DIC_TABELA, null, values);
    }

    public DIC lerDIC(Long id_DIC, Long idFormulario_DIC){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DIC_TABELA + " WHERE " + DIC_COL_ID + " = " + id_DIC + " JOIN " + FORMULARIO_TABELA + " ON " + FORMULARIO_COL_ID +" = " + idFormulario_DIC;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) c.moveToFirst();

        DIC dic = new DIC();
        dic.setId_DIC(c.getLong(c.getColumnIndex(DIC_COL_ID)));
        dic.setQuantidadeTratamentos_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_TRATAMENTOS)));
        dic.setQuantidadeRepeticoes_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_REPETICOES)));
        dic.setQuantidadeReplicacoes_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_REPLICACOES)));
        dic.setQuantidadeVariaveis_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_VARIAVEIS)));
        dic.setIdFormulario_DIC(c.getLong(c.getColumnIndex(DIC_COL_ID_FORMULARIO_DIC)));

        return dic;
    }


    //ler todos os DICs
    public List<DIC> lerTodosDICs(String modelo_Form, Long idFormulario_DIC){
        List<DIC> dics = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DIC_TABELA + " WHERE " + FORMULARIO_COL_MODELO + " = " + modelo_Form + " JOIN " + FORMULARIO_TABELA + " ON " + FORMULARIO_COL_ID +" = " + idFormulario_DIC;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                DIC dic = new DIC();
                dic.setId_Form(c.getLong(c.getColumnIndex(FORMULARIO_COL_ID)));
                dic.setModelo_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_MODELO)));
                dic.setNome_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_NOME)));
                dic.setDescricao_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_DESCRICAO)));
                dic.setCriador_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_CRIADOR)));
                dic.setDataCriacao_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_DATA_CRIACAO)));
                dic.setStatus_Form(c.getString(c.getColumnIndex(FORMULARIO_COL_STATUS)));
                dic.setId_DIC(c.getLong(c.getColumnIndex(DIC_COL_ID)));
                dic.setQuantidadeTratamentos_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_TRATAMENTOS)));
                dic.setQuantidadeRepeticoes_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_REPETICOES)));
                dic.setQuantidadeReplicacoes_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_REPLICACOES)));
                dic.setQuantidadeVariaveis_DIC(c.getInt(c.getColumnIndex(DIC_COL_QUANTIDADE_VARIAVEIS)));
                dic.setIdFormulario_DIC(c.getLong(c.getColumnIndex(DIC_COL_ID_FORMULARIO_DIC)));

            }while (c.moveToNext());
        }

        return dics;
    }




}
