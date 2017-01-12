package me.adolfoquaranta.ef2.auxiliares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.adolfoquaranta.ef2.modelos.Coleta;
import me.adolfoquaranta.ef2.modelos.DIC;
import me.adolfoquaranta.ef2.modelos.Dado;
import me.adolfoquaranta.ef2.modelos.Formulario;
import me.adolfoquaranta.ef2.modelos.Tratamento;
import me.adolfoquaranta.ef2.modelos.Variavel;

/**
 * Created by adolfo on 13/10/16.
 */

public class DBAuxilar extends SQLiteOpenHelper {

    public DBAuxilar(Context context) {
        super(context, DATABASE_NOME , null, 1);
    }

    private static final String LOG = "DBAuxiliar";

    private static final String DATABASE_NOME = "ef2.db";

    //
    private static final String FORMULARIO_TABELA = "Formulario";

    private static final String FORMULARIO_COL_ID = "id_Form";
    private static final String FORMULARIO_COL_MODELO = "modelo_Form";
    private static final String FORMULARIO_COL_NOME = "nome_Form";
    private static final String FORMULARIO_COL_DESCRICAO = "descricao_Form";
    private static final String FORMULARIO_COL_CRIADOR = "criador_Form";
    private static final String FORMULARIO_COL_DATA_CRIACAO = "dataCriacao_Form";
    private static final String FORMULARIO_COL_STATUS = "status_Form";


    private static final String DIC_TABELA = "DIC";

    private static final String DIC_COL_ID = "id_DIC";
    private static final String DIC_COL_QUANTIDADE_TRATAMENTOS = "quantidadeTratamentos_DIC";
    private static final String DIC_COL_QUANTIDADE_REPETICOES = "quantidadeRepeticoes_DIC";
    private static final String DIC_COL_QUANTIDADE_REPLICACOES = "quantidadeReplicacoes_DIC";
    private static final String DIC_COL_QUANTIDADE_VARIAVEIS = "quantidadeVariaveis_DIC";
    private static final String DIC_COL_ID_FORMULARIO = "idFormulario_DIC";
    private static final String DIC_CONSTRAINT_FK_DIC_FORMULARIO = "FK_DIC_Formulario";


    private static final String TRATAMENTO_TABELA = "Tratamento";

    private static final String TRATAMENTO_COL_ID = "id_Tratamento";
    private static final String TRATAMENTO_COL_NOME = "nome_Tratamento";
    private static final String TRATAMENTO_COL_TIPO = "tipo_Tratamento";
    private static final String TRATAMENTO_COL_ID_FORMULARIO = "idForm_Tratamento";
    private static final String TRATAMENTO_CONSTRAINT_FK_TRATAMENTO_FORMULARIO = "FK_DIC_Tratamento";


    private static final String VARIAVEL_TABELA = "Variavel";

    private static final String VARIAVEL_COL_ID = "id_Variavel";
    private static final String VARIAVEL_COL_NOME = "nome_Variavel";
    private static final String VARIAVEL_COL_TIPO = "tipo_Variavel";
    private static final String VARIAVEL_COL_ID_FORMULARIO = "idForm_Variavel";
    private static final String VARIAVEL_CONSTRAINT_FK_VARIAVEL_FORMULARIO = "FK_DIC_Variavel";


    private static final String COLETA_TABELA = "Coleta";

    private static final String COLETA_COL_ID = "id_Coleta";
    private static final String COLETA_COL_NOME = "nome_Coleta";
    private static final String COLETA_COL_DESCRICAO = "descricao_Coleta";
    private static final String COLETA_COL_DATA_CRIACAO = "dataCriacao_Coleta";
    private static final String COLETA_COL_DATA_ULTIMA_EDICAO = "dataUltimaEdicao_Coleta";
    private static final String COLETA_COL_STATUS = "status_Coleta";
    private static final String COLETA_COL_TIPO = "tipo_Coleta";
    private static final String COLETA_COL_MODELO_FORM = "modeloForm_Coleta";
    private static final String COLETA_COL_ID_FORMULARIO = "idForm_Coleta";
    private static final String COLETA_CONSTRAINT_FK_COLETA_FORMULARIO = "FK_Coleta_Formulario";


    private static final String DADO_TABELA = "Dado";

