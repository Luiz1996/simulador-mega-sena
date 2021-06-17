package megasena.controller;

import bsh.EvalError;
import bsh.Interpreter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import megasena.conexao.Conexao;
import megasena.main.*;
import megasena.util.GeradorDeCodigoCombinacao;
import megasena.util.LimparCamposTela;
import megasena.util.SQLUtil;
import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class ValidaApostasController {
    private GeradorDeCodigoCombinacao geradorCombinacoes = new GeradorDeCodigoCombinacao();
    private List<List<Integer>> todosIndicesCombinatorios = new ArrayList<>();
    private final List<Integer> numerosDigitados;
    private final LimparCamposTela limparCampos;
    private final Interpreter geradorDeCodigo;
    private final MegaSenaView megaV;
    private boolean teveRepeticao;
    private int num_concurso = 0;
    private String sql4Numeros;
    private String sql5Numeros;
    private String sql6Numeros;
    
    public ValidaApostasController(MegaSenaView megaV){
        this.megaV = megaV;
        geradorDeCodigo = new Interpreter();
        limparCampos = new LimparCamposTela(this.megaV);
        numerosDigitados = new ArrayList<>();
        limparCampos();
        teveRepeticao = false;
        if(!validarCamposPreenchidos()){
            return;
        }
        obterNumerosEOrdenar();
        if(!teveRepeticao){
            iniciarProcessamento();
        }
    }

    private void limparCampos() {
        limparCampos.LimparCamposGerarApostas();
        limparCampos.LimparCamposCombinacoesPossiveis();
    }

    private boolean validarCamposPreenchidos() {
        if(!"".equals(megaV.getTxtNum1Sorteio().getText().trim()) &&
           !"".equals(megaV.getTxtNum2Sorteio().getText().trim()) &&
           !"".equals(megaV.getTxtNum3Sorteio().getText().trim()) &&
           !"".equals(megaV.getTxtNum4Sorteio().getText().trim()) &&
           !"".equals(megaV.getTxtNum5Sorteio().getText().trim()) &&
           !"".equals(megaV.getTxtNum6Sorteio().getText().trim()) &&
           !"".equals(megaV.getTxtNumConcursoValidarApostas().getText().trim()) &&
           Integer.parseInt(megaV.getTxtNum1Sorteio().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNum2Sorteio().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNum3Sorteio().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNum4Sorteio().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNum5Sorteio().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNum6Sorteio().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNumConcursoValidarApostas().getText()) > 0)
        {
            num_concurso = Integer.parseInt(this.megaV.getTxtNumConcursoValidarApostas().getText().trim());
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Certifique-se de que todos os números solicitados foram preenchidos.", "Falha", JOptionPane.ERROR_MESSAGE);
            megaV.getBarraDeProgresso().setValue(0);
            limparCampos.LimparCamposValidarApostas();
            return false;
        }
    }

    private void obterNumerosEOrdenar() {
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum1Sorteio().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum2Sorteio().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum3Sorteio().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum4Sorteio().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum5Sorteio().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum6Sorteio().getText()));
        
        Comparator<? super Integer> c = null;
        numerosDigitados.sort(c);
        
        megaV.getTxtNum1CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(0)));
        megaV.getTxtNum2CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(1)));
        megaV.getTxtNum3CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(2)));
        megaV.getTxtNum4CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(3)));
        megaV.getTxtNum5CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(4)));
        megaV.getTxtNum6CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(5)));
        
        if (!validarNumerosRepetidos()) {
            JOptionPane.showMessageDialog(null, "Foram identificados números repetidos.\n\n".concat(numerosDigitados.toString()), "Falha", JOptionPane.ERROR_MESSAGE);
            megaV.getBarraDeProgresso().setValue(0);
            limparCampos.LimparCamposCombinacoesPossiveis();
            limparCampos.LimparCamposValidarApostas();
            teveRepeticao = true;
            return;
        }
        
        new CombinacoesPossiveisController(megaV).gerarCombinacoesAoValidarApostas();
        megaV.getTxtNumConcursoValidarApostas().setText(String.valueOf(num_concurso));
        
        megaV.getTxtNum1Sorteio().setText(String.valueOf(numerosDigitados.get(0)));
        megaV.getTxtNum2Sorteio().setText(String.valueOf(numerosDigitados.get(1)));
        megaV.getTxtNum3Sorteio().setText(String.valueOf(numerosDigitados.get(2)));
        megaV.getTxtNum4Sorteio().setText(String.valueOf(numerosDigitados.get(3)));
        megaV.getTxtNum5Sorteio().setText(String.valueOf(numerosDigitados.get(4)));
        megaV.getTxtNum6Sorteio().setText(String.valueOf(numerosDigitados.get(5)));
        
        megaV.getBarraDeProgresso().setValue(10);
    }
    
    private boolean validarNumerosRepetidos() {
        for(int i = 0; i < (numerosDigitados.size() - 1); i++){
            for(int j = (i + 1); j < numerosDigitados.size(); j++){
                if(Objects.equals(numerosDigitados.get(i), numerosDigitados.get(j))){
                    return false;
                }
            }
        }
        return true;
    }
    
    private void iniciarProcessamento() {
        sql4Numeros = getSQLConsultaApostasMenores(6, 4);
        sql5Numeros = getSQLConsultaApostasMenores(6, 5);
        sql6Numeros = getSQLConsulta6Numeros();
        
        Main.tempoInicial = System.currentTimeMillis();
        
        limparCampos.desabilitaBotoesTela();
        
        new Thread(consulta4Numeros).start();
        new Thread(consulta5Numeros).start();
        new Thread(consulta6Numeros).start();
    }
    
    private final Runnable consulta4Numeros = new Runnable() {
        @Override
        public void run() {
            megaV.getTxt4Numeros().setText(executaConsulta(sql4Numeros));
            Main.tempoFinal = System.currentTimeMillis();
            megaV.getLblTempoExecução().setText("Tempo de execução: " + new SimpleDateFormat("mm:ss").format(new Date(Main.tempoFinal - Main.tempoInicial)) + " s.");
            if(megaV.getBarraDeProgresso().getValue() < 100){
                megaV.getBarraDeProgresso().setValue(100);
            }
            limparCampos.habilitaBotoesTela();
        }
    };
    
    private final Runnable consulta5Numeros = new Runnable() {
        @Override
        public void run() {
            megaV.getTxt5Numeros().setText(executaConsulta(sql5Numeros));
            if(megaV.getBarraDeProgresso().getValue() < 55){
                megaV.getBarraDeProgresso().setValue(55);
            }
        }
    };
    
    private final Runnable consulta6Numeros = new Runnable() {
        @Override
        public void run() {
            megaV.getTxt6Numeros().setText(executaConsulta(sql6Numeros));
            if(megaV.getBarraDeProgresso().getValue() < 25){
                megaV.getBarraDeProgresso().setValue(25);
            }
        }
    };

    private String getSQLConsulta6Numeros() {
        SQLUtil sqlUtil = new SQLUtil();
        StringBuilder sb = new StringBuilder();
        sb.append(sqlUtil.retornaSQLBaseConsultaAcertos())
          .append(sqlUtil.retornaWhere(numerosDigitados.get(0)))
          .append(sqlUtil.retornaWhere(numerosDigitados.get(1)))
          .append(sqlUtil.retornaWhere(numerosDigitados.get(2)))
          .append(sqlUtil.retornaWhere(numerosDigitados.get(3)))      
          .append(sqlUtil.retornaWhere(numerosDigitados.get(4)))    
          .append(sqlUtil.retornaWhere(numerosDigitados.get(5)))
          .append(";");   
        return sb.toString();
    }

    private String getSQLConsultaApostasMenores(int n, int m) {
        List<Integer> listaIndicesErros;
        String codigoGeradoDinamicamente = geradorCombinacoes.gerardorDeCodigoCombinacao(n, m);
        SQLUtil sqlUtil = new SQLUtil();
        StringBuilder sb = new StringBuilder();
        
        try {
            geradorDeCodigo.set("todosIndicesCombinatorios", todosIndicesCombinatorios);
            geradorDeCodigo.eval(codigoGeradoDinamicamente);
        } catch (EvalError ex) {
            JOptionPane.showMessageDialog(null, "Error at ValidaApostasController.java(line 210):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }
        
        sb.append(sqlUtil.retornaSQLBaseConsultaAcertos());
        
        for(int i = 0; i < todosIndicesCombinatorios.size(); i++){
            sb.append((i == 0) ? " and ((": " (");
            
            for(int j = 0; j < m; j++){
                if(j == 0){
                    sb.append(sqlUtil.retornaWhereSemAnd(numerosDigitados.get(todosIndicesCombinatorios.get(i).get(j))));
                }else{
                    sb.append(sqlUtil.retornaWhere(numerosDigitados.get(todosIndicesCombinatorios.get(i).get(j))));
                }
            }
            
            listaIndicesErros = new ArrayList<>();
            for(int k = 0; k < (n - m); k++){
                sb.append(preencheCasosDeErro(listaIndicesErros, i));
            }
            
            sb.append(")");
            
            if(i != (todosIndicesCombinatorios.size() - 1)){
                sb.append("or");
            }else{
                sb.append(");");
            }
        }
        todosIndicesCombinatorios = new ArrayList<>();
        return sb.toString();
    }

    private String preencheCasosDeErro(List<Integer> listaIndicesErros, int indice) {
        for(int i = 0; i < numerosDigitados.size(); i++){
            if(!todosIndicesCombinatorios.get(indice).contains(i) && !listaIndicesErros.contains(i)){
                listaIndicesErros.add(i);
                return new SQLUtil().retornaWhereFalha(numerosDigitados.get(i));
            }
        }
        return null;
    }
    
    private String executaConsulta(String sql){
        String qtde = "0";
        try {
            Conexao con = new Conexao(false);
            PreparedStatement st = con.conexao.prepareStatement(sql);
            
            st.setInt(1, num_concurso);
            st.executeQuery();
            ResultSet rs = st.getResultSet();
            
            if(rs.next()){
                qtde = String.valueOf(rs.getInt("quantidade"));
            }
            
            rs.close();
            st.close();
            con.conexao.close();
        } catch (SQLException ex) {
            megaV.getBarraDeProgresso().setValue(0);
            JOptionPane.showMessageDialog(null, "Error at ValidaApostasController.java(line 272):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }
        return qtde;
    }
}
