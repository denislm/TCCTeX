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
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Textuais {
    
    private File introducao;
    private File conclusao;
    private LinkedList<File> capitulos = new LinkedList();
    Conversor a = new Conversor();
    int i;
    
    public boolean converterTextuais() throws IOException{
        try{
    if (this.verificarTextuais()){
    //Converte todos capitulos
    for( i= this.getCapitulos().size()-1; i>=0 ; i--){
    a.odtToTex(this.getCapitulos().get(i));
    this.listarTabelas(this.getCapitulos().get(i));
    this.listarImagens(this.getCapitulos().get(i));
    }
    //Converte introdução
    a.odtToTex(this.getIntroducao());
    //Converte conclusão
    a.odtToTex(this.getConclusao());
     System.out.println("Textuais foram convertidos");
     return true;
    }
       return false;    
    }catch (FileNotFoundException e){
            System.out.println("Algum(ns) capitulo(s) não foi(ram)) encontrado(s)");
        }
        return false;
    }
    public boolean verificarTextuais(){
        if(introducao.exists() && capitulos.get(0).exists() && conclusao.exists()){
            
        if(introducao.getName().toString().contains(".odt") &&
           conclusao.getName().toString().contains(".odt")){
        for( i=0; i < capitulos.size(); i++){
            String cap = capitulos.get(i).getName();
        if((cap.contains(".odt"))){
            System.out.println(capitulos.get(i).getName());           
        }else{
            JOptionPane.showMessageDialog(null, "Capitulo(s) diferentes de odt.","Erro,",JOptionPane.ERROR_MESSAGE);
            System.out.println("Capitulo(s) diferentes de odt.");   
             return false;
        }
       }
        //introdução e conclusão são .odt
       return true;
        }
        // Arquivo diferente de odt inserido
        System.out.println("Erro: Introdução ou(e) conclusão diferente de odt");
        return false;
        }
        // Algum dos arquivo não existe
        System.out.println("Erro: Algum arquivo não existe");
        return false;
    
    }
    
   public String teste(File odt) throws IOException{
Conversor a = new Conversor();
String texto;
/* Recebe arquivo odt, 
 * converte para tex, 
 * adiciona as tags begin{tipo} e end {tipo} se necessário*/  
//Ex: recebe resumo add begin{resumo} ao inicio do doc...
     texto = a.alterarTex(a.odtToTex(odt), "");
        return texto;
    
        
}
  ///////////////////////LISTAR IMAGENS NO TEX////////////////////////////////////////////////////////////////////////////////////// 
   public static boolean listarImagens(File arquivo) throws FileNotFoundException, IOException{
 
       int quantImg, i;  String texto = "",linha = "";
 Scanner scanner;
 LinkedList<String> nomeImagens = new LinkedList();
  LinkedList<String>includesImagens = new LinkedList();
  Figura figura = new Figura();
  LinkedList<Figura>figuras = new LinkedList();
 /* Pega o nome do arquivo sem o .odt */
 String nomeArquivo = arquivo.getName().replace( ".odt", "" ); 
 File file = new File("LaTex\\"+nomeArquivo+"-img\\"); 

// se a pasta de imagem do capitulo existir
if(file.exists()){
// Pegar quantidade de arquivos dentro da pasta do capítulo
File list[] = file.listFiles();
// Salvar quantidade de arquivos (sendo que só existem imagens na pasta) 
quantImg = list.length;
   Textuais t= new Textuais();

   texto = t.teste(arquivo);
// Rodar for para quantidade de imagens
for( i = 1; i<= quantImg; i++){
/* Apagar linha que começa com: Figura\ 1\ (ex: Figura\ 1\ {}-\ Barra de Ferramentas)
* Para isso é necessário achar a linha salvar em uma string e retirar: 
* "Figura\\ "+i+"\\ " , retirar "{}", retirar "-\\ " e salvar o que resta como nome da figura */

scanner = new Scanner(texto);
/* Escaneia texto que contem texto do arquivo TeX */
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Verifica se existe os seguintes padrões de legendas de figuras
   * no arquivo tex gerado a partir do odt, que não funcionam no LaTex*/
  if(linha.contains("Figura\\ "+i+"\\ {}-\\")
                    || linha.contains("Figura " + i+ " - ")
                    || linha.contains("Figura "+i+"\\ {}-\\")
                    || linha.contains("Figura " + i+ "-")
                    || linha.contains("Figura\\ " + i+ "-")
                    || linha.contains("Figura\\ " + i+ "\\ {}- ")){
  /* Se existirem os padrões em uma linha é retirado o texto 
   * que vem antes do nome da figura,para que possa ser pego
   * apenas o nome dela*/
     linha = linha.replace("Figura\\ "+i+"\\ {}-\\","");
     linha = linha.replace("Figura " + i+ " - ","");
     linha = linha.replace("Figura " + i+ " - ","");
     linha = linha.replace("Figura "+i+"\\ {}-\\ ","");
     linha = linha.replace("Figura " + i+ "-","");
     linha = linha.replace("Figura\\ " + i+ "-","");
     linha = linha.replace("Figura\\ " + i+ "\\ {}- ", "");
  /* Adiciona o nome da figura na lista*/   
      nomeImagens.add(linha);
      
  }else{
      //linha não contem figuram vai para proxima
  }
}
 scanner.close();
 

}
if(nomeImagens.size()==quantImg){

    for( i=0; i<nomeImagens.size(); i++){
    //Apaga todas legendas de imagens criadas pelo word, do arquivo tex
     texto = texto.replace("Figura\\ "+(i+1)+"\\ {}-\\"+nomeImagens.get(i),"");
     texto = texto.replace("Figura " + (i+1)+ " - "+nomeImagens.get(i),"");
     texto = texto.replace("Figura " + (i+1)+ " - "+nomeImagens.get(i),"");
     texto = texto.replace("Figura "+(i+1)+"\\ {}-\\ "+nomeImagens.get(i),"");
     texto = texto.replace("Figura " + (i+1)+ "-"+nomeImagens.get(i),"");
     texto = texto.replace("Figura\\ " + (i+1)+ "-"+nomeImagens.get(i),"");
     texto = texto.replace("Figura\\ " + (i+1)+ "\\ {}- "+nomeImagens.get(i),"");
    }
    /* Escanear novamente linhas do texto e ao achar  \includegraphics[ 
     * adicionar \n\captionof{figure}{nome da figura}*/
    scanner = new Scanner(texto);
    /* Escaneia texto que contem texto do arquivo TeX */
    figura = new Figura();figura.setPosição(0);
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Pegar linhas que possuem \includegraphics*/
  if(linha.contains("\\includegraphics")){
  // Adicionar na lista de includes
      figura.setTitulo(linha);
      figura.setPosição(i);

  }if(linha.contains("Fonte:")||linha.contains("fonte:")){
      linha.replace("Fonte: ", "");
      linha.replace("Fonte:", "");
      linha.replace("fonte: ", "");
      linha.replace("fonte:", "");
      figura.setFonte(linha);   
  }
  if(figura.getPosição()!=0)
  figuras.add(figura);
          } 
 scanner.close();
 
 // Roda o for para quantas imagens existirem
 for( i=0; i<nomeImagens.size(); i++){
    //Adiciona legendas das imagens no modelo LaTeX
     texto = texto.replace(includesImagens.get(i),""
             + "\\begin{figure}[H]"
             + "\\caption{"+nomeImagens.get(i)+"}\n"
             +includesImagens.get(i)+"\n"
             +"\\fonte{"+""+"}"
             + "\\end{figure}");     
 }
 Conversor a = new Conversor();
a.escreverArquivo(texto, new File("LaTex\\"+nomeArquivo+".tex"));
 
    
    return true;
    
}else{
System.out.println("Você não inseriu a legenda da imagem corretamente\n"
        + nomeImagens+"\t"+nomeImagens.size()+"\n"
        + quantImg);
return false;
}
 
}else{
    //Arquivo não existe
   return false;
}
           
}
   int numeroDeTabelas;
