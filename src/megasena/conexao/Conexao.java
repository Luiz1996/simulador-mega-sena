package megasena.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import megasena.util.Constantes;

/**
 *
 * @author Luiz Flávio
 */
public class Conexao {
    public Connection conexao;
    public Conexao(boolean ignoreSchema) throws SQLException{
        if(ignoreSchema){
            conexao = DriverManager.getConnection("jdbc:mysql://".concat(Constantes.getIP_SERVER()).concat(":").concat(Constantes.getPORT()), Constantes.getUSER(), Constantes.getPASSWORD());
        }else{
            conexao = DriverManager.getConnection("jdbc:mysql://".concat(Constantes.getIP_SERVER()).concat(":").concat(Constantes.getPORT()).concat("/").concat(Constantes.getSCHEMA()), Constantes.getUSER(), Constantes.getPASSWORD());
        }
        conexao.setAutoCommit(false);
    }
}
