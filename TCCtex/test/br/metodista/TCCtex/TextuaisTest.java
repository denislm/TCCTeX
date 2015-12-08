/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.metodista.TCCtex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import static java.lang.System.in;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Scanner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Denis
 */
public class TextuaisTest {
   
    
           File introducao = new File("TestesArquivos\\Introducao.odt");
           File capitulo1 = new File("TestesArquivos\\Cap01.odt");
           File capitulo2 = new File("TestesArquivos\\Cap02.odt");
           File capitulo3 = new File("TestesArquivos\\Cap03.odt");
           File conclusao = new File("TestesArquivos\\Conclusao.odt"); 
           LinkedList<File> capitulos = new LinkedList();
    
public static void main(String[]args) throws IOException{
TextuaisTest textuaisTest = new TextuaisTest();
    textuaisTest.testarConversao();
}
           @Test
      public void testarVerificarTextuais() throws IOException{
           Textuais t = new Textuais();
           LinkedList<File> capitulos = new LinkedList();
           capitulos.add(new File("TestesArquivos\\Cap01.odt"));
           capitulos.add(new File("TestesArquivos\\Cap02.odt"));
           capitulos.add(new File("TestesArquivos\\Cap03.odt"));
           
           t.setIntroducao(new File("TestesArquivos\\Introducao.odt"));
           t.setConclusao(new File("TestesArquivos\\conclusao.odt"));
           t.setCapitulos(capitulos);
           assertFalse(t.verificarTextuais());
      }
    
          
      private void testarConversao() throws IOException{
           Textuais t = new Textuais();
           Conversor a = new Conversor();
           capitulos.add(capitulo1);
           capitulos.add(capitulo2);
           capitulos.add(capitulo3);
          
           t.setIntroducao(introducao);
           t.setConclusao(conclusao);
           t.setCapitulos(capitulos);
           
            t.converterTextuais();
           
           
           
// Teste para contar imagens de um Cap
      }

        
}
