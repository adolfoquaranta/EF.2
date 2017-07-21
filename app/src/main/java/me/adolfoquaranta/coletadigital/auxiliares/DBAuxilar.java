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
import me.adolfoquaranta.coletadigital.modelos.Fator;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.NivelFator;
import me.adolfoquaranta.coletadigital.modelos.NivelParcela;
import me.adolfoquaranta.coletadigital.modelos.Parcela;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

/**
 * Created by adolfo on 13/10/16.
 * Classe de auxilio a base de dados
 */

public class DBAuxilar extends SQLiteOpenHelper {

    private static final String LOG = "DBAuxiliar";
    private static final String DATABASE_NOME = "coletadigital.db";
    private static final Integer DATABASE_VERSAO = 1;

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
    private static final String FORMULARIO_COL_QUANTIDADE_PARCELAS = "quantidadeParcelas_Formulario";

    private static final String TRATAMENTO_TABELA = "Tratamento";
    private static final String TRATAMENTO_COL_ID = "id_Tratamento";
    private static final String TRATAMENTO_COL_NOME = "nome_Tratamento";
    private static final String TRATAMENTO_COL_TIPO = "tipo_Tratamento";
    private static final String TRATAMENTO_COL_ID_FORMULARIO = "idFormulario_Tratamento";
    private static final String TRATAMENTO_CONSTRAINT_FK_TRATAMENTO_FORMULARIO = "FK_Tratamento_Formulario";

    private static final String FATOR_TABELA = "Fator";
    private static final String FATOR_COL_ID = "id_Fator";
    private static final String FATOR_COL_NOME = "nome_Fator";
    private static final String FATOR_COL_TIPO = "tipo_Fator";
    private static final String FATOR_COL_QUANTIDADENIVEIS = "quantidadeNiveis_Fator";
    private static final String FATOR_COL_ID_FORMULARIO = "idFormulario_Fator";
    private static final String FATOR_CONSTRAINT_FK_FATOR_FORMULARIO = "FK_Fator_Formulario";

    private static final String NIVELFATOR_TABELA = "NivelFator";
    private static final String NIVELFATOR_COL_ID = "id_NivelFator";
    private static final String NIVELFATOR_COL_NOME = "nome_NivelFator";
    private static final String NIVELFATOR_COL_NUMERO = "numero_NivelFator";
    private static final String NIVELFATOR_COL_ID_FATOR = "idFator_NivelFator";
    private static final String NIVELFATOR_CONSTRAINT_FK_NIVELFATOR_FATOR = "FK_NivelFator_Fator";

    private static final String PARCELA_TABELA = "Parcela";
    private static final String PARCELA_COL_ID = "id_Parcela";
    private static final String PARCELA_COL_NOME = "nome_Parcela";
    private static final String PARCELA_COL_TIPO = "tipo_Parcela";
    private static final String PARCELA_COL_QUANTIDADENIVEIS = "quantidadeNiveis_Parcela";
    private static final String PARCELA_COL_ID_FORMULARIO = "idFormulario_Parcela";
    private static final String PARCELA_CONSTRAINT_FK_PARCELA_FORMULARIO = "FK_Parcela_Formulario";

    private static final String NIVELPARCELA_TABELA = "NivelParcela";
    private static final String NIVELPARCELA_COL_ID = "id_NivelParcela";
    private static final String NIVELPARCELA_COL_NOME = "nome_NivelParcela";
    private static final String NIVELPARCELA_COL_NUMERO = "numero_NivelParcela";
    private static final String NIVELPARCELA_COL_ID_PARCELA = "idParcela_NivelParcela";
    private static final String NIVELPARCELA_CONSTRAINT_FK_NIVELPARCELA_PARCELA = "FK_NivelParcela_Parcela";

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
    private static final String DADO_COL_TRATAMENTO = "tratamento_Dado";
    private static final String DADO_COL_ID_COLETA = "idColeta_Dado";
    private static final String DADO_COL_VARIAVEL = "variavel_Dado";
    private static final String DADO_COL_ID_TRATAMENTO = "idTratamento_Dado";
    private static final String DADO_COL_ID_VARIAVEL = "idVariavel_Dado";
    private static final String DADO_COL_ID_FATOR = "idFator_Dado";
    private static final String DADO_COL_ID_NIVELFATOR = "idNivelFator_Dado";
    private static final String DADO_COL_ID_PARCELA = "idParcela_Dado";
    private static final String DADO_COL_ID_NIVEL_PARCELA = "idNivelParcela_Dado";
    private static final String DADO_CONSTRAINT_FK_DADO_COLETA = "FK_Dado_Coleta";
    private static final String DADO_CONSTRAINT_FK_DADO_TRATAMENTO = "FK_Dado_Tratamento";
    private static final String DADO_CONSTRAINT_FK_DADO_VARIAVEL = "FK_Dado_Variavel";
    private static final String DADO_CONSTRAINT_FK_DADO_FATOR = "FK_Dado_Fator";
    private static final String DADO_CONSTRAINT_FK_DADO_NIVELFATOR = "FK_Dado_NivelFator";
    private static final String DADO_CONSTRAINT_FK_DADO_PARCELA = "FK_Dado_Parcela";
    private static final String DADO_CONSTRAINT_FK_DADO_NIVELPARCELA = "FK_Dado_NivelParcela";


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
            + FORMULARIO_COL_QUANTIDADE_PARCELAS + " INTEGER "
            + ")";

