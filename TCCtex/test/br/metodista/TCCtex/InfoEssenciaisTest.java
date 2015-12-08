/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.metodista.TCCtex;

import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Denis
 */
public class InfoEssenciaisTest {
        InfoEssenciais info = new InfoEssenciais();

    
 
      @Test
      public void testarInstituição(){
           String faculdade = "Metodista";
          info.setInstituicao(faculdade);
          assertEquals(info.getInstituicao(), faculdade);
                
}
      @Test
      public void testarFaculdade(){
          info.setFaculdade("Facet");
          assertEquals(info.getFaculdade(), "Facet");
                
}
          @Test
      public void testarCurso(){
          info.setCurso("SI");
          assertEquals(info.getCurso(), "SI");
                
}
  @Test
      public void testarAutor(){
           String x = "Antonio, Denis, Rodrigo";
          info.separarAutores(x);
          ArrayList<String> autores = new ArrayList();
          String[] autorSplit = x.split(", ");
          for(int i = 0; i<=autorSplit.length-1; i++){
            
            autores.add(autorSplit[i]);
            System.out.println(autores);
        }    
          
          ArrayList<String> autor = info.getAutor();
          assertEquals(info.getAutor(), autores);
                
}
         @Test
      public void testarTitulo(){
          info.setTitulo("TCCTex");
          assertEquals(info.getTitulo(), "TCCTex");
                
}
         @Test
      public void testarSubTitulo(){
          info.setSubTitulo("TCCTex");
          assertEquals(info.getSubTitulo(), "TCCTex");
                
}
         @Test
      public void testarCidade(){
          info.setCidade("SBC");
          assertEquals(info.getCidade(), "SBC");
                
} 
         @Test
      public void testarAno(){
          info.setAno(2014);
          assertEquals(info.getAno(), 2014);
                
}        @Test
      public void testarOrientador(){
          info.setOrientador("Silvia");
          assertEquals(info.getOrientador(), "Silvia");
                
}        @Test
      public void testarCoOrientador(){
          info.setCoOrientador("x");
          assertEquals(info.getCoOrientador(), "x");
                
}        @Test
      public void testarNatureza(){
          info.setNatureza("TCC");
          assertEquals(info.getNatureza(), "TCC");
                
}        @Test
      public void testarTipoCurso(){
          info.setTipoCurso("Bacharelado");
          assertEquals(info.getTipoCurso(), "Bacharelado");
                
}   
         @Test
      public void testarVerificarInformacoesPreenchidas(){
           info.setInstituicao("Universidade Metodista de São Paulo");
           info.setFaculdade("Sistemas de Informação");
           info.setCurso("Sistemas de Informação");
           info.separarAutores("Antonio, Rodrigo, Denis");
           info.setTitulo("TCCtex");
           info.setCidade("São Bernardo do Campo");
           info.setAno(2014);
           info.setNatureza("TCC");
           info.setTipoCurso("Bacharelado");
           info.setOrientador("Silvia Aparecida Brunini");

           assertTrue(info.verificarInformacoesPreenchidas());
}
      
        @Test
      public void testarPreencherPre() throws IOException{
           info.setInstituicao("Universidade Metodista de São Paulo");
           info.setFaculdade("Faculdade de Exatas e Tecnologia");
           info.setCurso("Sistemas de Informação");
           info.separarAutores("Antonio Carlos Silva Filho, Denis Luiz Martinoni Junior, Rodrigo Mascarini Galindo");
           info.setTitulo("TCCtex");
           info.setSubTitulo("");
           info.setCidade("São Bernardo do Campo");
           info.setAno(2014);
           info.setNatureza("Trabalho de Conclusão de Curso");
           info.setTipoCurso("Bacharel");
           info.setOrientador("Silvia Aparecida Brunini");
           info.setCoOrientador("");
           info.separarBanca("Professor 02, Professor 03");
           
          
         
           
      }
}
