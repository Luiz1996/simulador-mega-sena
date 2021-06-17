package megasena.util;

import java.util.List;

/**
 *
 * @author Luiz Flávio
 */
public class SQLUtil {
    
    public String retornaWhere(int num){
        return " and num".concat(String.valueOf(num).concat(" = 'S' "));
    }
    
    public String retornaWhereSemAnd(int num){
        return " num".concat(String.valueOf(num).concat(" = 'S' "));
    }
    
    public String retornaWhereFalha(int num){
        return " and num".concat(String.valueOf(num).concat(" = 'N' "));
    }
    
    public String retornaInsert(List<Integer> numerosAposta, int numConcurso){
        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("INSERT INTO aposta_jogo (num_concurso, ");
        
        for(int i = 0; i < numerosAposta.size(); i++){
            sqlInsert.append("num")
                     .append(numerosAposta.get(i))
                     .append(", ");
        }
        
        sqlInsert.append("numeros_jogados) VALUES (")
                 .append(numConcurso)
                 .append(", ");
        
        numerosAposta.forEach((_item) -> {
            sqlInsert.append("'S', ");
        });
        
        sqlInsert.append(numerosAposta.size())
                 .append(");");
        
        return sqlInsert.toString();
    }
    
    public String retornaTruncateTable(){
        return "truncate table aposta_jogo;";
    }
    
    public String retornaSQLBaseConsultaAcertos(){
        return "SELECT COALESCE(COUNT(*), 0) as quantidade FROM aposta_jogo WHERE num_concurso = ?";
    }
    
    public String retornaDropSchema(){
        return "DROP SCHEMA IF EXISTS `mega-sena`";
    }
    
    public String retornaCreateSchema(){
        return "CREATE SCHEMA IF NOT EXISTS `mega-sena`";
    }

    public String retornaUseSchema() {
        return "USE `mega-sena`";
    }
    
    public String retornaCreateTable(){
        return  "CREATE TABLE IF NOT EXISTS `mega-sena`.`aposta_jogo` (\n" +
                "  `id_jogo`         INT     NOT NULL AUTO_INCREMENT COMMENT 'Coluna responsável por identificar unicamente cada aposta registrada no banco de dados.',\n" +
                "  `num_concurso`    INT     NOT NULL                COMMENT 'Coluna responsável por identificar o concurso a qual se refere a aposta efetuada no sistema.',\n" +
                "  `num1`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num2`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num3`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num4`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num5`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num6`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num7`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num8`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num9`            CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num10`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num11`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num12`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num13`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num14`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num15`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num16`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num17`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num18`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num19`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num20`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num21`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num22`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num23`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num24`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num25`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num26`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num27`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num28`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num29`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num30`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num31`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num32`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num33`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num34`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num35`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num36`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num37`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num38`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num39`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num40`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num41`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num42`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num43`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num44`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num45`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num46`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num47`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num48`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num49`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num50`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num51`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num52`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num53`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num54`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num55`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num56`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num57`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num58`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num59`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `num60`           CHAR(1) NOT NULL DEFAULT 'N'    COMMENT 'Coluna que identificará com S ou N se o jogador apostou no número que dá nome à coluna.',\n" +
                "  `numeros_jogados` INT     NOT NULL                COMMENT 'Coluna responsável por identificar quantos números foram escolhidos nessa aposta, permitido intervalo de 6 à 15.',\n" +
                "  PRIMARY KEY (`id_jogo`));";
    }
}
