package br.metodista.TCCtex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;


/**
 *
 * @author Denis
 */
public class PosTextuaisTest {
    
      static PosTextuais p = new PosTextuais();
      public static void main(String[]args) throws IOException{
            testarReferencias();
            //testarCopia();
           }

      public static void testarReferencias() throws IOException{
      p.setReferencias(new File("TestesArquivos\\Referencias.odt"));
      p.setRrbq(new File("TestesArquivos\\Anexos\\RRBQ.pdf"));
          LinkedList<File> atas = new LinkedList();
          atas.add(new File("TestesArquivos\\Anexos\\ata01.pdf"));
          atas.add(new File("TestesArquivos\\Anexos\\ata02.pdf"));
      p.setAtas(atas);
      p.setCronograma(new File("TestesArquivos\\Anexos\\cronograma.pdf"));
      p.converterPosTextuais();

      }
      public static void testarCopia() throws IOException{
      p.pdfToTex(new File("TestesArquivos\\Anexos\\RRBQ.pdf"),"Relat\\'orio de Recomenda\\c{c}\\~oes da Banca de Qualifica\\c{c}\\~ao","anexo");
      }
      
      
}
