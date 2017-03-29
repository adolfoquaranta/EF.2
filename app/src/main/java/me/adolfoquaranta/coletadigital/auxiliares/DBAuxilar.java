package me.adolfoquaranta.coletadigital.auxiliares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.adolfoquaranta.coletadigital.modelos.Coleta;
import me.adolfoquaranta.coletadigital.modelos.Dado;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

/**
 * Created by adolfo on 13/10/16.
 * Classe de auxilio a base de dados
 */

public class DBAuxilar extends SQLiteOpenHelper {

    private static final String LOG = "DBAuxiliar";
    private static final String DATABASE_NOME = "coletadigital.db";
    private static final Integer DATABASE_VERSAO = 2;

    private static final String FORMULARIO_TABELA = "Formulario";
    private static final String FORMULARIO_COL_ID = "id_Formulario";
    private static final String FORMULARIO_COL_TIPO = "tipo_Formulario";
    private static final String FORMULARIO_COL_NOME = "nome_Formulario";
    private static final String FORMULARIO_COL_DESCRICAO = "descricao_Formulario";
    private static final String FORMULARIO_COL_CRIADOR = "criador_Formulario";
    private static final String FORMULARIO_COL_DATA_CRIACAO = "dataCriacao_Formulario";
    private static final String FORMULARIO_COL_STATUS = "status_Formulario";
    private static final String FORMULARIO_COL_MODELO = "modelo_Formulario";
    private static final String FORMULARIO_COL_QUANTIDADE_TRATAMENTOS = "quantidadeTratamentos_Formulario";
    private static final String FORMULARIO_COL_QUANTIDADE_REPETICOES = "quantidadeRepeticoes_Formulario";
    private static final String FORMULARIO_COL_QUANTIDADE_REPLICACOES = "quantidadeReplicacoes_Formulario";
    private static final String FORMULARIO_COL_QUANTIDADE_VARIAVEIS = "quantidadeVariaveis_Formulario";
    private static final String FORMULARIO_COL_QUANTIDADE_BLOCOS = "quantidadeBlocos_Formulario";
    private static final String FORMULARIO_COL_QUANTIDADE_FATORES = "quantidadeFatores_Formulario";
    private static final String FORMULARIO_COL_QUANTIDADE_DIVISOES = "quantidadeDivisoes_Formulario";

    private static final String TRATAMENTO_TABELA = "Tratamento";
    private static final String TRATAMENTO_COL_ID = "id_Tratamento";
    private static final String TRATAMENTO_COL_NOME = "nome_Tratamento";
    private static final String TRATAMENTO_COL_TIPO = "tipo_Tratamento";
    private static final String TRATAMENTO_COL_ID_FORMULARIO = "idFormulario_Tratamento";
    private static final String TRATAMENTO_CONSTRAINT_FK_TRATAMENTO_FORMULARIO = "FK_Tratamento_Formulario";

    private static final String VARIAVEL_TABELA = "Variavel";
    private static final String VARIAVEL_COL_ID = "id_Variavel";
    private static final String VARIAVEL_COL_NOME = "nome_Variavel";
    private static final String VARIAVEL_COL_TIPO = "tipo_Variavel";
    private static final String VARIAVEL_COL_ID_FORMULARIO = "idFormulario_Variavel";
    private static final String VARIAVEL_CONSTRAINT_FK_VARIAVEL_FORMULARIO = "FK_Variavel_Formulario";

    private static final String COLETA_TABELA = "Coleta";
    private static final String COLETA_COL_ID = "id_Coleta";
    private static final String COLETA_COL_NOME = "nome_Coleta";
    private static final String COLETA_COL_DESCRICAO = "descricao_Coleta";
    private static final String COLETA_COL_DATA_CRIACAO = "dataCriacao_Coleta";
    private static final String COLETA_COL_DATA_ULTIMA_EDICAO = "dataUltimaEdicao_Coleta";
    private static final String COLETA_COL_STATUS = "status_Coleta";
    private static final String COLETA_COL_TIPO = "tipo_Coleta";
    private static final String COLETA_COL_ID_FORMULARIO = "idFormulario_Coleta";
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
    private static final String DADO_COL_TRATAMENTO = "tratamento_Dado";
    private static final String DADO_COL_VARIAVEL = "variavel_Dado";
    private static final String DADO_COL_ID_TRATAMENTO = "idTratamento_Dado";
    private static final String DADO_COL_ID_VARIAVEL = "idVariavel_Dado";
    private static final String DADO_CONSTRAINT_FK_DADO_COLETA = "FK_Dado_Coleta";
    private static final String DADO_CONSTRAINT_FK_DADO_TRATAMENTO = "FK_Dado_Tratamento";
    private static final String DADO_CONSTRAINT_FK_DADO_VARIAVEL = "FK_Dado_Variavel";