    private static final String CRIAR_TABELA_TRATAMENTO = "CREATE TABLE "
            + TRATAMENTO_TABELA + "("+ TRATAMENTO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + TRATAMENTO_COL_NOME + " TEXT, "
            + TRATAMENTO_COL_TIPO + " INTEGER, "
            + TRATAMENTO_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + TRATAMENTO_CONSTRAINT_FK_TRATAMENTO_FORMULARIO + "' FOREIGN KEY ('" + TRATAMENTO_COL_ID_FORMULARIO + "') REFERENCES " + FORMULARIO_TABELA + " ('" + FORMULARIO_COL_ID + "') ON DELETE CASCADE"
            + ")";

    private static final String CRIAR_TABELA_FATOR = "CREATE TABLE "
            + FATOR_TABELA + "(" + FATOR_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + FATOR_COL_NOME + " TEXT, "
            + FATOR_COL_TIPO + " INTEGER, "
            + FATOR_COL_QUANTIDADENIVEIS + " INTEGER, "
            + FATOR_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + FATOR_CONSTRAINT_FK_FATOR_FORMULARIO + "' FOREIGN KEY ('" + FATOR_COL_ID_FORMULARIO + "') REFERENCES " + FORMULARIO_TABELA + " ('" + FORMULARIO_COL_ID + "') ON DELETE CASCADE"
            + ")";

    private static final String CRIAR_TABELA_NIVELFATOR = "CREATE TABLE "
            + NIVELFATOR_TABELA + "(" + NIVELFATOR_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + NIVELFATOR_COL_NOME + " TEXT, "
            + NIVELFATOR_COL_NUMERO + " INTEGER, "
            + NIVELFATOR_COL_ID_FATOR + " INTEGER, "
            + "CONSTRAINT '" + NIVELFATOR_CONSTRAINT_FK_NIVELFATOR_FATOR + "' FOREIGN KEY ('" + NIVELFATOR_COL_ID_FATOR + "') REFERENCES " + FATOR_TABELA + " ('" + FATOR_COL_ID + "') ON DELETE CASCADE"
            + ")";

    private static final String CRIAR_TABELA_PARCELA = "CREATE TABLE "
            + PARCELA_TABELA + "(" + PARCELA_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + PARCELA_COL_NOME + " TEXT, "
            + PARCELA_COL_TIPO + " INTEGER, "
            + PARCELA_COL_QUANTIDADENIVEIS + " INTEGER, "
            + PARCELA_COL_ID_FORMULARIO + " INTEGER, "
            + "CONSTRAINT '" + PARCELA_CONSTRAINT_FK_PARCELA_FORMULARIO + "' FOREIGN KEY ('" + PARCELA_COL_ID_FORMULARIO + "') REFERENCES " + FORMULARIO_TABELA + " ('" + FORMULARIO_COL_ID + "') ON DELETE CASCADE"
            + ")";

