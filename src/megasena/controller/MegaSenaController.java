package megasena.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import megasena.util.Constantes;
import megasena.view.AjudaView;
import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class MegaSenaController {
    private ActionListener actionListener;
    private FocusListener focusListener;
    private final MegaSenaView megaV;
    
    public MegaSenaController(MegaSenaView megaV){
        this.megaV = megaV;
        
        actionBtnAjudaGerarApostas();
        actionBtnGerarApostas();
        actionBtnAjudaValidarApostas();
        actionBtnValidarApostas();
        actionBtnAjudaCombinacoesPossiveis();
        actionBtnCombinacoesPossiveis();
        
        focusLostJTextFields();
        focusGainedAllJTextFields();
        
        ativaReconhecimentoTeclaRecriaBase();
        
        this.megaV.getTxtNum1Sorteio().requestFocus();
        
        new Thread(mudaCorNomeDevConstantemente).start();
    }

    private void actionBtnAjudaGerarApostas() {
        actionListener = (ActionEvent ae) -> {
            AjudaView ajudaV = new AjudaView();
            ajudaV.getLblAjudaTitulo().setText("Ajuda - Gerar Apostas:");
            ajudaV.getTxtAjuda().setText("Para gerar as apostas, deverá preencher os \ncampos:\n\n-Qtde de Apostas\n-Nº de Threads\n-Concurso\n\nApós preenchê-los, clique em Gerar Apostas.\n--\nEsta funcionalidade gerará apostas\naleatórias e as salvará no\nbanco de dados.\n\nA Qtde de Apostas informado será dividido\npelo Nº de Threads e será disparado\nprocessos concorrentes para otimizar a\ninserção das apostas.");
            ajudaV.getTxtAjuda().select(0, 0); 
            ajudaV.setVisible(true);
        };     
        megaV.getBtnAjudaGerarApostas().addActionListener(actionListener);
    }

    private void actionBtnAjudaValidarApostas() {
        actionListener = (ActionEvent ae) -> {
            AjudaView ajudaV = new AjudaView();
            ajudaV.getLblAjudaTitulo().setText("Ajuda - Validar Apostas:");
            ajudaV.getTxtAjuda().setText("Para validar as apostas, deverá preencher os \ncampos:\n\n-Nº s do Sorteio\n-Concurso\n\nApós preenchê-lo, clique em Validar Apostas.\n--\nEsta funcionalidade gerará as combinações \ne consultará no banco de dados a quantidade de\napostas vencedoras para 6, 5 e 4 números.");
            ajudaV.getTxtAjuda().select(0, 0); 
            ajudaV.setVisible(true);
        };     
        megaV.getBtnAjudaValidarApostas().addActionListener(actionListener);
    }

    private void actionBtnAjudaCombinacoesPossiveis() {
        actionListener = (ActionEvent ae) -> {
            AjudaView ajudaV = new AjudaView();
            ajudaV.getLblAjudaTitulo().setText("Ajuda - Combinações Possíveis:");
            ajudaV.getTxtAjuda().setText("Para gerar as combinações possíveis, deverá\npreencher o campo:\n\n-6 Números\n\nApós preenchê-lo, clique em Gerar Combinações.\n--\nEsta funcionalidade gerará as combinações \npossíveis e as listará em tela.");
            ajudaV.getTxtAjuda().select(0, 0); 
            ajudaV.setVisible(true);
        };     
        megaV.getBtnAjudaCombinacoesPossiveis().addActionListener(actionListener);
    }

    private void actionBtnGerarApostas() {
        actionListener = (ActionEvent ae) -> {
            GeraApostasController gerarApostasC = new GeraApostasController(megaV);
        };     
        megaV.getBtnGerarApostas().addActionListener(actionListener);
    }

    private void actionBtnValidarApostas() {
        actionListener = (ActionEvent ae) -> {
            ValidaApostasController apostasVencC =  new ValidaApostasController(megaV);
        };     
        megaV.getBtnValidarApostas().addActionListener(actionListener);
    }

    private void actionBtnCombinacoesPossiveis() {
        actionListener = (ActionEvent ae) -> {
            CombinacoesPossiveisController combPossC = new CombinacoesPossiveisController(megaV);
        };     
        megaV.getBtnGerarCombinacoesPossiveis().addActionListener(actionListener);
    }

    private void focusLostGeneration(JTextField campoLost, JTextField campoGained) {
        focusListener = new FocusListener()  {
            @Override
            public void focusGained(FocusEvent e) {
                //não necessita implementação
            }

            @Override
            public void focusLost(FocusEvent e) {
                campoGained.requestFocus();
                campoGained.selectAll();
            }
        };
        campoLost.addFocusListener(focusListener);
    }
    
    private void focusLostJTextFields(){
        focusLostGeneration(megaV.getTxtNum6CombinacoesPossiveis(), megaV.getTxtNum1CombinacoesPossiveis());
        focusLostGeneration(megaV.getTxtNumThreads(), megaV.getTxtQtdeApostas());
        focusLostGeneration(megaV.getTxtNumConcursoValidarApostas(), megaV.getTxtNum1Sorteio());
        focusLostGeneration(megaV.getTxtNum6Sorteio(), megaV.getTxtNumConcursoValidarApostas());
    }
    
    private void focusGainedGeneration(JTextField campoTxt) {
        focusListener = new FocusListener()  {
            @Override
            public void focusGained(FocusEvent e) {
                campoTxt.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                //não necessita implementação
            }
        };
        campoTxt.addFocusListener(focusListener);
    }
    
    private void focusGainedAllJTextFields() {
        //Gerar Apostas
        focusGainedGeneration(megaV.getTxtQtdeApostas());
        focusGainedGeneration(megaV.getTxtNumConcursoGerarApostas());
        focusGainedGeneration(megaV.getTxtNumThreads());
        
        //Validar Apostas
        focusGainedGeneration(megaV.getTxtNum1Sorteio());
        focusGainedGeneration(megaV.getTxtNum2Sorteio());
        focusGainedGeneration(megaV.getTxtNum3Sorteio());
        focusGainedGeneration(megaV.getTxtNum4Sorteio());
        focusGainedGeneration(megaV.getTxtNum5Sorteio());
        focusGainedGeneration(megaV.getTxtNum6Sorteio());
        focusGainedGeneration(megaV.getTxtNumConcursoValidarApostas());
        
        //Combinações Possíveis
        focusGainedGeneration(megaV.getTxtNum1CombinacoesPossiveis());
        focusGainedGeneration(megaV.getTxtNum2CombinacoesPossiveis());
        focusGainedGeneration(megaV.getTxtNum3CombinacoesPossiveis());
        focusGainedGeneration(megaV.getTxtNum4CombinacoesPossiveis());
        focusGainedGeneration(megaV.getTxtNum5CombinacoesPossiveis());
        focusGainedGeneration(megaV.getTxtNum6CombinacoesPossiveis());
    }
    
    private void ativaReconhecimentoTeclaRecriaBase() {
        megaV.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("alt F2"), "RecriaDataBaseController");
        megaV.getRootPane().getActionMap().put("RecriaDataBaseController", new AbstractAction("RecriaDataBaseController") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constantes.isHaProcessamentoComConexaoAberta()) {
                    if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja recriar a base 'mega-sena'?\n\nObs: os dados atuais serão perdidos.", "Confirmação de recriação de base", JOptionPane.YES_NO_OPTION) == 0) {
                        new RecriaDataBaseController(megaV);
                        megaV.getTxtQtdeApostas().requestFocus();
                    }
                }
            }
        });
    }
    
    private final Runnable mudaCorNomeDevConstantemente = new Runnable() {
        @Override
        public void run() {
            while(true){
                megaV.getLblNomeDesenvolvedor().setForeground(new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Error at MegaSenaController.java(line 187):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };
}