    private static final String CRIAR_TABELA_FORMULARIO = "CREATE TABLE "
            + FORMULARIO_TABELA + "(" + FORMULARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + FORMULARIO_COL_TIPO + " TEXT NOT NULL, "
            + FORMULARIO_COL_MODELO + " INTEGER, "
            + FORMULARIO_COL_NOME + " TEXT, "
            + FORMULARIO_COL_DESCRICAO + " TEXT, "
            + FORMULARIO_COL_CRIADOR + " TEXT, "
            + FORMULARIO_COL_DATA_CRIACAO + " TEXT, "
            + FORMULARIO_COL_STATUS + " INTEGER, "
            + FORMULARIO_COL_QUANTIDADE_TRATAMENTOS + " INTEGER, "
            + FORMULARIO_COL_QUANTIDADE_REPETICOES + " INTEGER, "
            + FORMULARIO_COL_QUANTIDADE_REPLICACOES + " INTEGER, "
            + FORMULARIO_COL_QUANTIDADE_VARIAVEIS + " INTEGER, "
            + FORMULARIO_COL_QUANTIDADE_BLOCOS + " INTEGER, "
            + FORMULARIO_COL_QUANTIDADE_FATORES + " INTEGER, "
            + FORMULARIO_COL_QUANTIDADE_DIVISOES + " INTEGER "
            + ")";

    private static final String CRIAR_TABELA_TRATAMENTO = "CREATE TABLE "
            + TRATAMENTO_TABELA + "("+ TRATAMENTO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + TRATAMENTO_COL_NOME + " TEXT, "
            + TRATAMENTO_COL_TIPO + " INTEGER, "
            + TRATAMENTO_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + TRATAMENTO_CONSTRAINT_FK_TRATAMENTO_FORMULARIO + "' FOREIGN KEY ('" + TRATAMENTO_COL_ID_FORMULARIO + "') REFERENCES " + FORMULARIO_TABELA + " ('" + FORMULARIO_COL_ID + "') ON DELETE CASCADE"
            + ")";

    private static final String CRIAR_TABELA_VARIAVEL = "CREATE TABLE "
            + VARIAVEL_TABELA + "("+ VARIAVEL_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + VARIAVEL_COL_NOME + " TEXT, "
            + VARIAVEL_COL_TIPO + " INTEGER, "
            + VARIAVEL_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + VARIAVEL_CONSTRAINT_FK_VARIAVEL_FORMULARIO + "' FOREIGN KEY ('" + VARIAVEL_COL_ID_FORMULARIO + "') REFERENCES " + FORMULARIO_TABELA + " ('" + FORMULARIO_COL_ID + "') ON DELETE CASCADE"
            + ")";

    private static final String CRIAR_TABELA_COLETA = "CREATE TABLE "
            + COLETA_TABELA + "(" + COLETA_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLETA_COL_NOME + " TEXT NOT NULL, "
            + COLETA_COL_DESCRICAO + " TEXT, "
            + COLETA_COL_DATA_CRIACAO + " TEXT, "
            + COLETA_COL_DATA_ULTIMA_EDICAO + " TEXT, "
            + COLETA_COL_STATUS + " TEXT, "
            + COLETA_COL_TIPO + " TEXT, "
            + COLETA_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + COLETA_CONSTRAINT_FK_COLETA_FORMULARIO + "' FOREIGN KEY ('" + COLETA_COL_ID_FORMULARIO + "') REFERENCES " + FORMULARIO_TABELA + " ('" + FORMULARIO_COL_ID + "') ON DELETE CASCADE"
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
            + DADO_COL_TRATAMENTO + " INTEGER, "
            + DADO_COL_VARIAVEL + " INTEGER, "
            + DADO_COL_ID_TRATAMENTO + " INTEGER, "
            + DADO_COL_ID_VARIAVEL + " INTEGER, "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_COLETA + "' FOREIGN KEY ('" + DADO_COL_ID_COLETA + "') REFERENCES " + COLETA_TABELA + " ('" + COLETA_COL_ID + "') ON DELETE CASCADE, "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_TRATAMENTO + "' FOREIGN KEY ('" + DADO_COL_ID_TRATAMENTO + "') REFERENCES " + TRATAMENTO_TABELA + " ('" + TRATAMENTO_COL_ID + "') ON DELETE CASCADE, "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_VARIAVEL + "' FOREIGN KEY ('" + DADO_COL_ID_VARIAVEL + "') REFERENCES " + VARIAVEL_TABELA + " ('" + VARIAVEL_COL_ID + "') ON DELETE CASCADE"
            + ")";