    private static final String DADO_COL_ID = "id_Dado";
    private static final String DADO_COL_VALOR = "valor_Dado";
    private static final String DADO_COL_REPETICAO = "repeticao_Dado";
    private static final String DADO_COL_REPLICACAO = "replicacao_Dado";
    private static final String DADO_COL_BLOCO = "bloco_Dado";
    private static final String DADO_COL_FATOR = "fator_Dado";
    private static final String DADO_COL_DIVISAO = "divisao_Dado";
    private static final String DADO_COL_NIVEL_FATOR = "nivelFator_Dado";
    private static final String DADO_COL_NIVEL_DIVISAO = "nivelDivisao_Dado";
    private static final String DADO_COL_ID_COLETA = "idColeta_Dado";
    private static final String DADO_COL_ID_TRATAMENTO = "idTratamento_Dado";
    private static final String DADO_COL_ID_VARIAVEL = "idVariavel_Dado";
    private static final String DADO_CONSTRAINT_FK_DADO_COLETA = "FK_Dado_Coleta";
    private static final String DADO_CONSTRAINT_FK_DADO_TRATAMENTO = "FK_Dado_Tratamento";
    private static final String DADO_CONSTRAINT_FK_DADO_VARIAVEL = "FK_Dado_Variavel";






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
            + DIC_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + DIC_CONSTRAINT_FK_DIC_FORMULARIO +"' FOREIGN KEY ('"+ DIC_COL_ID_FORMULARIO +"') REFERENCES "+ FORMULARIO_TABELA + " ('"+ FORMULARIO_COL_ID + "') "
            + ")";

    private static final String CRIAR_TABELA_TRATAMENTO = "CREATE TABLE "
            + TRATAMENTO_TABELA + "("+ TRATAMENTO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + TRATAMENTO_COL_NOME + " TEXT, "
            + TRATAMENTO_COL_TIPO + " INTEGER, "
            + TRATAMENTO_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + TRATAMENTO_CONSTRAINT_FK_TRATAMENTO_FORMULARIO +"' FOREIGN KEY ('"+ TRATAMENTO_COL_ID_FORMULARIO +"') REFERENCES "+ FORMULARIO_TABELA + " ('"+ FORMULARIO_COL_ID + "') "
            + ")";

    private static final String CRIAR_TABELA_VARIAVEL = "CREATE TABLE "
            + VARIAVEL_TABELA + "("+ VARIAVEL_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + VARIAVEL_COL_NOME + " TEXT, "
            + VARIAVEL_COL_TIPO + " INTEGER, "
            + VARIAVEL_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + VARIAVEL_CONSTRAINT_FK_VARIAVEL_FORMULARIO +"' FOREIGN KEY ('"+ VARIAVEL_COL_ID_FORMULARIO +"') REFERENCES "+ FORMULARIO_TABELA + " ('"+ FORMULARIO_COL_ID + "') "
            + ")";

    private static final String CRIAR_TABELA_COLETA = "CREATE TABLE "
            + COLETA_TABELA + "(" + COLETA_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLETA_COL_NOME + " TEXT NOT NULL, "
            + COLETA_COL_DESCRICAO + " TEXT, "
            + COLETA_COL_DATA_CRIACAO + " TEXT, "
            + COLETA_COL_DATA_ULTIMA_EDICAO + " TEXT, "
            + COLETA_COL_STATUS + " TEXT, "
            + COLETA_COL_TIPO + " TEXT, "
            + COLETA_COL_MODELO_FORM + " TEXT, "
            + COLETA_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + COLETA_CONSTRAINT_FK_COLETA_FORMULARIO +"' FOREIGN KEY ('"+ COLETA_COL_ID_FORMULARIO +"') REFERENCES "+ FORMULARIO_TABELA + " ('"+ FORMULARIO_COL_ID + "') "
            + ")";

