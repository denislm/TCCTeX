/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.metodista.TCCtex;

import br.metodista.TCCtex.telas.TelaPrincipal;
import static br.metodista.TCCtex.telas.TelaPrincipal.t1;
import static br.metodista.TCCtex.telas.TelaPrincipal.t2;
import static br.metodista.TCCtex.telas.TelaPrincipal.t3;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Denis
 */

public class Principal {
    
    
    /////////////////CARREGA ARQUIVO SALVO PREENCHENDO CAMPOS//////////////////////////////
    public void abrir(File arquivo) throws FileNotFoundException{
        //Verifica se arquivo existe
        if(arquivo.exists()){
            //Verifica se arquivo termina com .tcc
            if(arquivo.getAbsoluteFile().toString().endsWith(".tcc")){
                //Reseta lista de autores e capitulos
                TelaPrincipal.t1.resetListas();
                TelaPrincipal.t3.resetCapitulos();
                //Le Arquivo
                Scanner s = new Scanner(new FileInputStream(
                        arquivo.getAbsoluteFile()), "utf-8");
//---Preenche campos da Tela Informações Essenciais pegando cada linha do arquivo----------------//
        TelaPrincipal.t1.setjTInstituicao(s.nextLine());
        TelaPrincipal.t1.setjTFaculdade(s.nextLine());
        TelaPrincipal.t1.setjTCurso(s.nextLine());
        TelaPrincipal.t1.setjTCidade(s.nextLine());
        TelaPrincipal.t1.getjCTipoCurso().setSelectedIndex(Integer.parseInt(s.nextLine()));
        
        TelaPrincipal.t1.setjTTitulo(s.nextLine());
        TelaPrincipal.t1.setjTSubTitulo(s.nextLine());
        TelaPrincipal.t1.setjTAutores(s.nextLine());
        TelaPrincipal.t1.setjTOrientador(s.nextLine());
        TelaPrincipal.t1.setjTCoorientador(s.nextLine());
        
        TelaPrincipal.t1.setjTAno(s.nextLine());
        TelaPrincipal.t1.getjCNatureza().setSelectedItem(Integer.parseInt(s.nextLine()));
        //Verifica se Informações essenciais já foi concluída com base na linha
        if(s.nextLine().equals("true")){
            if(!TelaPrincipal.t1.getjCheckBox1().isSelected()){
        TelaPrincipal.t1.getjCheckBox1().doClick();
            }else{
            TelaPrincipal.t1.getjCheckBox1().setSelected(false);   
            TelaPrincipal.t1.getjCheckBox1().doClick();
            }
        }else{
            TelaPrincipal.t1.getjCheckBox1().setSelected(false);
    }
            
//------------Preenche campos da Tela Pré Textuais-------------------------//
        TelaPrincipal.t2.setjTResumo(s.nextLine());
        TelaPrincipal.t2.setjTAbstract(s.nextLine());
        TelaPrincipal.t2.setjTDedicatoria(s.nextLine());
        TelaPrincipal.t2.setjTAgradecimentos(s.nextLine());
        TelaPrincipal.t2.setjTEpigrafe(s.nextLine());
    //Verifica se Pré Textuais já foi concluído
        if(s.nextLine().equals("true")){
            if(!TelaPrincipal.t2.getjCheckBox1().isSelected()){
        TelaPrincipal.t2.getjCheckBox1().doClick();
            }else{
            TelaPrincipal.t2.getjCheckBox1().setSelected(false);   
            TelaPrincipal.t2.getjCheckBox1().doClick();
            }
        }else{
            TelaPrincipal.t2.getjCheckBox1().setSelected(false);
    }
//------------Preenche campos da Tela Textuais-------------------------//
        
            TelaPrincipal.t3.setjTIntroducao(s.nextLine()); 
            TelaPrincipal.t3.setjTCapitulo("Selecione o Arquivo e clique em Adicionar");
            String cap = s.nextLine();
            String[] capitulos = cap.split(", ");
            
           TelaPrincipal.t3.limparTabela();
            
            for(int i=0; i < capitulos.length; i++){
                File odtCapitulo = new File(capitulos[i]);
                TelaPrincipal.t3.getDtm().addRow(new Object[]{"Cap "
                    +(i+1)
                    ,odtCapitulo.getName(),odtCapitulo.getAbsolutePath()});
    
            }      
            TelaPrincipal.t3.setjTConclusao(s.nextLine());
            //Verifica se proxima linha é true
            if(s.nextLine().equals("true")){
            /* Se for true então a parte foi terminada, então
             * veirifica se caixa não está selecionada*/
            if(!TelaPrincipal.t3.getjCheckBox1().isSelected()){
                //Se não estiver selecionada: seleciona
                TelaPrincipal.t3.getjCheckBox1().doClick();
            //Se não estiver selecionada: seleciona a mesma
            }else{
            TelaPrincipal.t3.getjCheckBox1().setSelected(false);   
            TelaPrincipal.t3.getjCheckBox1().doClick();
            }
        }else{
            TelaPrincipal.t3.getjCheckBox1().setSelected(false);
    }
            
            }else{
            JOptionPane.showMessageDialog(null, "Selecione um arquivo com extensão .tcc");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Arquivo não existe, selecione outro arquivo");
        }
    }
    
///////////////Salva Arquivo com extensão .tcc com todas modificações salvas//////////// ///////////////////////////////////////
    public void salvar(File pasta) throws FileNotFoundException, IOException{
        if(pasta.exists()){
        try{
        String infoEssenciais, pre, textuais = "";
//----------Salvar Informações Essenciais-----------------//   
        infoEssenciais = 
        TelaPrincipal.t1.getjTInstituicao().getText()+"\n"+
        TelaPrincipal.t1.getjTFaculdade().getText()+"\n"+
        TelaPrincipal.t1.getjTCurso().getText()+"\n"+
        TelaPrincipal.t1.getjTCidade().getText()+"\n"+
        TelaPrincipal.t1.getjCTipoCurso().getSelectedIndex()+"\n"+
     
        TelaPrincipal.t1.getjTTitulo().getText()+"\n"+
        TelaPrincipal.t1.getjTSubTitulo().getText()+"\n"+
        TelaPrincipal.t1.getjTAutores().getText()+"\n"+
        TelaPrincipal.t1.getjTOrientador().getText()+"\n"+
        TelaPrincipal.t1.getjTCoorientador().getText()+"\n"+
        
        TelaPrincipal.t1.getjTAno().getText()+"\n"+
        TelaPrincipal.t1.getjCNatureza().getSelectedIndex()+"\n"+
        TelaPrincipal.t1.getjCheckBox1().isSelected()+"\n";
 
//------------Salvar Pré Textuais------------------------//   
        pre = 
        TelaPrincipal.t2.getjTResumo().getText()+"\r\n"+
        TelaPrincipal.t2.getjTAbstract().getText()+"\r\n"+
        TelaPrincipal.t2.getjTDedicatoria().getText()+"\r\n"+
        TelaPrincipal.t2.getjTAgradecimentos().getText()+"\r\n"+
        TelaPrincipal.t2.getjTEpigrafe().getText()+"\r\n"+
        TelaPrincipal.t2.getjCheckBox1().isSelected()+"\r\n";
//--------------Salvar Textuais--------------------------//
        //Pega Capitulos da tabela e salva no vetor abaixo
        javax.swing.table.DefaultTableModel capitulos = TelaPrincipal.t3.getDtm();
        //Pega capitulo do vetor e insere na String 
        for(int i = 0; i< capitulos.getDataVector().size(); i++){
            if( i == 0){
                textuais = capitulos.getValueAt(i, 2).toString();
            }else
        textuais = textuais + ", " + capitulos.getValueAt(i, 2);
        }
      
        textuais = TelaPrincipal.t3.getjTIntroducao().getText() + "\r\n" 
                + textuais + "\n"
                + TelaPrincipal.t3.getjTConclusao().getText()+"\r\n"
                + TelaPrincipal.t3.getjCheckBox1().isSelected();;
        Conversor a = new Conversor();
        
        String texto = infoEssenciais + pre + textuais;
        pasta = new File(pasta.getAbsoluteFile()+"\\config.tcc");
        System.out.println(pasta);
        a.escreverArquivo(texto, pasta);
        }catch(NullPointerException e){
        //Pessoa clicou no cancelar ou fechou
        }
        }else{
        JOptionPane.showMessageDialog(null, "Pasta não existe, selecione outra pasta");
        }
    }
}
