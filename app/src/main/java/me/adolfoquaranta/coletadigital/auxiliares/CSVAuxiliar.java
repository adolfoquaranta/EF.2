package me.adolfoquaranta.coletadigital.auxiliares;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.adolfoquaranta.coletadigital.modelos.Coleta;
import me.adolfoquaranta.coletadigital.modelos.Fator;
import me.adolfoquaranta.coletadigital.modelos.Formulario;
import me.adolfoquaranta.coletadigital.modelos.NivelFator;
import me.adolfoquaranta.coletadigital.modelos.NivelParcela;
import me.adolfoquaranta.coletadigital.modelos.Parcela;
import me.adolfoquaranta.coletadigital.modelos.Tratamento;
import me.adolfoquaranta.coletadigital.modelos.Variavel;

public class CSVAuxiliar {
    private Long id_Formulario, id_Coleta;
    private DBAuxilar dbAuxilar;
    private String filePath;

    public CSVAuxiliar(Long id_Formulario, Long id_Coleta, Context context) {
        this.id_Formulario = id_Formulario;
        this.id_Coleta = id_Coleta;
        dbAuxilar = new DBAuxilar(context);
    }

    public String exportarCSV(Integer modelo_Formulario) {
        Formulario formulario = dbAuxilar.lerFormulario(id_Formulario);
        Coleta coleta = dbAuxilar.lerColeta(id_Coleta);
        ArrayList<Variavel> variaveis = dbAuxilar.lerTodasVariaveis(id_Formulario);

        if (modelo_Formulario == 0 || modelo_Formulario == 1) {
            ArrayList<Tratamento> tratamentos = dbAuxilar.lerTodosTratamentos(id_Formulario);
            criarDiretorioEnomeArquivo(formulario.getNome_Formulario(), coleta.getNome_Coleta());
            return escreverArquivoDICouDBC(formulario, coleta, tratamentos, variaveis);
        } else if (modelo_Formulario == 2) {
            ArrayList<Fator> fatores = dbAuxilar.lerTodosFatores(id_Formulario);
            criarDiretorioEnomeArquivo(formulario.getNome_Formulario(), coleta.getNome_Coleta());
            return escreverArquivoFAT(formulario, coleta, fatores, variaveis);
        } else if (modelo_Formulario == 3) {
            ArrayList<Parcela> parcelas = dbAuxilar.lerTodasParcelas(id_Formulario);
            criarDiretorioEnomeArquivo(formulario.getNome_Formulario(), coleta.getNome_Coleta());
            return escreverArquivoParcela(formulario, coleta, parcelas, variaveis);
        } else {
            return "";
        }

    }

    private boolean criarDiretorioEnomeArquivo(String nomeA, String nomeB) {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileDir = "COLETA DIGITAL";
        String fileName = (nomeA
                + "_"
                + nomeB
                + "_"
                + new SimpleDateFormat("dd-MM-yyyy_HHmmss", new Locale("pt", "BR")).format(new Date()))
                + ".csv";
        filePath = baseDir + File.separator + fileDir + File.separator + fileName;

        File folder = new File(baseDir + File.separator + fileDir);
        Boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        return success;
    }