    private static final String CRIAR_TABELA_DADO = "CREATE TABLE "
            + DADO_TABELA + "(" + DADO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + DADO_COL_VALOR + " TEXT, "
            + DADO_COL_REPETICAO + " INTEGER, "
            + DADO_COL_REPLICACAO + " INTEGER, "
            + DADO_COL_BLOCO + " INTEGER, "
            + DADO_COL_FATOR + " INTEGER, "
            + DADO_COL_DIVISAO + " INTEGER, "
            + DADO_COL_NIVEL_FATOR + " INTEGER, "
            + DADO_COL_NIVEL_DIVISAO + " INTEGER, "
            + DADO_COL_ID_COLETA + " INTEGER, "
            + DADO_COL_ID_TRATAMENTO + " INTEGER, "
            + DADO_COL_ID_VARIAVEL + " INTEGER, "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_COLETA +"' FOREIGN KEY ('"+ DADO_COL_ID_COLETA +"') REFERENCES "+ COLETA_TABELA + " ('"+ COLETA_COL_ID + "'), "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_TRATAMENTO +"' FOREIGN KEY ('"+ DADO_COL_ID_TRATAMENTO +"') REFERENCES "+ TRATAMENTO_TABELA + " ('"+ TRATAMENTO_COL_ID + "'), "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_VARIAVEL +"' FOREIGN KEY ('"+ DADO_COL_ID_VARIAVEL +"') REFERENCES "+ VARIAVEL_TABELA + " ('"+ VARIAVEL_COL_ID + "') "
            + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA_FORMULARIO);
        db.execSQL(CRIAR_TABELA_DIC);
        db.execSQL(CRIAR_TABELA_TRATAMENTO);
        db.execSQL(CRIAR_TABELA_VARIAVEL);
        db.execSQL(CRIAR_TABELA_COLETA);
        db.execSQL(CRIAR_TABELA_DADO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DIC_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + FORMULARIO_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + TRATAMENTO_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + VARIAVEL_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + COLETA_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + DADO_TABELA);

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

    public Long updateFormularioStatus(Formulario formulario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FORMULARIO_COL_STATUS, formulario.getStatus_Form());

        return (long) db.update(FORMULARIO_TABELA, values, "id=" + formulario.getId_Form(), null);
    }


    //DIC
    public Long inserirDIC(DIC dic){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DIC_COL_QUANTIDADE_TRATAMENTOS, dic.getQuantidadeTratamentos_DIC());
        values.put(DIC_COL_QUANTIDADE_REPETICOES, dic.getQuantidadeRepeticoes_DIC());
        values.put(DIC_COL_QUANTIDADE_REPLICACOES, dic.getQuantidadeReplicacoes_DIC());
        values.put(DIC_COL_QUANTIDADE_VARIAVEIS, dic.getQuantidadeVariaveis_DIC());
        values.put(DIC_COL_ID_FORMULARIO, dic.getIdFormulario_DIC());


        return db.insert(DIC_TABELA, null, values);
    }

    public DIC lerDICdoFormulario(Long idFormulario_DIC){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DIC_TABELA + " JOIN " + FORMULARIO_TABELA + " ON " + FORMULARIO_COL_ID +" = " + idFormulario_DIC + " WHERE " + DIC_COL_ID_FORMULARIO + " = " + idFormulario_DIC;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) c.moveToFirst();

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
        dic.setIdFormulario_DIC(c.getLong(c.getColumnIndex(DIC_COL_ID_FORMULARIO)));

        return dic;
    }


    //ler todos os DICs
    public List<DIC> lerTodosDICs(String modelo_Form, Long idFormulario_DIC){
        List<DIC> dics = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DIC_TABELA + " JOIN " + FORMULARIO_TABELA + " ON " + FORMULARIO_COL_ID +" = " + idFormulario_DIC + " WHERE " + FORMULARIO_COL_MODELO + " = " + modelo_Form;

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
                dic.setIdFormulario_DIC(c.getLong(c.getColumnIndex(DIC_COL_ID_FORMULARIO)));

            }while (c.moveToNext());
        }

        return dics;
    }


    //Tratamento

    //Inserir Tratamento

    public Long insertTratamento(Tratamento tratamento){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRATAMENTO_COL_NOME, tratamento.getNome_Tratamento());
        values.put(TRATAMENTO_COL_TIPO, tratamento.getTipo_Tratamento());
        values.put(TRATAMENTO_COL_ID_FORMULARIO, tratamento.getIdForm_Tratamento());

        return db.insert(TRATAMENTO_TABELA, null, values);
    }

    //ler todos os Tratamentos
    public List<Tratamento> lerTodosTratamentos(Long id_Formulario){
        List<Tratamento> tratamentos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TRATAMENTO_TABELA + " WHERE " + TRATAMENTO_COL_ID_FORMULARIO + " = " + id_Formulario;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Tratamento tratamento = new Tratamento();
                tratamento.setId_Tratamento(c.getLong(c.getColumnIndex(TRATAMENTO_COL_ID)));
                tratamento.setNome_Tratamento(c.getString(c.getColumnIndex(TRATAMENTO_COL_NOME)));
                tratamento.setTipo_Tratamento(c.getInt(c.getColumnIndex(TRATAMENTO_COL_TIPO)));
                tratamento.setIdForm_Tratamento(c.getLong(c.getColumnIndex(TRATAMENTO_COL_ID_FORMULARIO)));
                tratamentos.add(tratamento);
            }while (c.moveToNext());
        }

        return tratamentos;
    }

    //Variavel

    //Inserir Variavel

    public Long insertVariavel(Variavel variavel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VARIAVEL_COL_NOME, variavel.getNome_Variavel());
        values.put(VARIAVEL_COL_TIPO, variavel.getTipo_Variavel());
        values.put(VARIAVEL_COL_ID_FORMULARIO, variavel.getIdForm_Variavel());

        return db.insert(VARIAVEL_TABELA, null, values);
    }

    //ler todas as Variaveis
    public List<Variavel> lerTodasVariaveis(Long id_Formulario){
        List<Variavel> variaveis = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + VARIAVEL_TABELA + " WHERE " + VARIAVEL_COL_ID_FORMULARIO + " = " + id_Formulario;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Variavel variavel = new Variavel();
                variavel.setId_Variavel(c.getLong(c.getColumnIndex(VARIAVEL_COL_ID)));
                variavel.setNome_Variavel(c.getString(c.getColumnIndex(VARIAVEL_COL_NOME)));
                variavel.setTipo_Variavel(c.getInt(c.getColumnIndex(VARIAVEL_COL_TIPO)));
                variavel.setIdForm_Variavel(c.getLong(c.getColumnIndex(VARIAVEL_COL_ID_FORMULARIO)));
                variaveis.add(variavel);
            }while (c.moveToNext());
        }

        return variaveis;
    }

    //Coleta

    //Inserir Coleta
    public Long insertColeta(Coleta coleta){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLETA_COL_NOME, coleta.getNome_Coleta());
        values.put(COLETA_COL_DESCRICAO, coleta.getDescricao_Coleta());
        values.put(COLETA_COL_DATA_CRIACAO, coleta.getDataCriacao_Coleta());
        values.put(COLETA_COL_DATA_ULTIMA_EDICAO, coleta.getDataUltimaEdicao_Coleta());
        values.put(COLETA_COL_STATUS, coleta.getStatus_Coleta());
        values.put(COLETA_COL_TIPO, coleta.getTipo_Coleta());
        values.put(COLETA_COL_MODELO_FORM, coleta.getModeloForm_Coleta());
        values.put(COLETA_COL_ID_FORMULARIO, coleta.getIdForm_Coleta());

        return db.insert(COLETA_TABELA, null, values);
    }

    public Coleta lerColeta(Long id_Coleta){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + COLETA_TABELA + " WHERE " + COLETA_COL_ID + " = " + id_Coleta;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) c.moveToFirst();

        Coleta coleta = new Coleta();
        coleta.setIdForm_Coleta(c.getLong(c.getColumnIndex(COLETA_COL_ID)));
        coleta.setNome_Coleta(c.getString(c.getColumnIndex(COLETA_COL_NOME)));
        coleta.setDescricao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DESCRICAO)));
        coleta.setDataCriacao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DATA_CRIACAO)));
        coleta.setDataUltimaEdicao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DATA_ULTIMA_EDICAO)));
        coleta.setStatus_Coleta(c.getString(c.getColumnIndex(COLETA_COL_STATUS)));
        coleta.setTipo_Coleta(c.getString(c.getColumnIndex(COLETA_COL_TIPO)));
        coleta.setModeloForm_Coleta(c.getString(c.getColumnIndex(COLETA_COL_MODELO_FORM)));
        coleta.setIdForm_Coleta(c.getLong(c.getColumnIndex(COLETA_COL_ID_FORMULARIO)));

        return coleta;
    }


    //Dado

    //Inserir Dado
    public Long inserirDado(Dado dado){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DADO_COL_VALOR, dado.getValor_Dado());
        values.put(DADO_COL_REPETICAO,dado.getRepeticao_Dado());
        values.put(DADO_COL_REPLICACAO,dado.getReplicacao_Dado());
        values.put(DADO_COL_BLOCO,dado.getBloco_Dado());
        values.put(DADO_COL_FATOR,dado.getFator_Dado());
        values.put(DADO_COL_DIVISAO,dado.getDivisao_Dado());
        values.put(DADO_COL_NIVEL_FATOR,dado.getNivelFator_Dado());
        values.put(DADO_COL_NIVEL_DIVISAO,dado.getNivelDivisao_Dado());
        values.put(DADO_COL_ID_COLETA,dado.getIdColeta_Dado());
        values.put(DADO_COL_ID_TRATAMENTO,dado.getIdTratamento_Dado());
        values.put(DADO_COL_ID_VARIAVEL,dado.getIdVariavel_Dado());

        return db.insert(DADO_TABELA, null, values);
    }


}
