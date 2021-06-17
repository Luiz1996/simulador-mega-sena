package megasena.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Luiz Flávio
 */
public class Constantes {

    //dados banco de dados
    private static String IP_SERVER;
    private static String PORT;
    private static String USER;
    private static String PASSWORD;
    private static String SCHEMA;
    private static int QTDE_REGS_COMMIT;
    
    //dados jogos/apostas
    private static int APOSTA_MAIOR;
    private static int QTDE_APOSTAS = 0;
    private static int QTDE_APOSTAS_TOTAL = 0;
    private static final int NUM_MIN_APOSTA = 1;
    private static final int NUM_MAX_APOSTA = 60;
    private static final int MIN_NUM_AP_MAIOR = 7;
    private static final int MAX_NUM_AP_MAIOR = 15;
    
    private static boolean haProcessamentoComConexaoAberta = false;

    public static String getIP_SERVER() {
        return IP_SERVER;
    }

    public static void setIP_SERVER(String IP_SERVER) {
        Constantes.IP_SERVER = IP_SERVER;
    }

    public static String getPORT() {
        return PORT;
    }

    public static void setPORT(String PORT) {
        Constantes.PORT = PORT;
    }

    public static String getUSER() {
        return USER;
    }

    public static void setUSER(String USER) {
        Constantes.USER = USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        Constantes.PASSWORD = PASSWORD;
    }

    public static String getSCHEMA() {
        return SCHEMA;
    }

    public static void setSCHEMA(String SCHEMA) {
        Constantes.SCHEMA = SCHEMA;
    }

    public static int getAPOSTA_MAIOR() {
        return APOSTA_MAIOR;
    }

    public static void setAPOSTA_MAIOR(int APOSTA_MAIOR) {
        Constantes.APOSTA_MAIOR = APOSTA_MAIOR;
    }

    public static int getQTDE_APOSTAS() {
        return QTDE_APOSTAS;
    }

    public static void setQTDE_APOSTAS(int QTDE_APOSTAS) {
        Constantes.QTDE_APOSTAS = QTDE_APOSTAS;
    }

    public static int getNUM_MIN_APOSTA() {
        return NUM_MIN_APOSTA;
    }

    public static int getNUM_MAX_APOSTA() {
        return NUM_MAX_APOSTA;
    }

    public static int getMIN_NUM_AP_MAIOR() {
        return MIN_NUM_AP_MAIOR;
    }

    public static int getMAX_NUM_AP_MAIOR() {
        return MAX_NUM_AP_MAIOR;
    }

    public static int getQTDE_REGS_COMMIT() {
        return QTDE_REGS_COMMIT;
    }

    public static void setQTDE_REGS_COMMIT(int QTDE_REGS_COMMIT) {
        Constantes.QTDE_REGS_COMMIT = QTDE_REGS_COMMIT;
    }

    public static int getQTDE_APOSTAS_TOTAL() {
        return QTDE_APOSTAS_TOTAL;
    }

    public static void setQTDE_APOSTAS_TOTAL(int QTDE_APOSTAS_TOTAL) {
        Constantes.QTDE_APOSTAS_TOTAL = QTDE_APOSTAS_TOTAL;
    }

    public static boolean isHaProcessamentoComConexaoAberta() {
        return haProcessamentoComConexaoAberta;
    }

    public static void setHaProcessamentoComConexaoAberta(boolean haProcessamentoComConexaoAberta) {
        Constantes.haProcessamentoComConexaoAberta = haProcessamentoComConexaoAberta;
    }
    
    public void getPropValues(String[] args) throws IOException {
        Properties prop = new Properties();
        String propFileName;

        if (args.length == 0) {
            propFileName = "C:\\Users\\Luiz Flávio\\Documents\\NetBeansProjects\\mega-sena\\resources\\config\\config.properties";
        } else {
            propFileName = args[0];
        }

        try (FileReader in = new FileReader(propFileName)) {
            prop.load(in);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error at Constantes.java(line 132):\n\n".concat(ex.getMessage()), "Falha", JOptionPane.ERROR_MESSAGE);
        }

        Constantes.setIP_SERVER(prop.getProperty("server"));
        Constantes.setPORT(prop.getProperty("port"));
        Constantes.setSCHEMA(prop.getProperty("schema"));
        Constantes.setUSER(prop.getProperty("user"));
        Constantes.setPASSWORD(prop.getProperty("password"));
        Constantes.setAPOSTA_MAIOR(Integer.parseInt(prop.getProperty("aposta_maior")));
        Constantes.setQTDE_REGS_COMMIT(Integer.parseInt(prop.getProperty("qtde_regs_commit")));
    }
}