    private static final String CRIAR_TABELA_NIVELPARCELA = "CREATE TABLE "
            + NIVELPARCELA_TABELA + "(" + NIVELPARCELA_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + NIVELPARCELA_COL_NOME + " TEXT, "
            + NIVELPARCELA_COL_NUMERO + " INTEGER, "
            + NIVELPARCELA_COL_ID_PARCELA + " INTEGER, "
            + "CONSTRAINT '" + NIVELPARCELA_CONSTRAINT_FK_NIVELPARCELA_PARCELA + "' FOREIGN KEY ('" + NIVELPARCELA_COL_ID_PARCELA + "') REFERENCES " + PARCELA_TABELA + " ('" + PARCELA_COL_ID + "') ON DELETE CASCADE"
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
            + DADO_COL_ID_FATOR + " INTEGER, "
            + DADO_COL_ID_NIVELFATOR + " INTEGER, "
            + DADO_COL_ID_COLETA + " INTEGER, "
            + DADO_COL_TRATAMENTO + " INTEGER, "
            + DADO_COL_VARIAVEL + " INTEGER, "
            + DADO_COL_ID_TRATAMENTO + " INTEGER, "
            + DADO_COL_ID_VARIAVEL + " INTEGER, "
            + DADO_COL_ID_PARCELA + " INTEGER, "
            + DADO_COL_ID_NIVEL_PARCELA + " INTEGER, "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_COLETA + "' FOREIGN KEY ('" + DADO_COL_ID_COLETA + "') REFERENCES " + COLETA_TABELA + " ('" + COLETA_COL_ID + "') ON DELETE CASCADE, "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_TRATAMENTO + "' FOREIGN KEY ('" + DADO_COL_ID_TRATAMENTO + "') REFERENCES " + TRATAMENTO_TABELA + " ('" + TRATAMENTO_COL_ID + "') ON DELETE CASCADE, "
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_VARIAVEL + "' FOREIGN KEY ('" + DADO_COL_ID_VARIAVEL + "') REFERENCES " + VARIAVEL_TABELA + " ('" + VARIAVEL_COL_ID + "') ON DELETE CASCADE,"
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_NIVELFATOR + "' FOREIGN KEY ('" + DADO_COL_ID_NIVELFATOR + "') REFERENCES " + NIVELFATOR_TABELA + " ('" + NIVELFATOR_COL_ID + "') ON DELETE CASCADE,"
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_FATOR + "' FOREIGN KEY ('" + DADO_COL_ID_FATOR + "') REFERENCES " + FATOR_TABELA + " ('" + FATOR_COL_ID + "') ON DELETE CASCADE,"
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_PARCELA + "' FOREIGN KEY ('" + DADO_COL_ID_PARCELA + "') REFERENCES " + PARCELA_TABELA + " ('" + PARCELA_COL_ID + "') ON DELETE CASCADE,"
            + "CONSTRAINT '" + DADO_CONSTRAINT_FK_DADO_NIVELPARCELA + "' FOREIGN KEY ('" + DADO_COL_ID_NIVEL_PARCELA + "') REFERENCES " + NIVELPARCELA_TABELA + " ('" + NIVELPARCELA_COL_ID + "') ON DELETE CASCADE"
            + ")";

