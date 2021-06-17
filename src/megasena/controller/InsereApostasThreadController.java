package megasena.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import megasena.conexao.Conexao;
import megasena.util.Constantes;
import megasena.util.SQLUtil;
import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class InsereApostasThreadController implements Runnable {
    private final MegaSenaView megaV;
    private final int qtdeApostasPorThread;
    private final int numConcurso;

    public InsereApostasThreadController(MegaSenaView megaV, int qtdeApostasPorThread, int numConcurso) {
        this.megaV = megaV;
        this.qtdeApostasPorThread = qtdeApostasPorThread;
        this.numConcurso = numConcurso;
    }

    @Override
    public void run() {
        Conexao con = null;
        try {
            con = new Conexao(false);
        } catch (SQLException ex) {
            megaV.getBarraDeProgresso().setValue(0);
            JOptionPane.showMessageDialog(null, "Error at InsereApostasThreadController.java(line 38):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }
        
        List<Integer> numerosAposta;
        
        for (int i = 0; i < qtdeApostasPorThread; i++) {
            numerosAposta = new ArrayList<>();
            Constantes.setQTDE_APOSTAS(Constantes.getQTDE_APOSTAS() + 1);
            Constantes.setQTDE_APOSTAS_TOTAL((Constantes.getQTDE_APOSTAS_TOTAL() + 1));

            if (Constantes.getQTDE_APOSTAS() >= Constantes.getAPOSTA_MAIOR()) {
                Constantes.setQTDE_APOSTAS(0);
                gerarNumerosRandomicamente(numerosAposta, true);
            } else {
                gerarNumerosRandomicamente(numerosAposta, false);
            }
            
            String sqlInsert = new SQLUtil().retornaInsert(numerosAposta, numConcurso);
            
            try (PreparedStatement st = con.conexao.prepareStatement(sqlInsert)) {
                st.executeUpdate();
                if(i % Constantes.getQTDE_REGS_COMMIT() == 0){
                    con.conexao.commit();
                }
                st.close();
            } catch (SQLException ex) {
                megaV.getBarraDeProgresso().setValue(0);
                JOptionPane.showMessageDialog(null, "Error at InsereApostasThreadController.java(line 65):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
            }
            
            numerosAposta = new ArrayList<>();
        }
        try {
            con.conexao.commit();
            con.conexao.close();
        } catch (SQLException ex) {
            megaV.getBarraDeProgresso().setValue(0);
            JOptionPane.showMessageDialog(null, "Error at InsereApostasThreadController.java(line 75):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }
        Constantes.setQTDE_APOSTAS_TOTAL(Integer.parseInt(megaV.getTxtQtdeApostas().getText()));
    }

    private void gerarNumerosRandomicamente(List<Integer> numerosAposta, boolean isApostaMaior) {
        if(isApostaMaior){
            int qtdeNumerosJogo = new Random().nextInt((Constantes.getMAX_NUM_AP_MAIOR() - Constantes.getMIN_NUM_AP_MAIOR()) + 1) + Constantes.getMIN_NUM_AP_MAIOR();
            for(int i = 0; i < qtdeNumerosJogo; i++){
                int numAtual = new Random().nextInt((Constantes.getNUM_MAX_APOSTA() - Constantes.getNUM_MIN_APOSTA()) + 1) + Constantes.getNUM_MIN_APOSTA(); 
                if(!numerosAposta.contains(numAtual)){
                    numerosAposta.add(numAtual);
                }else{
                    i--;
                }
            }
        }else{
            for(int i = 0; i < 6; i++){
                int numAtual = new Random().nextInt((Constantes.getNUM_MAX_APOSTA() - Constantes.getNUM_MIN_APOSTA()) + 1) + Constantes.getNUM_MIN_APOSTA(); 
                if(!numerosAposta.contains(numAtual)){
                    numerosAposta.add(numAtual);
                }else{
                    i--;
                }
            }
        }
    }
}
