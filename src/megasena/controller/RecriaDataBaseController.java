package megasena.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import megasena.conexao.Conexao;
import megasena.util.LimparCamposTela;
import megasena.util.SQLUtil;
import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class RecriaDataBaseController {
    private final MegaSenaView megaV;
    private final SQLUtil sqlUtil = new SQLUtil();
    private int countErrors;
    
    public RecriaDataBaseController(MegaSenaView megaV){
        this.megaV = megaV;
        countErrors = 0;
        recriarDataBase();
    }

    private void recriarDataBase() {
        new LimparCamposTela(megaV).LimparCamposCombinacoesPossiveis();
        new LimparCamposTela(megaV).LimparCamposGerarApostas();
        new LimparCamposTela(megaV).LimparCamposValidarApostas();
        executaConsulta(sqlUtil.retornaDropSchema());
        executaConsulta(sqlUtil.retornaCreateSchema());
        executaConsulta(sqlUtil.retornaUseSchema());
        executaConsulta(sqlUtil.retornaCreateTable());
        if(countErrors == 0){
            megaV.getBarraDeProgresso().setValue(100);
            new LimparCamposTela(megaV).habilitaBotoesTela();
            JOptionPane.showMessageDialog(null, "O banco de dados 'mega-sena' foi recriado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Ocorreram erros durante a recriação da base, contacte o administrador!", "Falha", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void executaConsulta(String sql){
        try {
            Conexao con = new Conexao(true);
            PreparedStatement st = con.conexao.prepareStatement(sql);
            st.executeUpdate();
            con.conexao.commit();
            st.close();
            con.conexao.close();
        } catch (SQLException ex) {
            countErrors++;
            JOptionPane.showMessageDialog(null, "Error at RecriaDataBaseController.java(line 53):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }
    }
}
