/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.metodista.TCCtex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Denis
 */
public class ConversorTest {
    
    public ConversorTest() {
    }
    
     @Test
      public void testarGerarTexPrincipal() throws IOException{
           Conversor arquivo = new Conversor();
           LinkedList nomeCapitulo = new LinkedList<>();
           
           for(int i = 1; i <= 7; i++ )
           nomeCapitulo.add("Cap" + i);
         
// System.out.println(arquivo.gerarTexPrincipal(nomeCapitulo));
                
}
      /*Atenção: esse método só será rodado uma vez por arquivo convertido
      Exemplo: 
      - O usuário insere um arquivo ".odt" do resumo
      - O arquivo é convertido para tex
      - As linhas /begin{resumo} e /end{resumo} não existem
      - Então são criadas utilizando este método */
     @Test
      public void testarAlterarTex() throws FileNotFoundException, IOException{
           Conversor arquivo = new Conversor();
          
arquivo.alterarTex(new File("TestesArquivos\\resumo.tex"), "resumo");
                          
}//não sei pq mas da problema
      @Test
      public void testarOdtToTex() throws FileNotFoundException, IOException, AssertionError{
           Conversor arquivo = new Conversor();
            File documento = new File("TestesArquivos\\mydocument.odt");
        
            arquivo.odtToTex(documento,"");
                         
}     

}
