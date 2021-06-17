package megasena.controller;

import bsh.EvalError;
import bsh.Interpreter;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import javax.swing.JOptionPane;
import megasena.util.GeradorDeCodigoCombinacao;
import megasena.util.LimparCamposTela;
import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class CombinacoesPossiveisController {
    private GeradorDeCodigoCombinacao geradorCombinacoes = new GeradorDeCodigoCombinacao();
    private List<List<Integer>> todosIndicesCombinatorios = new ArrayList<>();
    private final List<Integer> numerosDigitados;
    private final Interpreter geradorDeCodigo;
    private final LimparCamposTela limparCampos;
    private final MegaSenaView megaV;
    
    public CombinacoesPossiveisController(MegaSenaView megaV){
        this.megaV = megaV;
        geradorDeCodigo = new Interpreter();
        numerosDigitados = new ArrayList<>();
        limparCampos = new LimparCamposTela(this.megaV);
        if(!validarCamposPreenchidos()){
            return;
        }
        gerarCombinacoes();
    }

    private void limparCampos() {
        limparCampos.LimparCamposGerarApostas();
        limparCampos.LimparCamposValidarApostas();
    }

    private void gerarCombinacoes() {
        obterNumerosEOrdenar();
        if (!validarNumerosRepetidos()) {
            JOptionPane.showMessageDialog(null, "Foram identificados números repetidos.\n\n".concat(numerosDigitados.toString()), "Falha", JOptionPane.ERROR_MESSAGE);
            megaV.getBarraDeProgresso().setValue(0);
            limparCampos.LimparCamposCombinacoesPossiveis();
            return;
        }
        gerarCombinacoes5Numeros();
        gerarCombinacoes4Numeros();
        megaV.getLblTempoExecução().setText("Tempo de execução: 0 s.");
    }

    private boolean validarCamposPreenchidos() {
        limparCampos();
        if( !megaV.getTxtNum1CombinacoesPossiveis().getText().trim().equals("") &&
            !megaV.getTxtNum2CombinacoesPossiveis().getText().trim().equals("") &&
            !megaV.getTxtNum3CombinacoesPossiveis().getText().trim().equals("") &&
            !megaV.getTxtNum4CombinacoesPossiveis().getText().trim().equals("") &&
            !megaV.getTxtNum5CombinacoesPossiveis().getText().trim().equals("") &&
            !megaV.getTxtNum6CombinacoesPossiveis().getText().trim().equals("") &&
            intervaloValido(megaV.getTxtNum1CombinacoesPossiveis().getText().trim()) &&
            intervaloValido(megaV.getTxtNum2CombinacoesPossiveis().getText().trim()) &&
            intervaloValido(megaV.getTxtNum3CombinacoesPossiveis().getText().trim()) &&
            intervaloValido(megaV.getTxtNum4CombinacoesPossiveis().getText().trim()) &&
            intervaloValido(megaV.getTxtNum5CombinacoesPossiveis().getText().trim()) &&
            intervaloValido(megaV.getTxtNum6CombinacoesPossiveis().getText().trim())  
        ){
            megaV.getBarraDeProgresso().setValue(50);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Certifique-se de que todos os números solicitados foram preenchidos.", "Falha", JOptionPane.ERROR_MESSAGE);
            megaV.getBarraDeProgresso().setValue(0);
            limparCampos.LimparCamposCombinacoesPossiveis();
            return false;
        }
    }

    private void obterNumerosEOrdenar() {
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum1CombinacoesPossiveis().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum2CombinacoesPossiveis().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum3CombinacoesPossiveis().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum4CombinacoesPossiveis().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum5CombinacoesPossiveis().getText()));
        numerosDigitados.add(Integer.parseInt(megaV.getTxtNum6CombinacoesPossiveis().getText()));
        
        Comparator<? super Integer> c = null;
        numerosDigitados.sort(c);
        
        megaV.getTxtNum1CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(0)));
        megaV.getTxtNum2CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(1)));
        megaV.getTxtNum3CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(2)));
        megaV.getTxtNum4CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(3)));
        megaV.getTxtNum5CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(4)));
        megaV.getTxtNum6CombinacoesPossiveis().setText(String.valueOf(numerosDigitados.get(5)));
        
        megaV.getBarraDeProgresso().setValue(60);
    }
    
    private void gerarCombinacoes5Numeros() {
        String codigoGeradoDinamicamente = geradorCombinacoes.gerardorDeCodigoCombinacao(6, 5);
        
        //Gerando combinações
        try {
            geradorDeCodigo.set("todosIndicesCombinatorios", todosIndicesCombinatorios);
            geradorDeCodigo.eval(codigoGeradoDinamicamente);
        } catch (EvalError ex) {
            JOptionPane.showMessageDialog(null, "Error at CombinacoesPossiveisController.java(line 111):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }
        
        //Setando os dados nos campos
        for(int i = 0; i < todosIndicesCombinatorios.size(); i++){
            StringBuilder textoCampo = new StringBuilder();
            for(int j = 0; j < todosIndicesCombinatorios.get(i).size(); j++){
                textoCampo.append(String.valueOf(numerosDigitados.get(todosIndicesCombinatorios.get(i).get(j))))
                          .append((j < (todosIndicesCombinatorios.get(i).size() - 1)) ? " - " : "");
            }
            switch (i) {
                case 0:
                    megaV.getTxt5Numeros1CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 1:
                    megaV.getTxt5Numeros2CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 2:
                    megaV.getTxt5Numeros3CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 3:
                    megaV.getTxt5Numeros4CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 4:
                    megaV.getTxt5Numeros5CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 5:
                    megaV.getTxt5Numeros6CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
            }
        }
        todosIndicesCombinatorios = new ArrayList<>();
        megaV.getBarraDeProgresso().setValue(80);
    }

    private void gerarCombinacoes4Numeros() {
        String codigoGeradoDinamicamente = geradorCombinacoes.gerardorDeCodigoCombinacao(6, 4);
        
        //Gerando combinações
        try {
            geradorDeCodigo.set("todosIndicesCombinatorios", todosIndicesCombinatorios);
            geradorDeCodigo.eval(codigoGeradoDinamicamente);
        } catch (EvalError ex) {
            JOptionPane.showMessageDialog(null, "Error at CombinacoesPossiveisController.java(line 154):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }
        
        //Setando os dados nos campos
        for(int i = 0; i < todosIndicesCombinatorios.size(); i++){
            StringBuilder textoCampo = new StringBuilder();
            for(int j = 0; j < todosIndicesCombinatorios.get(i).size(); j++){
                textoCampo.append(String.valueOf(numerosDigitados.get(todosIndicesCombinatorios.get(i).get(j))))
                          .append((j < (todosIndicesCombinatorios.get(i).size() - 1)) ? " - " : "");
            }
            switch (i) {
                case 0:
                    megaV.getTxt4Numeros1CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 1:
                    megaV.getTxt4Numeros2CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 2:
                    megaV.getTxt4Numeros3CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 3:
                    megaV.getTxt4Numeros4CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 4:
                    megaV.getTxt4Numeros5CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 5:
                    megaV.getTxt4Numeros6CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 6:
                    megaV.getTxt4Numeros7CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 7:
                    megaV.getTxt4Numeros8CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 8:
                    megaV.getTxt4Numeros9CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 9:
                    megaV.getTxt4Numeros10CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 10:
                    megaV.getTxt4Numeros11CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 11:
                    megaV.getTxt4Numeros12CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 12:
                    megaV.getTxt4Numeros13CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 13:
                    megaV.getTxt4Numeros14CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
                case 14:
                    megaV.getTxt4Numeros15CombinacoesPossiveis().setText(textoCampo.toString());
                    break;
            }
        }
        todosIndicesCombinatorios = new ArrayList<>();
        megaV.getBarraDeProgresso().setValue(100);
    }

    private boolean intervaloValido(String numFornecido) {
        int num = Integer.parseInt(numFornecido);
        return num >= 1 && num <= 60;
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
    
    public void gerarCombinacoesAoValidarApostas(){
        gerarCombinacoes4Numeros();
        gerarCombinacoes5Numeros();
    }
}