    public DBAuxilar(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
        //context.deleteDatabase(DATABASE_NOME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA_FORMULARIO);
        db.execSQL(CRIAR_TABELA_TRATAMENTO);
        db.execSQL(CRIAR_TABELA_VARIAVEL);
        db.execSQL(CRIAR_TABELA_COLETA);
        db.execSQL(CRIAR_TABELA_DADO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
        values.put(FORMULARIO_COL_TIPO, formulario.getTipo_Formulario());
        values.put(FORMULARIO_COL_MODELO, formulario.getModelo_Formulario());
        values.put(FORMULARIO_COL_NOME, formulario.getNome_Formulario());
        values.put(FORMULARIO_COL_DESCRICAO, formulario.getDescricao_Formulario());
        values.put(FORMULARIO_COL_CRIADOR, formulario.getCriador_Formulario());
        values.put(FORMULARIO_COL_DATA_CRIACAO, formulario.getDataCriacao_Formulario());
        values.put(FORMULARIO_COL_STATUS, formulario.getStatus_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_TRATAMENTOS, formulario.getQuantidadeTratamentos_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_REPETICOES, formulario.getQuantidadeRepeticoes_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_REPLICACOES, formulario.getQuantidadeReplicacoes_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_VARIAVEIS, formulario.getQuantidadeVariaveis_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_BLOCOS, formulario.getQuantidadeBlocos_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_DIVISOES, formulario.getQuantidadeDivisoes_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_FATORES, formulario.getQuantidadeFatores_Formulario());

        return db.insert(FORMULARIO_TABELA, null, values);

    }

    public Formulario lerFormulario(Long id_Formulario) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + FORMULARIO_TABELA + " WHERE " + FORMULARIO_COL_ID + " = " + id_Formulario;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) c.moveToFirst();

