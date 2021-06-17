package megasena.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import megasena.conexao.Conexao;
import megasena.main.Main;
import megasena.util.Constantes;
import megasena.util.LimparCamposTela;
import megasena.util.SQLUtil;
import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class GeraApostasController {
    private final LimparCamposTela limparCampos;
    private final MegaSenaView megaV;
    
    public GeraApostasController(MegaSenaView megaV){
        Constantes.setQTDE_APOSTAS(0);
        Constantes.setQTDE_APOSTAS_TOTAL(0);
        this.megaV = megaV;
        this.megaV.getBarraDeProgresso().setValue(0);
        this.megaV.getLblTempoExecução().setText("Tempo de execução: 0 s.");
        limparCampos = new LimparCamposTela(this.megaV);
        limparCampos();
        if(!validarCamposPreenchidos()){
            return;
        }
        iniciarProcessamento();
    }

    private void limparCampos() {
        limparCampos.LimparCamposValidarApostas();
        limparCampos.LimparCamposCombinacoesPossiveis();
    }

    private boolean validarCamposPreenchidos() {
        if(!"".equals(megaV.getTxtQtdeApostas().getText().trim()) &&
           !"".equals(megaV.getTxtNumConcursoGerarApostas().getText().trim()) &&
           !"".equals(megaV.getTxtNumThreads().getText().trim()) &&
           Integer.parseInt(megaV.getTxtQtdeApostas().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNumConcursoGerarApostas().getText().trim()) > 0 &&
           Integer.parseInt(megaV.getTxtNumThreads().getText().trim()) > 0)
        {
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Certifique-se de que todos os números solicitados foram preenchidos.", "Falha", JOptionPane.ERROR_MESSAGE);
            megaV.getBarraDeProgresso().setValue(0);
            limparCampos.LimparCamposGerarApostas();
            return false;
        }
    }

    private void iniciarProcessamento() {
        int qtdeApostas = Integer.parseInt(megaV.getTxtQtdeApostas().getText());
        int qtdeThreads = Integer.parseInt(megaV.getTxtNumThreads().getText());
        int qtdeApostasPorThreads = Math.abs(qtdeApostas/qtdeThreads);
        int qtdeExtraThread0 = (qtdeApostas - (qtdeThreads * qtdeApostasPorThreads));
        
        if(qtdeThreads > qtdeApostas){
            JOptionPane.showMessageDialog(null, "O 'Nº de Threads' não pode ser maior que a 'Qtde de Apostas'.", "Falha", JOptionPane.ERROR_MESSAGE);
            limparCampos.LimparCamposGerarApostas();
            return;
        }
        
        Thread minhasThreads[] = new Thread[qtdeThreads];
        
        for (int i = 0; i < minhasThreads.length; i++) {
            minhasThreads[i] = new Thread(new InsereApostasThreadController(megaV, ((i == 0) ? (qtdeApostasPorThreads + qtdeExtraThread0) : qtdeApostasPorThreads), Integer.parseInt(megaV.getTxtNumConcursoGerarApostas().getText())));
        }
        
        validaLimparBase();        
        
        Main.tempoInicial = System.currentTimeMillis();
        
        for (Thread minhasThread : minhasThreads) {
            minhasThread.start();
        }
        
        new Thread(barraProgresso).start();
    }
    
    private final Runnable barraProgresso = new Runnable() {
        @Override
        public void run() {
            limparCampos.desabilitaBotoesTela();
            while(megaV.getBarraDeProgresso().getValue() < 100){
                megaV.getBarraDeProgresso().setValue(Math.abs((Constantes.getQTDE_APOSTAS_TOTAL() * 100) / Integer.parseInt(megaV.getTxtQtdeApostas().getText().trim())));
            }
            Main.tempoFinal = System.currentTimeMillis();
            megaV.getLblTempoExecução().setText("Tempo de execução: " + new SimpleDateFormat("mm:ss").format(new Date(Main.tempoFinal - Main.tempoInicial)) + " s.");
            limparCampos.habilitaBotoesTela();
        }
    };
    
    private void validaLimparBase() {
        if (JOptionPane.showConfirmDialog(null, "Deseja limpar todos os jogos existentes?", "Confirmação de limpeza de base", JOptionPane.YES_NO_OPTION) == 0) {
            try {
                Conexao con = new Conexao(false);
                try (PreparedStatement st = con.conexao.prepareStatement(new SQLUtil().retornaTruncateTable())) {
                    st.executeUpdate();
                    st.close();
                }
                con.conexao.commit();
                con.conexao.close();
            } catch (SQLException ex) {
                megaV.getBarraDeProgresso().setValue(0);
                JOptionPane.showMessageDialog(null, "Error at GerarApostasController.java(line 115):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