    private String escreverArquivoDICouDBC(Formulario formulario, Coleta coleta, ArrayList<Tratamento> tratamentos, ArrayList<Variavel> variaveis) {
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            List<String[]> linhas = new ArrayList<>();
            String[] cabecalhoPosicoes = new String[]{}, cabecalhoNomeVariaveis = new String[formulario.getQuantidadeVariaveis_Formulario()];

            String bloco = "BLOCO", tratamento = "TRATAMENTO", repeticao = "REPETICAO", replicacao = "REPLICA";

            if (formulario.getModelo_Formulario() == 0) {
                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
                    cabecalhoPosicoes = new String[]{tratamento, repeticao};
                } else {
                    cabecalhoPosicoes = new String[]{tratamento, repeticao, replicacao};
                }
            }
            if (formulario.getModelo_Formulario() == 1) {
                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
                    cabecalhoPosicoes = new String[]{bloco, tratamento};
                } else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
                    cabecalhoPosicoes = new String[]{bloco, tratamento, repeticao};
                } else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
                    cabecalhoPosicoes = new String[]{bloco, tratamento, replicacao};
                } else {
                    cabecalhoPosicoes = new String[]{bloco, tratamento, repeticao, replicacao};
                }
            }

            for (int v = 0; v < formulario.getQuantidadeVariaveis_Formulario(); v++) {
                cabecalhoNomeVariaveis[v] = variaveis.get(v).getNome_Variavel();
            }
            linhas.add(ArrayUtils.addAll(cabecalhoPosicoes, cabecalhoNomeVariaveis));

            if (formulario.getQuantidadeBlocos_Formulario() != -1) {
                for (int bloc = 0; bloc < formulario.getQuantidadeBlocos_Formulario(); bloc++) {
                    for (int trat = 0; trat < formulario.getQuantidadeTratamentos_Formulario(); trat++) {
                        for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                            for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                String[] posicoes;

                                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
                                    posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento()};
                                } else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
                                    posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1)};
                                } else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1) {
                                    posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento(), String.valueOf(repli + 1)};
                                } else {
                                    posicoes = new String[]{String.valueOf(bloc + 1), tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                }

                                String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                    valores[var] = dbAuxilar.lerValorDado(tratamentos.get(trat).getId_Tratamento(), bloc, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                                }

                                String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                linhas.add(colunas);
                            }
                        }
                    }
                }
            } else {
                for (int trat = 0; trat < formulario.getQuantidadeTratamentos_Formulario(); trat++) {
                    for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                        for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                            String[] posicoes;

                            if (formulario.getQuantidadeReplicacoes_Formulario() <= 1) {
                                posicoes = new String[]{tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1)};
                            } else {
                                posicoes = new String[]{tratamentos.get(trat).getNome_Tratamento(), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                            }

                            String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                            for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                valores[var] = dbAuxilar.lerValorDado(tratamentos.get(trat).getId_Tratamento(), 0, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                            }

                            String[] colunas = ArrayUtils.addAll(posicoes, valores);
                            linhas.add(colunas);
                        }
                    }
                }
            }
            writer.writeAll(linhas);
            writer.close();
            Log.i("Witter", "ArquivoGerado");
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    private String escreverArquivoFAT(Formulario formulario, Coleta coleta, ArrayList<Fator> fatores, ArrayList<Variavel> variaveis) {
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            List<String[]> linhas = new ArrayList<>();
            String[] cabecalhoPosicoes = new String[]{}, cabecalhoNomeVariaveis = new String[formulario.getQuantidadeVariaveis_Formulario()], cabecalhoNomeFatores = new String[formulario.getQuantidadeFatores_Formulario()];

            String bloco = "Bloco", repeticao = "Repeticao", replicacao = "Replica";

            for (int v = 0; v < formulario.getQuantidadeVariaveis_Formulario(); v++) {
                cabecalhoNomeVariaveis[v] = variaveis.get(v).getNome_Variavel();
            }
            for (int f = 0; f < formulario.getQuantidadeFatores_Formulario(); f++) {
                cabecalhoNomeFatores[f] = fatores.get(f).getNome_Fator();
            }


            StringBuilder builder = new StringBuilder();
            for (int s = 0; s < cabecalhoNomeFatores.length; s++) {
                builder.append(cabecalhoNomeFatores[s]);
                if (s + 1 < cabecalhoNomeFatores.length)
                    builder.append(",");
            }
            String nomeFatores = builder.toString();


            if (formulario.getQuantidadeBlocos_Formulario() <= 1 && formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{nomeFatores, repeticao};
            else if (formulario.getQuantidadeBlocos_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{nomeFatores, repeticao, replicacao};
            else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, nomeFatores};
            else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, nomeFatores, repeticao};
            else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, nomeFatores, replicacao};
            else
                cabecalhoPosicoes = new String[]{bloco, nomeFatores, repeticao, replicacao};


            linhas.add(ArrayUtils.addAll(cabecalhoPosicoes, cabecalhoNomeVariaveis));


            if (formulario.getQuantidadeBlocos_Formulario() != -1) {
                for (int bloc = 0; bloc < formulario.getQuantidadeBlocos_Formulario(); bloc++) {
                    for (int fat = 0; fat < formulario.getQuantidadeFatores_Formulario(); fat++) {
                        ArrayList<NivelFator> niveis = dbAuxilar.lerTodosNiveisFator(fatores.get(fat).getId_Fator());
                        for (int niv = 0; niv < niveis.size(); niv++) {
                            for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                                for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                    String[] posicoes;


                                    if (formulario.getQuantidadeBlocos_Formulario() <= 1 && formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                        posicoes = new String[]{" ", " ", String.valueOf(rep + 1)};
                                    else if (formulario.getQuantidadeBlocos_Formulario() <= 1)
                                        posicoes = new String[]{" ", " ", String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                    else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator()};
                                    else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), " ", " ", String.valueOf(rep + 1)};
                                    else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), " ", " ", String.valueOf(repli + 1)};
                                    else
                                        posicoes = new String[]{String.valueOf(bloc + 1), " ", " ", String.valueOf(rep + 1), String.valueOf(repli + 1)};

                                    String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                    for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                        valores[var] = dbAuxilar.lerValorDadoFatorial(fatores.get(fat).getId_Fator(), niveis.get(niv).getId_NivelFator(), bloc, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                                    }

                                    String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                    linhas.add(colunas);
                                }
                            }
                        }
                    }
                }
            } else {
                for (int fat = 0; fat < formulario.getQuantidadeFatores_Formulario(); fat++) {
                    ArrayList<NivelFator> niveis = dbAuxilar.lerTodosNiveisFator(fatores.get(fat).getId_Fator());
                    for (int niv = 0; niv < niveis.size(); niv++) {
                        for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                            for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                String[] posicoes;

                                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                    posicoes = new String[]{fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator()};
                                else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                    posicoes = new String[]{" ", " ", String.valueOf(rep + 1)};
                                else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                    posicoes = new String[]{" ", " ", String.valueOf(repli + 1)};
                                else
                                    posicoes = new String[]{" ", " ", String.valueOf(rep + 1), String.valueOf(repli + 1)};

                                String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                    valores[var] = dbAuxilar.lerValorDadoFatorial(fatores.get(fat).getId_Fator(), niveis.get(niv).getId_NivelFator(), 0, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                                }

                                String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                linhas.add(colunas);
                            }
                        }
                    }
                }

            }


            writer.writeAll(linhas);
            writer.close();
            Log.i("Witter", "ArquivoGerado");
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