        Formulario formulario = new Formulario();
        assert c != null;
        formulario.setId_Formulario(c.getLong(c.getColumnIndex(FORMULARIO_COL_ID)));
        formulario.setTipo_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_TIPO)));
        formulario.setModelo_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_MODELO)));
        formulario.setNome_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_NOME)));
        formulario.setDescricao_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_DESCRICAO)));
        formulario.setCriador_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_CRIADOR)));
        formulario.setDataCriacao_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_DATA_CRIACAO)));
        formulario.setStatus_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_STATUS)));
        formulario.setQuantidadeTratamentos_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_TRATAMENTOS)));
        formulario.setQuantidadeRepeticoes_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_REPETICOES)));
        formulario.setQuantidadeReplicacoes_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_REPLICACOES)));
        formulario.setQuantidadeVariaveis_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_VARIAVEIS)));
        formulario.setQuantidadeBlocos_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_BLOCOS)));
        formulario.setQuantidadeFatores_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_FATORES)));
        formulario.setQuantidadeDivisoes_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_DIVISOES)));
        c.close();

        return formulario;
    }

    //ler todos os formularios
    public List<Formulario> lerTodosFormularios(String tipo_Formulario) {
        List<Formulario> formularios = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + FORMULARIO_TABELA + " WHERE " + FORMULARIO_COL_TIPO + " = " + "'" + tipo_Formulario + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Formulario formulario = new Formulario();
                formulario.setId_Formulario(c.getLong(c.getColumnIndex(FORMULARIO_COL_ID)));
                formulario.setTipo_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_TIPO)));
                formulario.setModelo_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_MODELO)));
                formulario.setNome_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_NOME)));
                formulario.setDescricao_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_DESCRICAO)));
                formulario.setCriador_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_CRIADOR)));
                formulario.setDataCriacao_Formulario(c.getString(c.getColumnIndex(FORMULARIO_COL_DATA_CRIACAO)));
                formulario.setStatus_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_STATUS)));
                formulario.setQuantidadeTratamentos_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_TRATAMENTOS)));
                formulario.setQuantidadeRepeticoes_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_REPETICOES)));
                formulario.setQuantidadeReplicacoes_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_REPLICACOES)));
                formulario.setQuantidadeVariaveis_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_VARIAVEIS)));
                formulario.setQuantidadeBlocos_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_BLOCOS)));
                formulario.setQuantidadeFatores_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_FATORES)));
                formulario.setQuantidadeDivisoes_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_DIVISOES)));
                formularios.add(formulario);

            }while (c.moveToNext());
        }

        c.close();

        return formularios;
    }

    public int updateFormularioStatus(Formulario formulario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FORMULARIO_COL_STATUS, formulario.getStatus_Formulario());

        return db.update(FORMULARIO_TABELA, values, FORMULARIO_COL_ID + " = " + formulario.getId_Formulario(), null);
    }

    public int updateFormulario(Formulario formulario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FORMULARIO_COL_TIPO, formulario.getTipo_Formulario());
        values.put(FORMULARIO_COL_MODELO, formulario.getModelo_Formulario());
        values.put(FORMULARIO_COL_NOME, formulario.getNome_Formulario());
        values.put(FORMULARIO_COL_DESCRICAO, formulario.getDescricao_Formulario());
        values.put(FORMULARIO_COL_CRIADOR, formulario.getCriador_Formulario());
        values.put(FORMULARIO_COL_DATA_CRIACAO, formulario.getDataCriacao_Formulario());
        values.put(FORMULARIO_COL_STATUS, formulario.getStatus_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_TRATAMENTOS, formulario.getQuantidadeTratamentos_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_REPETICOES, formulario.getQuantidadeRepeticoes_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_REPLICACOES, formulario.getQuantidadeReplicacoes_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_VARIAVEIS, formulario.getQuantidadeVariaveis_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_BLOCOS, formulario.getQuantidadeBlocos_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_DIVISOES, formulario.getQuantidadeDivisoes_Formulario());
        values.put(FORMULARIO_COL_QUANTIDADE_FATORES, formulario.getQuantidadeFatores_Formulario());

        return db.update(FORMULARIO_TABELA, values, FORMULARIO_COL_ID + " = " + formulario.getId_Formulario(), null);
    }

    public int deleteFormulario(Long id_Form) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FORMULARIO_TABELA, FORMULARIO_COL_ID + " = " + id_Form, null);
    }



    //Tratamento

    //Inserir Tratamento
    public Long insertTratamento(Tratamento tratamento){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRATAMENTO_COL_NOME, tratamento.getNome_Tratamento());
        values.put(TRATAMENTO_COL_TIPO, tratamento.getTipo_Tratamento());
        values.put(TRATAMENTO_COL_ID_FORMULARIO, tratamento.getIdFormulario_Tratamento());

        return db.insert(TRATAMENTO_TABELA, null, values);
    }

    //ler todos os Tratamentos
    public ArrayList<Tratamento> lerTodosTratamentos(Long id_Formulario) {
        ArrayList<Tratamento> tratamentos = new ArrayList<>();

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
                tratamento.setIdFormulario_Tratamento(c.getLong(c.getColumnIndex(TRATAMENTO_COL_ID_FORMULARIO)));
                tratamentos.add(tratamento);
            }while (c.moveToNext());
        }
        c.close();
        return tratamentos;
    }

    public int updateTratamento(Tratamento tratamento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRATAMENTO_COL_NOME, tratamento.getNome_Tratamento());
        values.put(TRATAMENTO_COL_TIPO, tratamento.getTipo_Tratamento());
        values.put(TRATAMENTO_COL_ID_FORMULARIO, tratamento.getIdFormulario_Tratamento());

        return db.update(TRATAMENTO_TABELA, values, TRATAMENTO_COL_ID + " = " + tratamento.getId_Tratamento(), null);
    }


    //Variavel
    //Inserir Variavel
    public Long insertVariavel(Variavel variavel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VARIAVEL_COL_NOME, variavel.getNome_Variavel());
        values.put(VARIAVEL_COL_TIPO, variavel.getTipo_Variavel());
        values.put(VARIAVEL_COL_ID_FORMULARIO, variavel.getIdFormulario_Variavel());

        return db.insert(VARIAVEL_TABELA, null, values);
    }

    //ler todas as Variaveis
    public ArrayList<Variavel> lerTodasVariaveis(Long id_Formulario) {
        ArrayList<Variavel> variaveis = new ArrayList<>();

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
                variavel.setIdFormulario_Variavel(c.getLong(c.getColumnIndex(VARIAVEL_COL_ID_FORMULARIO)));
                variaveis.add(variavel);
            }while (c.moveToNext());
        }
        c.close();
        return variaveis;
    }

    public int updateVariavel(Variavel variavel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VARIAVEL_COL_NOME, variavel.getNome_Variavel());
        values.put(VARIAVEL_COL_TIPO, variavel.getTipo_Variavel());
        values.put(VARIAVEL_COL_ID_FORMULARIO, variavel.getIdFormulario_Variavel());

        return db.update(VARIAVEL_TABELA, values, VARIAVEL_COL_ID + " = " + variavel.getId_Variavel(), null);
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
        values.put(COLETA_COL_ID_FORMULARIO, coleta.getIdFormulario_Coleta());
        values.put(COLETA_COL_ID_FORMULARIO, coleta.getIdFormulario_Coleta());

        return db.insert(COLETA_TABELA, null, values);
    }

    public Coleta lerColeta(Long id_Coleta){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + COLETA_TABELA + " WHERE " + COLETA_COL_ID + " = " + id_Coleta;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) c.moveToFirst();

        Coleta coleta = new Coleta();
        assert c != null;
        coleta.setId_Coleta(c.getLong(c.getColumnIndex(COLETA_COL_ID)));
        coleta.setNome_Coleta(c.getString(c.getColumnIndex(COLETA_COL_NOME)));
        coleta.setDescricao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DESCRICAO)));
        coleta.setDataCriacao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DATA_CRIACAO)));
        coleta.setDataUltimaEdicao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DATA_ULTIMA_EDICAO)));
        coleta.setStatus_Coleta(c.getString(c.getColumnIndex(COLETA_COL_STATUS)));
        coleta.setTipo_Coleta(c.getString(c.getColumnIndex(COLETA_COL_TIPO)));
        coleta.setIdFormulario_Coleta(c.getLong(c.getColumnIndex(COLETA_COL_ID)));
        c.close();

        return coleta;
    }

    public ArrayList<Coleta> lerTodasColetas(Long id_Formulario) {
        ArrayList<Coleta> coletas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + COLETA_TABELA + " WHERE " + COLETA_COL_ID_FORMULARIO + "=" + id_Formulario;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Coleta coleta = new Coleta();
                coleta.setId_Coleta(c.getLong(c.getColumnIndex(COLETA_COL_ID)));
                coleta.setNome_Coleta(c.getString(c.getColumnIndex(COLETA_COL_NOME)));
                coleta.setDescricao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DESCRICAO)));
                coleta.setDataCriacao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DATA_CRIACAO)));
                coleta.setDataUltimaEdicao_Coleta(c.getString(c.getColumnIndex(COLETA_COL_DATA_ULTIMA_EDICAO)));
                coleta.setStatus_Coleta(c.getString(c.getColumnIndex(COLETA_COL_STATUS)));
                coleta.setTipo_Coleta(c.getString(c.getColumnIndex(COLETA_COL_TIPO)));
                coleta.setIdFormulario_Coleta(c.getLong(c.getColumnIndex(COLETA_COL_ID_FORMULARIO)));
                coletas.add(coleta);
            } while (c.moveToNext());
        }

        c.close();
        return coletas;
    }


    public int deleteColeta(Long id_Coleta) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COLETA_TABELA, COLETA_COL_ID + " = " + id_Coleta, null);
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
        values.put(DADO_COL_TRATAMENTO, dado.getTratamento_Dado());
        values.put(DADO_COL_VARIAVEL, dado.getVariavel_Dado());
        values.put(DADO_COL_ID_TRATAMENTO,dado.getIdTratamento_Dado());
        values.put(DADO_COL_ID_VARIAVEL,dado.getIdVariavel_Dado());

        return db.insert(DADO_TABELA, null, values);
    }

    public ArrayList<Dado> lerTodosDadoDe(Long id_Coleta) {
        ArrayList<Dado> dados = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DADO_TABELA + " WHERE " + DADO_COL_ID_COLETA + " = " + id_Coleta;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Dado dado = new Dado();
                dado.setId_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID)));
                dado.setValor_Dado(c.getString(c.getColumnIndex(DADO_COL_VALOR)));
                dado.setRepeticao_Dado(c.getInt(c.getColumnIndex(DADO_COL_REPETICAO)));
                dado.setReplicacao_Dado(c.getInt(c.getColumnIndex(DADO_COL_REPLICACAO)));
                dado.setBloco_Dado(c.getInt(c.getColumnIndex(DADO_COL_BLOCO)));
                dado.setFator_Dado(c.getInt(c.getColumnIndex(DADO_COL_FATOR)));
                dado.setDivisao_Dado(c.getInt(c.getColumnIndex(DADO_COL_DIVISAO)));
                dado.setNivelFator_Dado(c.getInt(c.getColumnIndex(DADO_COL_NIVEL_FATOR)));
                dado.setNivelDivisao_Dado(c.getInt(c.getColumnIndex(DADO_COL_NIVEL_DIVISAO)));
                dado.setIdColeta_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID_COLETA)));
                dado.setTratamento_Dado(c.getInt(c.getColumnIndex(DADO_COL_TRATAMENTO)));
                dado.setVariavel_Dado(c.getInt(c.getColumnIndex(DADO_COL_VARIAVEL)));
                dado.setIdTratamento_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID_TRATAMENTO)));
                dado.setIdVariavel_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID_VARIAVEL)));
                dados.add(dado);
            } while (c.moveToNext());
        }
        c.close();

        return dados;

    }


    public Dado lerValorDado(Long id_Tratamento, Integer bloco, Integer repeticao, Integer replicacao, Long id_Variavel, Long id_Coleta) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT " + DADO_COL_VALOR + " FROM " + DADO_TABELA + " WHERE " + DADO_COL_ID_TRATAMENTO + " = " + id_Tratamento
                + " AND " + DADO_COL_BLOCO + " = " + bloco
                + " AND " + DADO_COL_REPETICAO + " = " + repeticao
                + " AND " + DADO_COL_REPLICACAO + " = " + replicacao
                + " AND " + DADO_COL_ID_VARIAVEL + " = " + id_Variavel
                + " AND " + DADO_COL_ID_COLETA + " = " + id_Coleta;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) c.moveToFirst();

        Dado dado = new Dado();
        assert c != null;
        dado.setValor_Dado(c.getString(c.getColumnIndex(DADO_COL_VALOR)));
        c.close();

        return dado;

    }


    public int updateDado(Dado dado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DADO_COL_VALOR, dado.getValor_Dado());

        return db.update(DADO_TABELA, values, "id_Dado=" + dado.getId_Dado(), null);
    }

    public Long checarDado(Dado dado) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DADO_TABELA + " WHERE " + DADO_COL_ID_COLETA + " = " + dado.getIdColeta_Dado()
                + " AND " + DADO_COL_TRATAMENTO + " = " + dado.getTratamento_Dado()
                + " AND " + DADO_COL_BLOCO + " = " + dado.getBloco_Dado()
                + " AND " + DADO_COL_VARIAVEL + " = " + dado.getVariavel_Dado()
                + " AND " + DADO_COL_REPETICAO + " = " + dado.getRepeticao_Dado()
                + " AND " + DADO_COL_REPLICACAO + " = " + dado.getReplicacao_Dado();
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        Long id_Dado;
        if (c.moveToFirst()) {
            id_Dado = c.getLong(c.getColumnIndex(DADO_COL_ID));
        } else {
            id_Dado = 0L;
        }

        c.close();

        return id_Dado;

    }



}


