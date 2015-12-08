package br.metodista.TCCtex;

import br.metodista.TCCtex.telas.TelaPreTextuais;
import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import de.nixosoft.jlr.JLROpener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Denis
 */
public class PreTextuais {
    
    
 // Arquivos Necessários
    private File resumoPtBr;
    private File resumoEnUs;
 // Arquivo Opcionais
    private File dedicatoria;
    private File agradecimento;
    private File epigrafe;
    private File errata;
   



public void converterPre() throws IOException{
    try{
    if( resumoPtBr.exists()  
      &&   resumoEnUs.exists() ){
          this.gerarPre(this.resumoPtBr, "resumo");
          this.gerarPre(this.resumoEnUs, "abstract");
          if(dedicatoria.exists())
          this.gerarPre(this.getDedicatoria(), "dedicatoria");
          if(agradecimento.exists())
          this.gerarPre(this.agradecimento, "agradecimento");
          if(epigrafe.exists())
          this.gerarPre(this.epigrafe, "epigrafe");
    }
    } catch (IOException ex) {
            Logger.getLogger(TelaPreTextuais.class.getName()).log(Level.SEVERE, null, ex);
        
      } catch (NullPointerException e) {
            
        }
}
public void gerarPre(File odt, String tipo) throws IOException{
Conversor a = new Conversor();
/* Recebe arquivo odt, 
 * converte para tex, 
 * adiciona as tags begin{tipo} e end {tipo} se necessário*/  
//Ex: recebe resumo add begin{resumo} ao inicio do doc...
if(tipo != null){
     a.alterarTex(a.odtToTex(odt,tipo), tipo);
    
}else{
a.odtToTex(odt,tipo);

}
        
}
    /**
     * Getters e Setters
     */


    /**
     * @return the resumoPtBr
     */
    public File getResumoPtBr() {
        return resumoPtBr;
    }

    /**
     * @param resumoPtBr the resumoPtBr to set
     */
    public void setResumoPtBr(File resumoPtBr) {
        this.resumoPtBr = resumoPtBr;
    }

    /**
     * @return the resumoEnUs
     */
    public File getResumoEnUs() {
        return resumoEnUs;
    }

    /**
     * @param resumoEnUs the resumoEnUs to set
     */
    public void setResumoEnUs(File resumoEnUs) {
        this.resumoEnUs = resumoEnUs;
    }

    /**
     * @return the dedicatoria
     */
    public File getDedicatoria() {
        return dedicatoria;
    }

    /**
     * @param dedicatoria the dedicatoria to set
     */
    public void setDedicatoria(File dedicatoria) {
        this.dedicatoria = dedicatoria;
    }

    /**
     * @return the agradecimento
     */
    public File getAgradecimento() {
        return agradecimento;
    }

    /**
     * @param agradecimento the agradecimento to set
     */
    public void setAgradecimento(File agradecimento) {
        this.agradecimento = agradecimento;
    }

    /**
     * @return the epigrafe
     */
    public File getEpigrafe() {
        return epigrafe;
    }

    /**
     * @param epigrafe the epigrafe to set
     */
    public void setEpigrafe(File epigrafe) {
        this.epigrafe = epigrafe;
    }

    /**
     * @return the errata
     */
    public File getErrata() {
        return errata;
    }

    /**
     * @param errata the errata to set
     */
    public void setErrata(File errata) {
        this.errata = errata;
    }

    
}
