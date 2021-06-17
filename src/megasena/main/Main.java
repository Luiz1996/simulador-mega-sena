package megasena.main;

import java.io.IOException;
import java.sql.SQLException;
import megasena.controller.MegaSenaController;
import megasena.util.Constantes;
import megasena.view.MegaSenaView;

/**
 *
 * @author Luiz Flávio
 */
public class Main {
    public static long tempoInicial = 0;
    public static long tempoFinal = 0;
    
    public static void main(String[] args) throws SQLException, IOException {
        //realiza a leitura das configurações para conexão com banco de dados MySQL
        new Constantes().getPropValues(args);
       
        //iniciando aplicação
        MegaSenaView megaV = new MegaSenaView();
        MegaSenaController megaC = new MegaSenaController(megaV);
    }
}