    public DBAuxilar(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
        //context.deleteDatabase(DATABASE_NOME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA_FORMULARIO);
        db.execSQL(CRIAR_TABELA_TRATAMENTO);
        db.execSQL(CRIAR_TABELA_FATOR);
        db.execSQL(CRIAR_TABELA_NIVELFATOR);
        db.execSQL(CRIAR_TABELA_PARCELA);
        db.execSQL(CRIAR_TABELA_NIVELPARCELA);
        db.execSQL(CRIAR_TABELA_VARIAVEL);
        db.execSQL(CRIAR_TABELA_COLETA);
        db.execSQL(CRIAR_TABELA_DADO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FORMULARIO_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + TRATAMENTO_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + FATOR_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + NIVELFATOR_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + PARCELA_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + NIVELPARCELA_TABELA);
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
        values.put(FORMULARIO_COL_QUANTIDADE_PARCELAS, formulario.getQuantidadeParcelas_Formulario());
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
        formulario.setQuantidadeParcelas_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_PARCELAS)));
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
                formulario.setQuantidadeParcelas_Formulario(c.getInt(c.getColumnIndex(FORMULARIO_COL_QUANTIDADE_PARCELAS)));
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
        values.put(FORMULARIO_COL_QUANTIDADE_PARCELAS, formulario.getQuantidadeParcelas_Formulario());
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


    //PARCELA

    //Inserir PARCELA
    public Long insertParcela(Parcela parcela) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PARCELA_COL_NOME, parcela.getNome_Parcela());
        values.put(PARCELA_COL_TIPO, parcela.getTipo_Parcela());
        values.put(PARCELA_COL_QUANTIDADENIVEIS, parcela.getQuantidadeNiveis_Parcela());
        values.put(PARCELA_COL_ID_FORMULARIO, parcela.getIdFormulario_Parcela());

        return db.insert(PARCELA_TABELA, null, values);
    }

    //ler Parcela
    public Parcela lerParcela(Long id_Parcela) {

        String selectQuery = "SELECT * FROM " + PARCELA_TABELA + " WHERE " + PARCELA_COL_ID + " = " + id_Parcela;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Parcela parcela = new Parcela();
        if (c.moveToFirst()) {
            parcela.setId_Parcela(c.getLong(c.getColumnIndex(PARCELA_COL_ID)));
            parcela.setNome_Parcela(c.getString(c.getColumnIndex(PARCELA_COL_NOME)));
            parcela.setTipo_Parcela(c.getInt(c.getColumnIndex(PARCELA_COL_TIPO)));
            parcela.setQuantidadeNiveis_Parcela(c.getInt(c.getColumnIndex(PARCELA_COL_QUANTIDADENIVEIS)));
            parcela.setIdFormulario_Parcela(c.getLong(c.getColumnIndex(PARCELA_COL_ID_FORMULARIO)));
        }
        c.close();
        return parcela;
    }

    //ler todas os Parcelas
    public ArrayList<Parcela> lerTodasParcelas(Long id_Formulario) {
        ArrayList<Parcela> parcelas = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + PARCELA_TABELA + " WHERE " + PARCELA_COL_ID_FORMULARIO + " = " + id_Formulario;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Parcela parcela = new Parcela();
                parcela.setId_Parcela(c.getLong(c.getColumnIndex(PARCELA_COL_ID)));
                parcela.setNome_Parcela(c.getString(c.getColumnIndex(PARCELA_COL_NOME)));
                parcela.setTipo_Parcela(c.getInt(c.getColumnIndex(PARCELA_COL_TIPO)));
                parcela.setQuantidadeNiveis_Parcela(c.getInt(c.getColumnIndex(PARCELA_COL_QUANTIDADENIVEIS)));
                parcela.setIdFormulario_Parcela(c.getLong(c.getColumnIndex(PARCELA_COL_ID_FORMULARIO)));
                parcelas.add(parcela);
            } while (c.moveToNext());
        }
        c.close();
        return parcelas;
    }

    public int updateParcela(Parcela parcela) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PARCELA_COL_NOME, parcela.getNome_Parcela());
        values.put(PARCELA_COL_TIPO, parcela.getTipo_Parcela());
        values.put(PARCELA_COL_QUANTIDADENIVEIS, parcela.getQuantidadeNiveis_Parcela());
        values.put(PARCELA_COL_ID_FORMULARIO, parcela.getIdFormulario_Parcela());

        return db.update(PARCELA_TABELA, values, PARCELA_COL_ID + " = " + parcela.getId_Parcela(), null);
    }

    //NivelParcela

    //Inserir NivelParcela
    public Long insertNivelParcela(NivelParcela nivelParcela) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NIVELPARCELA_COL_NOME, nivelParcela.getNome_NivelParcela());
        values.put(NIVELPARCELA_COL_NUMERO, nivelParcela.getNumero_NivelParcela());
        values.put(NIVELPARCELA_COL_ID_PARCELA, nivelParcela.getIdParcela_NivelParcela());

        return db.insert(NIVELPARCELA_TABELA, null, values);
    }

    //ler todas as Parcelas
    public ArrayList<NivelParcela> lerTodosNiveisParcela(Long id_Parcela) {
        ArrayList<NivelParcela> niveisParcela = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + NIVELPARCELA_TABELA + " WHERE " + NIVELPARCELA_COL_ID_PARCELA + " = " + id_Parcela;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                NivelParcela nivelParcela = new NivelParcela();
                nivelParcela.setId_NivelParcela(c.getLong(c.getColumnIndex(NIVELPARCELA_COL_ID)));
                nivelParcela.setNome_NivelParcela(c.getString(c.getColumnIndex(NIVELPARCELA_COL_NOME)));
                nivelParcela.setNumero_NivelParcela(c.getInt(c.getColumnIndex(NIVELPARCELA_COL_NUMERO)));
                nivelParcela.setIdParcela_NivelParcela(c.getLong(c.getColumnIndex(NIVELPARCELA_COL_ID_PARCELA)));
                niveisParcela.add(nivelParcela);
            } while (c.moveToNext());
        }
        c.close();
        return niveisParcela;
    }

    public int updateNiveisParcela(NivelParcela nivelParcela) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NIVELPARCELA_COL_NOME, nivelParcela.getNome_NivelParcela());
        values.put(NIVELPARCELA_COL_NUMERO, nivelParcela.getNumero_NivelParcela());
        values.put(NIVELPARCELA_COL_ID_PARCELA, nivelParcela.getIdParcela_NivelParcela());

        return db.update(NIVELPARCELA_TABELA, values, NIVELPARCELA_COL_ID + " = " + nivelParcela.getId_NivelParcela(), null);
    }


    //Fator

    //Inserir Fator
    public Long insertFator(Fator fator) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FATOR_COL_NOME, fator.getNome_Fator());
        values.put(FATOR_COL_TIPO, fator.getTipo_Fator());
        values.put(FATOR_COL_QUANTIDADENIVEIS, fator.getQuantidadeNiveis_Fator());
        values.put(FATOR_COL_ID_FORMULARIO, fator.getIdFormulario_Fator());

        return db.insert(FATOR_TABELA, null, values);
    }

    //ler Fator
    public Fator lerFator(Long id_Fator) {

        String selectQuery = "SELECT * FROM " + FATOR_TABELA + " WHERE " + FATOR_COL_ID + " = " + id_Fator;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Fator fator = new Fator();
        if (c.moveToFirst()) {
            fator.setId_Fator(c.getLong(c.getColumnIndex(FATOR_COL_ID)));
            fator.setNome_Fator(c.getString(c.getColumnIndex(FATOR_COL_NOME)));
            fator.setTipo_Fator(c.getInt(c.getColumnIndex(FATOR_COL_TIPO)));
            fator.setQuantidadeNiveis_Fator(c.getInt(c.getColumnIndex(FATOR_COL_QUANTIDADENIVEIS)));
            fator.setIdFormulario_Fator(c.getLong(c.getColumnIndex(FATOR_COL_ID_FORMULARIO)));
        }
        c.close();
        return fator;
    }

    //ler todos os Fatores
    public ArrayList<Fator> lerTodosFatores(Long id_Formulario) {
        ArrayList<Fator> fatores = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + FATOR_TABELA + " WHERE " + FATOR_COL_ID_FORMULARIO + " = " + id_Formulario;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Fator fator = new Fator();
                fator.setId_Fator(c.getLong(c.getColumnIndex(FATOR_COL_ID)));
                fator.setNome_Fator(c.getString(c.getColumnIndex(FATOR_COL_NOME)));
                fator.setTipo_Fator(c.getInt(c.getColumnIndex(FATOR_COL_TIPO)));
                fator.setQuantidadeNiveis_Fator(c.getInt(c.getColumnIndex(FATOR_COL_QUANTIDADENIVEIS)));
                fator.setIdFormulario_Fator(c.getLong(c.getColumnIndex(FATOR_COL_ID_FORMULARIO)));
                fatores.add(fator);
            } while (c.moveToNext());
        }
        c.close();
        return fatores;
    }

    public int updateFator(Fator fator) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FATOR_COL_NOME, fator.getNome_Fator());
        values.put(FATOR_COL_TIPO, fator.getTipo_Fator());
        values.put(FATOR_COL_QUANTIDADENIVEIS, fator.getQuantidadeNiveis_Fator());
        values.put(FATOR_COL_ID_FORMULARIO, fator.getIdFormulario_Fator());

        return db.update(FATOR_TABELA, values, FATOR_COL_ID + " = " + fator.getId_Fator(), null);
    }

    //NivelFator

    //Inserir NivelFator
    public Long insertNivelFator(NivelFator nivelFator) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NIVELFATOR_COL_NOME, nivelFator.getNome_NivelFator());
        values.put(NIVELFATOR_COL_NUMERO, nivelFator.getNumero_NivelFator());
        values.put(NIVELFATOR_COL_ID_FATOR, nivelFator.getIdFator_NivelFator());

        return db.insert(NIVELFATOR_TABELA, null, values);
    }

    //ler todos os Fatores
    public ArrayList<NivelFator> lerTodosNiveisFator(Long id_Fator) {
        ArrayList<NivelFator> niveisFator = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + NIVELFATOR_TABELA + " WHERE " + NIVELFATOR_COL_ID_FATOR + " = " + id_Fator;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                NivelFator nivelFator = new NivelFator();
                nivelFator.setId_NivelFator(c.getLong(c.getColumnIndex(NIVELFATOR_COL_ID)));
                nivelFator.setNome_NivelFator(c.getString(c.getColumnIndex(NIVELFATOR_COL_NOME)));
                nivelFator.setNumero_NivelFator(c.getInt(c.getColumnIndex(NIVELFATOR_COL_NUMERO)));
                nivelFator.setIdFator_NivelFator(c.getLong(c.getColumnIndex(NIVELFATOR_COL_ID_FATOR)));
                niveisFator.add(nivelFator);
            } while (c.moveToNext());
        }
        c.close();
        return niveisFator;
    }

    public int updateNiveisFator(NivelFator nivelFator) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NIVELFATOR_COL_NOME, nivelFator.getNome_NivelFator());
        values.put(NIVELFATOR_COL_NUMERO, nivelFator.getNumero_NivelFator());
        values.put(NIVELFATOR_COL_ID_FATOR, nivelFator.getIdFator_NivelFator());

        return db.update(NIVELFATOR_TABELA, values, NIVELFATOR_COL_ID + " = " + nivelFator.getId_NivelFator(), null);
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

    public int updateStatusColeta(Coleta coleta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLETA_COL_STATUS, coleta.getStatus_Coleta());

        return db.update(COLETA_TABELA, values, COLETA_COL_ID + "=" + coleta.getId_Coleta(), null);
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
        values.put(DADO_COL_ID_FATOR, dado.getIdFator_Dado());
        values.put(DADO_COL_ID_NIVELFATOR, dado.getIdNivelFator_Dado());
        values.put(DADO_COL_ID_PARCELA, dado.getIdParcela_Dado());
        values.put(DADO_COL_ID_NIVEL_PARCELA, dado.getIdNivelParcela_Dado());
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
                dado.setIdFator_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID_FATOR)));
                dado.setIdNivelFator_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID_NIVELFATOR)));
                dado.setIdParcela_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID_PARCELA)));
                dado.setIdNivelParcela_Dado(c.getLong(c.getColumnIndex(DADO_COL_ID_NIVEL_PARCELA)));
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

    public Dado lerValorDadoFatorial(Long id_Fator, Long id_NivelFator, Integer bloco, Integer repeticao, Integer replicacao, Long id_Variavel, Long id_Coleta) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT " + DADO_COL_VALOR + " FROM " + DADO_TABELA + " WHERE " + DADO_COL_ID_FATOR + " = " + id_Fator
                + " AND " + DADO_COL_ID_NIVELFATOR + " = " + id_NivelFator
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

    public Dado lerValorDadoParcela(Long id_Parcela, Long id_NivelParcela, Integer bloco, Integer repeticao, Integer replicacao, Long id_Variavel, Long id_Coleta) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT " + DADO_COL_VALOR + " FROM " + DADO_TABELA + " WHERE " + DADO_COL_ID_PARCELA + " = " + id_Parcela
                + " AND " + DADO_COL_ID_NIVEL_PARCELA + " = " + id_NivelParcela
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

        return db.update(DADO_TABELA, values, DADO_COL_ID + "=" + dado.getId_Dado(), null);
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


    public Long checarDadoFat(Dado dado) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DADO_TABELA + " WHERE " + DADO_COL_ID_COLETA + " = " + dado.getIdColeta_Dado()
                + " AND " + DADO_COL_ID_FATOR + " = " + dado.getIdFator_Dado()
                + " AND " + DADO_COL_BLOCO + " = " + dado.getBloco_Dado()
                + " AND " + DADO_COL_VARIAVEL + " = " + dado.getVariavel_Dado()
                + " AND " + DADO_COL_REPETICAO + " = " + dado.getRepeticao_Dado()
                + " AND " + DADO_COL_REPLICACAO + " = " + dado.getReplicacao_Dado()
                + " AND " + DADO_COL_ID_NIVELFATOR + " = " + dado.getIdNivelFator_Dado();

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

    public Long checarDadoPar(Dado dado) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DADO_TABELA + " WHERE " + DADO_COL_ID_COLETA + " = " + dado.getIdColeta_Dado()
                + " AND " + DADO_COL_ID_PARCELA + " = " + dado.getIdParcela_Dado()
                + " AND " + DADO_COL_BLOCO + " = " + dado.getBloco_Dado()
                + " AND " + DADO_COL_VARIAVEL + " = " + dado.getVariavel_Dado()
                + " AND " + DADO_COL_REPETICAO + " = " + dado.getRepeticao_Dado()
                + " AND " + DADO_COL_REPLICACAO + " = " + dado.getReplicacao_Dado()
                + " AND " + DADO_COL_ID_NIVEL_PARCELA + " = " + dado.getIdNivelParcela_Dado();

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


