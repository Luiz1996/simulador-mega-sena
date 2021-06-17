package megasena.util;

import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class LimparCamposTela {
    private final MegaSenaView megaV;
    
    public LimparCamposTela(MegaSenaView megaV){
        this.megaV = megaV;
    }
    
    public void LimparCamposGerarApostas(){
        megaV.getTxtQtdeApostas().setText("");
        megaV.getTxtNumThreads().setText("");
        megaV.getTxtNumConcursoGerarApostas().setText("");
    }
    
    public void LimparCamposValidarApostas(){
        megaV.getTxtNumConcursoValidarApostas().setText("");
        megaV.getTxtNum1Sorteio().setText("");
        megaV.getTxtNum2Sorteio().setText("");
        megaV.getTxtNum3Sorteio().setText("");
        megaV.getTxtNum4Sorteio().setText("");
        megaV.getTxtNum5Sorteio().setText("");
        megaV.getTxtNum6Sorteio().setText("");
        megaV.getTxt4Numeros().setText("");
        megaV.getTxt5Numeros().setText("");
        megaV.getTxt6Numeros().setText("");
    }
    
    public void LimparCamposCombinacoesPossiveis(){
        megaV.getTxtNum1CombinacoesPossiveis().setText("");
        megaV.getTxtNum2CombinacoesPossiveis().setText("");
        megaV.getTxtNum3CombinacoesPossiveis().setText("");
        megaV.getTxtNum4CombinacoesPossiveis().setText("");
        megaV.getTxtNum5CombinacoesPossiveis().setText("");
        megaV.getTxtNum6CombinacoesPossiveis().setText("");
        
        megaV.getTxt5Numeros1CombinacoesPossiveis().setText("");
        megaV.getTxt5Numeros2CombinacoesPossiveis().setText("");
        megaV.getTxt5Numeros3CombinacoesPossiveis().setText("");
        megaV.getTxt5Numeros4CombinacoesPossiveis().setText("");
        megaV.getTxt5Numeros5CombinacoesPossiveis().setText("");
        megaV.getTxt5Numeros6CombinacoesPossiveis().setText("");
        
        megaV.getTxt4Numeros1CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros2CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros3CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros4CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros5CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros6CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros7CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros8CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros9CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros10CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros11CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros12CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros13CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros14CombinacoesPossiveis().setText("");
        megaV.getTxt4Numeros15CombinacoesPossiveis().setText("");
    }
    
    public void desabilitaBotoesTela(){
        megaV.getBtnGerarApostas().setEnabled(false);
        megaV.getBtnGerarCombinacoesPossiveis().setEnabled(false);
        megaV.getBtnValidarApostas().setEnabled(false);
        Constantes.setHaProcessamentoComConexaoAberta(true);
    }
    
    public void habilitaBotoesTela(){
        megaV.getBtnGerarApostas().setEnabled(true);
        megaV.getBtnGerarCombinacoesPossiveis().setEnabled(true);
        megaV.getBtnValidarApostas().setEnabled(true);
        Constantes.setHaProcessamentoComConexaoAberta(false);
    }
}
