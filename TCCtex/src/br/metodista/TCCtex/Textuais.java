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
    
    
    public boolean converterTextuais() throws IOException{
        try{
    if (this.verificarTextuais()){
    //Converte todos capitulos
    for(int i= this.getCapitulos().size()-1; i>=0 ; i--){
        //modificar por?  a.odtToTex(this.getCapitulos().get(i),("cap"+(i+1)));
    a.odtToTex(this.getCapitulos().get(i),"");
    this.listarImagens(this.getCapitulos().get(i));
    //this.listarTabelas(this.getCapitulos().get(i));
    //
    //capitulos.clear();
    }
    //Converte introdução
    a.odtToTex(this.getIntroducao(),"intro");
    //Converte conclusão
    a.odtToTex(this.getConclusao(),"conclusao");
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
        for( int i=0; i < capitulos.size(); i++){
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
     texto = a.alterarTex(a.odtToTex(odt,""), "");
        return texto;
    
        
}
  ///////////////////////LISTAR IMAGENS NO TEX////////////////////////////////////////////////////////////////////////////////////// 
   public static boolean listarImagens(File arquivo) throws FileNotFoundException, IOException{
 
       int quantImg, i;  String texto = "",linha = "";
 Scanner scanner;
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
  
  linha.replace("\\rmfamily", "");
  linha.replace("\\selectlanguage{portuguese}", "");
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
     figura = new Figura();
     figura.setTitulo(linha);
      figuras.add(figura);
      
  }else{
      //linha não contem figuram vai para proxima
  }
}
 scanner.close();
 

}
if(figuras.size()==quantImg){

    for( i=0; i<figuras.size(); i++){
    //Apaga todas legendas de imagens criadas pelo word, do arquivo tex
     texto = texto.replace("Figura\\ "+(i+1)+"\\ {}-\\"+figuras.get(i).getTitulo(),"");
     texto = texto.replace("Figura " + (i+1)+ " - "+figuras.get(i).getTitulo(),"");
     texto = texto.replace("Figura " + (i+1)+ " - "+figuras.get(i).getTitulo(),"");
     texto = texto.replace("Figura "+(i+1)+"\\ {}-\\ "+figuras.get(i).getTitulo(),"");
     texto = texto.replace("Figura " + (i+1)+ "-"+figuras.get(i).getTitulo(),"");
     texto = texto.replace("Figura\\ " + (i+1)+ "-"+figuras.get(i).getTitulo(),"");
     texto = texto.replace("Figura\\ " + (i+1)+ "\\ {}- "+figuras.get(i).getTitulo(),"");
    }
    /* Escanear novamente linhas do texto e ao achar  \includegraphics[ 
     * adicionar \n\captionof{figure}{nome da figura}*/
    scanner = new Scanner(texto);
    /* Escaneia texto que contem texto do arquivo TeX */
    int x = 0;
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Pegar linhas que possuem \includegraphics*/
  if(linha.contains("\\includegraphics")){
  // Adicionar na lista de includes
      
      figuras.get(x).setArquivo(linha);
      x++;
  //Procura linhas que estão com fonte: texto, pega o texto após a fonte (ex: fonte: teste = pega teste)    
  }if(linha.contains("Fonte:")||linha.contains("fonte:")){
      linha = linha.replace("Fonte: ", "");
      linha = linha.replace("Fonte:", "");
      linha = linha.replace("fonte: ", "");
      linha = linha.replace("fonte:", "");
      //joga todas as fontes dentro da lista de figuras
      figuras.get(x-1).setFonte(linha);
      
      texto = texto.replace("Fonte: " + linha,"");
      texto = texto.replace("Fonte:" + linha,"");
      texto = texto.replace("fonte: " + linha,"");
      texto = texto.replace("fonte:" + linha,"");
  }
          }
 scanner.close();

 
 // Roda o for para quantas imagens existirem
 for( i=0; i<figuras.size(); i++){
    //Adiciona legendas das imagens no modelo LaTeX
     String teste = "\\begin{figure}[H]\n"
             +"\\setlength{\\abovecaptionskip}{27pt}\n"
             + "\\setlength{\\belowcaptionskip}{17pt}\n"
             + "\\caption{"+figuras.get(i).getTitulo()+"}\n"
             +figuras.get(i).getArquivo()+"\n";
     //System.out.println("Figura "+(i+1)+": "+figuras.get(i).getFonte());
     if(figuras.get(i).getFonte()!=null){
    teste = teste + "\\fonte{"+figuras.get(i).getFonte()+"} \n";
    }
     teste = teste +"\\end{figure}";
     texto = texto.replace(figuras.get(i).getArquivo(),teste);

             
 }
//Conversor a = new Conversor();
//a.escreverArquivo(texto, new File("LaTex\\"+nomeArquivo+".tex"));
    listarTabelas(arquivo, texto);
    
    return true;
    
}else{
System.out.println("Você não inseriu a legenda da imagem corretamente\n"
        + "Imagens encontradas: "+figuras.size()+"\t imagens na pasta:"+quantImg);
return false;
}
 
}else{
    //Arquivo não existe
   return false;
}
           
}
   
   int numeroDeTabelas;

  ///////////////////////LISTAR Tabelas NO TEX////////////////////////////////////////////////////////////////////////////////////// 
   public static void listarTabelas(File arquivo, String texto) throws FileNotFoundException, IOException{
 int quantTab = 0, i;  String linha = "", tabela = "";
       
 Scanner scanner;
 LinkedList<String> nomeTabelas = new LinkedList();
 /* Pega o nome do arquivo sem o .odt */
 String nomeArquivo = arquivo.getName().replace( ".odt", "" ); 

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
 scanner.close();
 System.out.println(nomeArquivo+"\t quantidade de Tabelas: "+quantTab);

/* Apagar linha que começa com: Figura\ 1\ (ex: Figura\ 1\ {}-\ Barra de Ferramentas)
* Para isso é necessário achar a linha salvar em uma string e retirar: 
* "Figura\\ "+i+"\\ " , retirar "{}", retirar "-\\ " e salvar o que resta como nome da figura */

scanner = new Scanner(texto);
int cont1 = 1;
/* Escaneia texto que contem texto do arquivo TeX */
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Verifica se existe os seguintes padrões de legendas de figuras
   * no arquivo tex gerado a partir do odt, que não funcionam no LaTex*/
  if(                  linha.contains("Tabela\\ "+cont1+"\\ {}-\\")
                    || linha.contains("Tabela\\ "+cont1+"\\ {}- ")
                    || linha.contains("Tabela "+cont1+" -\\ ")
                    || linha.contains("Tabela " +cont1+ " - ")
                    || linha.contains("Tabela "+cont1+"\\ {}-\\")
                    || linha.contains("Tabela " + cont1+ "-")
                    || linha.contains("Tabela\\ " + cont1+ "-")){
  /* Se existirem os padrões em uma linha é retirado o texto 
   * que vem antes do nome da Tabela,para que possa ser pego
   * apenas o nome dela*/
      texto = texto.replace(linha, "");
     linha = linha.replace("Tabela "+cont1+" -\\ ","");
     linha = linha.replace("Tabela\\ "+cont1+"\\ {}-\\ ","");
     linha = linha.replace("Tabela\\ "+cont1+"\\ {}-\\","");
     linha = linha.replace("Tabela " + cont1+ " - ","");
     linha = linha.replace("Tabela " + cont1+ " - ","");
     linha = linha.replace("Tabela "+cont1+"\\ {}-\\ ","");
     linha = linha.replace("Tabela " + cont1+ "-","");
     linha = linha.replace("Tabela\\ " + cont1+ "-","");
     linha = linha.replace("Tabela\\ "+cont1+"\\ {}- ","");
     
      
  /* Adiciona o nome da figura na lista*/   
      nomeTabelas.add(linha);
      cont1++;
      
      //System.out.println(" Legenda: " + linha);     
  }else{}
      //Se os padrões não existirem vai para proxima linha
} 
 scanner.close(); 

    //Apaga todas legendas de imagens criadas pelo word, do arquivo tex
   
     scanner = new Scanner(texto);
     
   /* Escanear novamente linhas do texto*/
     String texto2 = null;
     int cont2 = 0;
 while (scanner.hasNext()) {
  linha = scanner.nextLine();
  /* Pegar linhas entre \tablefirsthead{} e \end{supertabular}*/
  if(linha.contains("\\tablefirsthead{}")){
     // System.out.println("contador: "+cont +"\t"+nomeTabelas.get(cont));;
         linha = "";
         texto2 = texto2 + "\n" + 
                       "\\begin{table}\n" +
                       "\\caption{"+nomeTabelas.get(cont2)+"}\n" +
                       "\\tablefirsthead{}";
         cont2++;
  }   
  if(linha.contains("\\end{supertabular}")){
         linha = "";

         texto2 = texto2+ "\n" + "\\end{supertabular}\n" +
                       "\\end{table}";
                   
      }else{
      texto2 = texto2 + "\n" + linha;
      }
  
 }
 scanner.close();
    File cap = new File("LaTex\\"+nomeArquivo+".tex");
    Conversor.escreverArquivo(texto2, cap);
        
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
