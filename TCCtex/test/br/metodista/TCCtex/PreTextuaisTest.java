package br.metodista.TCCtex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;


/**
 *
 * @author Denis
 */
public class PreTextuaisTest {

      public static void main(String[]args) throws IOException{
          PreTextuais preTextuais = new PreTextuais();
          File resumoPtBr, resumoEnUs, dedicatoria, agradecimento, epigrafe;
          resumoPtBr = new File("\\TestesArquivos\\Resumo.odt" );
          resumoEnUs = new File("\\TestesArquivos\\Abstract.odt" );
          dedicatoria = new File("\\TestesArquivos\\Dedicatoria.odt" );
          agradecimento = new File("\\TestesArquivos\\Agradecimentos.odt" );
          epigrafe = new File("\\TestesArquivos\\Epigrafe.odt" );
          
          preTextuais.setResumoPtBr(resumoPtBr);
          preTextuais.setResumoEnUs(resumoEnUs);
          preTextuais.setDedicatoria(dedicatoria);
          preTextuais.setAgradecimento(agradecimento);
          preTextuais.setEpigrafe(epigrafe);
          
          preTextuais.converterPre();
           }
}
