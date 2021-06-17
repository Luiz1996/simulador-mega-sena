package megasena.util;

/**
 *
 * @author Luiz Flávio
 */
public class GeradorDeCodigoCombinacao {
    
    public String gerardorDeCodigoCombinacao(int n, int m){
        StringBuilder sb = new StringBuilder();
        
        //Variáveis auxiliares
        int contador = 1;
        int contadorAntes = (contador - 1);
        int contadorLoop = (n - m);
        
        //Laço para criação de estrutura/código
        while(contador <= m){
            
            //Montando estrutura de loop/For
            sb.append("for(int _")
              .append(contador)
              .append(" = ")
              .append((contador == 1? "0" : 
                                      "(_".concat(String.valueOf(contadorAntes)).concat(" + 1)")))
              .append(" ; _")
              .append(contador)
              .append(" <= ")
              .append(contadorLoop)
              .append("; _")
              .append(contador)
              .append("++){");

            //Montando índices em List<Integer>
            if(contador == m){
                sb.append("indices = new ArrayList();");
                for(int _0 = 1; _0 <= m; _0++){
                    sb.append("indices.add(_")
                      .append(_0)
                      .append(");");      
                }
                sb.append("todosIndicesCombinatorios.add(indices);");
            }
            
            //Atualizando variáveis de controle
            contador++;
            contadorLoop++;
            contadorAntes++;
            
        }
        
        //Fechando chaves "}"
        for(int _0 = 0; _0 < m; _0++){
            sb.append("}");
        }

        //Retornando string contendo o código Java
        return sb.toString();
    }
}