/*
    private String escreverArquivoFAT(Formulario formulario, Coleta coleta, ArrayList<Fator> fatores, ArrayList<Variavel> variaveis) {
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            List<String[]> linhas = new ArrayList<>();
            String[] cabecalhoPosicoes = new String[]{}, cabecalhoNomeVariaveis = new String[formulario.getQuantidadeVariaveis_Formulario()];

            String bloco = "BLOCO", fator = "FATOR", nivel = "NIVEL", repeticao = "REPETICAO", replicacao = "REPLICA";


            if (formulario.getQuantidadeBlocos_Formulario() <= 1 && formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{fator, nivel, repeticao};
            else if (formulario.getQuantidadeBlocos_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{fator, nivel, repeticao, replicacao};
            else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, fator, nivel};
            else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, fator, nivel, repeticao};
            else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, fator, nivel, replicacao};
            else
                cabecalhoPosicoes = new String[]{bloco, fator, nivel, repeticao, replicacao};

            for (int v = 0; v < formulario.getQuantidadeVariaveis_Formulario(); v++) {
                cabecalhoNomeVariaveis[v] = variaveis.get(v).getNome_Variavel();
            }
            linhas.add(ArrayUtils.addAll(cabecalhoPosicoes, cabecalhoNomeVariaveis));


            if (formulario.getQuantidadeBlocos_Formulario() != -1) {
                for (int bloc = 0; bloc < formulario.getQuantidadeBlocos_Formulario(); bloc++) {
                    for (int fat = 0; fat < formulario.getQuantidadeFatores_Formulario(); fat++) {
                        ArrayList<NivelFator> niveis = dbAuxilar.lerTodosNiveisFator(fatores.get(fat).getId_Fator());
                        for (int niv = 0; niv < niveis.size(); niv++) {
                            for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                                for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                    String[] posicoes;


                                    if (formulario.getQuantidadeBlocos_Formulario() <= 1 && formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                        posicoes = new String[]{fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(rep + 1)};
                                    else if (formulario.getQuantidadeBlocos_Formulario() <= 1)
                                        posicoes = new String[]{fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                    else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator()};
                                    else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(rep + 1)};
                                    else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(repli + 1)};
                                    else
                                        posicoes = new String[]{String.valueOf(bloc + 1), fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(rep + 1), String.valueOf(repli + 1)};

                                    String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                    for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                        valores[var] = dbAuxilar.lerValorDadoFatorial(fatores.get(fat).getId_Fator(), niveis.get(niv).getId_NivelFator(), bloc, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                                    }

                                    String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                    linhas.add(colunas);
                                }
                            }
                        }
                    }
                }
            } else {
                for (int fat = 0; fat < formulario.getQuantidadeFatores_Formulario(); fat++) {
                    ArrayList<NivelFator> niveis = dbAuxilar.lerTodosNiveisFator(fatores.get(fat).getId_Fator());
                    for (int niv = 0; niv < niveis.size(); niv++) {
                        for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                            for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                String[] posicoes;

                                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                    posicoes = new String[]{fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator()};
                                else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                    posicoes = new String[]{fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(rep + 1)};
                                else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                    posicoes = new String[]{fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(repli + 1)};
                                else
                                    posicoes = new String[]{fatores.get(fat).getNome_Fator(), niveis.get(niv).getNome_NivelFator(), String.valueOf(rep + 1), String.valueOf(repli + 1)};

                                String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                    valores[var] = dbAuxilar.lerValorDadoFatorial(fatores.get(fat).getId_Fator(), niveis.get(niv).getId_NivelFator(), 0, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                                }

                                String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                linhas.add(colunas);
                            }
                        }
                    }
                }

            }


            writer.writeAll(linhas);
            writer.close();
            Log.i("Witter", "ArquivoGerado");
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }
*/

    private String escreverArquivoParcela(Formulario formulario, Coleta coleta, ArrayList<Parcela> parcelas, ArrayList<Variavel> variaveis) {
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            List<String[]> linhas = new ArrayList<>();
            String[] cabecalhoPosicoes = new String[]{}, cabecalhoNomeVariaveis = new String[formulario.getQuantidadeVariaveis_Formulario()];

            String bloco = "BLOCO", parcela = "PARCELA", nivel = "NIVEL", repeticao = "REPETICAO", replicacao = "REPLICA";


            if (formulario.getQuantidadeBlocos_Formulario() <= 1 && formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{parcela, nivel, repeticao};
            else if (formulario.getQuantidadeBlocos_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{parcela, nivel, repeticao, replicacao};
            else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, parcela, nivel};
            else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, parcela, nivel, repeticao};
            else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                cabecalhoPosicoes = new String[]{bloco, parcela, nivel, replicacao};
            else
                cabecalhoPosicoes = new String[]{bloco, parcela, nivel, repeticao, replicacao};

            for (int v = 0; v < formulario.getQuantidadeVariaveis_Formulario(); v++) {
                cabecalhoNomeVariaveis[v] = variaveis.get(v).getNome_Variavel();
            }
            linhas.add(ArrayUtils.addAll(cabecalhoPosicoes, cabecalhoNomeVariaveis));


            if (formulario.getQuantidadeBlocos_Formulario() != -1) {
                for (int bloc = 0; bloc < formulario.getQuantidadeBlocos_Formulario(); bloc++) {
                    for (int fat = 0; fat < formulario.getQuantidadeParcelas_Formulario(); fat++) {
                        ArrayList<NivelParcela> niveis = dbAuxilar.lerTodosNiveisParcela(parcelas.get(fat).getId_Parcela());
                        for (int niv = 0; niv < niveis.size(); niv++) {
                            for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                                for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                    String[] posicoes;


                                    if (formulario.getQuantidadeBlocos_Formulario() <= 1 && formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                        posicoes = new String[]{parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(rep + 1)};
                                    else if (formulario.getQuantidadeBlocos_Formulario() <= 1)
                                        posicoes = new String[]{parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(rep + 1), String.valueOf(repli + 1)};
                                    else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela()};
                                    else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(rep + 1)};
                                    else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                        posicoes = new String[]{String.valueOf(bloc + 1), parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(repli + 1)};
                                    else
                                        posicoes = new String[]{String.valueOf(bloc + 1), parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(rep + 1), String.valueOf(repli + 1)};

                                    String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                    for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                        valores[var] = dbAuxilar.lerValorDadoParcela(parcelas.get(fat).getId_Parcela(), niveis.get(niv).getId_NivelParcela(), bloc, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                                    }

                                    String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                    linhas.add(colunas);
                                }
                            }
                        }
                    }
                }
            } else {
                for (int fat = 0; fat < formulario.getQuantidadeParcelas_Formulario(); fat++) {
                    ArrayList<NivelParcela> niveis = dbAuxilar.lerTodosNiveisParcela(parcelas.get(fat).getId_Parcela());
                    for (int niv = 0; niv < niveis.size(); niv++) {
                        for (int rep = 0; rep < formulario.getQuantidadeRepeticoes_Formulario(); rep++) {
                            for (int repli = 0; repli < formulario.getQuantidadeReplicacoes_Formulario(); repli++) {
                                String[] posicoes;

                                if (formulario.getQuantidadeReplicacoes_Formulario() <= 1 && formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                    posicoes = new String[]{parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela()};
                                else if (formulario.getQuantidadeReplicacoes_Formulario() <= 1)
                                    posicoes = new String[]{parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(rep + 1)};
                                else if (formulario.getQuantidadeRepeticoes_Formulario() <= 1)
                                    posicoes = new String[]{parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(repli + 1)};
                                else
                                    posicoes = new String[]{parcelas.get(fat).getNome_Parcela(), niveis.get(niv).getNome_NivelParcela(), String.valueOf(rep + 1), String.valueOf(repli + 1)};

                                String[] valores = new String[formulario.getQuantidadeVariaveis_Formulario()];
                                for (int var = 0; var < formulario.getQuantidadeVariaveis_Formulario(); var++) {
                                    valores[var] = dbAuxilar.lerValorDadoParcela(parcelas.get(fat).getId_Parcela(), niveis.get(niv).getId_NivelParcela(), 0, rep, repli, variaveis.get(var).getId_Variavel(), coleta.getId_Coleta()).getValor_Dado();
                                }

                                String[] colunas = ArrayUtils.addAll(posicoes, valores);
                                linhas.add(colunas);
                            }
                        }
                    }
                }

            }


            writer.writeAll(linhas);
            writer.close();
            Log.i("Witter", "ArquivoGerado");
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }


}