public void contarTabelas(File capitulo){

}
  ///////////////////////LISTAR Tabelas NO TEX////////////////////////////////////////////////////////////////////////////////////// 
   public void listarTabelas(File arquivo) throws FileNotFoundException, IOException{
 int quantTab = 0, i;  String texto = "",linha = "", tabela = "";
       
 Scanner scanner;
 LinkedList<String> nomeTabelas = new LinkedList();
  LinkedList<String>includesTabelas = new LinkedList();
 /* Pega o nome do arquivo sem o .odt */
 String nomeArquivo = arquivo.getName().replace( ".odt", "" ); 
texto = teste(arquivo);
// Pegar quantidade de Tabelas dentro de um capitulo
scanner = new Scanner(texto);
/* Escaneia texto que contem texto do arquivo TeX */
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Verifica se existe os seguintes padrões de legendas de figuras
   * no arquivo tex gerado a partir do odt, que não funcionam no LaTex*/
  if(linha.contains("\\begin{supertabular}")){
    quantTab++;  
  }   
 //Se linha não tiver \begin{supertabular} vai para próxima até o final
 }
 System.out.println(nomeArquivo+"\t quantidade de Tabelas: "+quantTab);
for( i = 1; i<= quantTab; i++){
/* Apagar linha que começa com: Figura\ 1\ (ex: Figura\ 1\ {}-\ Barra de Ferramentas)
* Para isso é necessário achar a linha salvar em uma string e retirar: 
* "Figura\\ "+i+"\\ " , retirar "{}", retirar "-\\ " e salvar o que resta como nome da figura */

scanner = new Scanner(texto);
/* Escaneia texto que contem texto do arquivo TeX */
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Verifica se existe os seguintes padrões de legendas de figuras
   * no arquivo tex gerado a partir do odt, que não funcionam no LaTex*/
  if(                  linha.contains("Tabela\\ "+i+"\\ {}-\\")
                    || linha.contains("Tabela\\ "+i+"\\ {}- ")
                    || linha.contains("Tabela " + i+ " - ")
                    || linha.contains("Tabela "+i+"\\ {}-\\")
                    || linha.contains("Tabela " + i+ "-")
                    || linha.contains("Tabela\\ " + i+ "-")){
  /* Se existirem os padrões em uma linha é retirado o texto 
   * que vem antes do nome da Tabela,para que possa ser pego
   * apenas o nome dela*/
     linha = linha.replace("Tabela\\ "+i+"\\ {}-\\ ","");
     linha = linha.replace("Tabela\\ "+i+"\\ {}-\\","");
     linha = linha.replace("Tabela " + i+ " - ","");
     linha = linha.replace("Tabela " + i+ " - ","");
     linha = linha.replace("Tabela "+i+"\\ {}-\\ ","");
     linha = linha.replace("Tabela " + i+ "-","");
     linha = linha.replace("Tabela\\ " + i+ "-","");
     linha = linha.replace("Tabela\\ "+i+"\\ {}- ","");
     linha = linha.replace("}","");
  /* Adiciona o nome da figura na lista*/   
      nomeTabelas.add(linha);
      System.out.println(" Legenda: " + linha);     
  }
      //Se os padrões não existirem vai para proxima linha
} 
 scanner.close(); 
}
    for( i=0; i<nomeTabelas.size(); i++){
    //Apaga todas legendas de imagens criadas pelo word, do arquivo tex
     texto = texto.replace("Tabela\\ "+(i+1)+"\\ {}-\\"+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela\\ "+(i+1)+"\\ {}-\\ "+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela\\ "+(i+1)+"\\ {}- \\ "+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela " + (i+1)+ " - "+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela " + (i+1)+ " - "+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela "+(i+1)+"\\ {}-\\ "+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela " + (i+1)+ "-"+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela\\ " + (i+1)+ "-"+nomeTabelas.get(i),"");
     texto = texto.replace("Tabela\\ "+(i+1)+"\\ {}- "+nomeTabelas.get(i),"");
     
     System.out.println("Tabela\\ "+(i+1)+"\\ {}- "+nomeTabelas.get(i));
     scanner = new Scanner(texto);
   /* Escanear novamente linhas do texto*/
boolean continua=true;
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Pegar linhas entre \tablefirsthead{} e \end{supertabular}*/
  if(linha.contains("\\tablefirsthead{}")){
      tabela = linha + "\n";
      while(scanner.hasNext() && continua){
      linha = scanner.nextLine();
      if(!linha.contains("\\end{supertabular}")){
          tabela = tabela + linha + "\n";
      }else{
      continua = false;
          tabela = tabela + linha;
          texto = texto.replace(tabela, (""
             + "\\begin{table}"
             + "\\setlength{\\abovecaptionskip}{27pt}\n"
             + "\\setlength{\\belowcaptionskip}{17pt}\n"
             + "\\captionof{table}{"+nomeTabelas.get(i)+"}\n"
             +tabela+"\n"
             + "\\end{table}"));
      }
      }
  // Adicionar na lista de includes
      includesTabelas.add(tabela);
  }
          } 
 scanner.close();
     
    }
    

       
     
a.escreverArquivo(texto, new File("LaTex\\"+nomeArquivo+".tex"));
}
 ////////////////////GETTERS E SETTERS///////////////////////////////////////////////////////////////////////////////////////////////////////  
    /**
     * @return the introducao
     */
    public File getIntroducao() {
        return introducao;
    }

    /**
     * @param introducao the introducao to set
     */
    public void setIntroducao(File introducao) {
        this.introducao = introducao;
    }

    /**
     * @return the conclusao
     */
    public File getConclusao() {
        return conclusao;
    }

    /**
     * @param conclusao the conclusao to set
     */
    public void setConclusao(File conclusao) {
        this.conclusao = conclusao;
    }

    /**
     * @return the capitulos
     */
    public LinkedList<File> getCapitulos() {
        return capitulos;
    }

    /**
     * @param capitulos the capitulos to set
     */
    public void setCapitulos(LinkedList<File> capitulos) {
        this.capitulos = capitulos;
    }
}
