/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.metodista.TCCtex;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import de.nixosoft.jlr.JLRGenerator;
import de.nixosoft.jlr.JLROpener;

import writer2latex.api.Config;
import writer2latex.api.Converter;
import writer2latex.api.ConverterFactory;
import writer2latex.api.ConverterResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Denis
 */
public class Conversor {
    
    

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    
/*Abre Tex e adiciona modificações no começo e fim (Ex: \begin{abstract} texto \end{abstract})*/
    
    public String alterarTex(File arquivo, String tipo) 
            throws FileNotFoundException, IOException{
        String comeco="", meio = "", fim="", texto;
        
        /* Abre arquivo tex utilizando o formato de arquivo utf-8
         * para que seja preservada toda acentuação e caracteres do arquivo*/
         Scanner scanner = new Scanner(new FileInputStream(
                 arquivo.getAbsoluteFile()), "utf-8")
                       .useDelimiter("\\||\\n");
         
        //Se tipo de arquivo for igual resumo ou abstract
        if(tipo.equals("resumo") || tipo.equals("abstract")){
            if(tipo.equals("resumo")){
        //Adiciona Título (Tag) na váriavel: comeco (Ex: /begin {resumo})
        comeco="\\begin{" + tipo + "}\n";
        //Adiciona Título Final (Tag) na váriavel: fim (Ex: /end {resumo})
        fim="\n\\end{" + tipo + "}";
            }else{
            comeco="\\begin{resumo}[abstract]\n";
            fim="\n\\end{resumo}";
            }
        
   // Lê primeira linha do tex e armazena na váriavel: primeiraLinha
    String primeiraLinha = scanner.nextLine();
    
   /* Caso primeira linha não for \begin{resumo} por exemplo, este método 
    * continua a rodar, para adicionar as linhas de começo e fim        */
 if(!primeiraLinha.equals("\\begin{"+tipo+"}")){
      
texto = juntarComecoMeioFim(arquivo, comeco, meio, fim);
this.escreverArquivo(texto, arquivo);
return texto;
 }else{
    // Se a 1ª linha não for \begin não modifica documento 
 }
        /* Se o tipo for Agradecimentos*/
        }else if(tipo.equals("agradecimento")){
        
            comeco="\\chapter*{Agradecimentos}"+
        "\n\\" +
        "\n\\vfill\n" +
        "\n" +
        "\\begin{flushright}\n" +
        "\\hfill \\textit";
        
        fim = "\\end{flushright}\n" +
        "\n" +
        "\\vspace*{1cm}\n" +
        "\n" +
        "\\clearpage";
        texto = juntarComecoMeioFim(arquivo, comeco, meio, fim);
        this.escreverArquivo(texto, arquivo);
        return texto;
         
        /* Se o tipo for epigrafe ou dedicatória*/
        }else if(tipo.equals("epigrafe") || tipo.equals("dedicatoria")){
        comeco="\\" +
        "\n\\vfill\n" +
        "\n" +
        "\\begin{flushright}\n" +
        "\\hfill \\textit";
        
        fim = "\\end{flushright}\n" +
        "\n" +
        "\\vspace*{1cm}\n" +
        "\n" +
        "\\clearpage";
        texto = juntarComecoMeioFim(arquivo, comeco, meio, fim);
        this.escreverArquivo(texto, arquivo);
        return texto;

        }else if(tipo.equals("referencias")){
         comeco="\\chapter*{Refer\\^encias}\n"
                 + "{\\raggedright\n\n";
        fim = "\n}";
        texto = juntarComecoMeioFim(arquivo, comeco, meio, fim);
        this.escreverArquivo(texto, arquivo);
        return texto;
        
        }else if(tipo.equals("obras")){
         comeco="\\chapter*{Obras Consultadas}\n"
                 + "{\\raggedright\n\n";
        fim = "\n}";
        texto = juntarComecoMeioFim(arquivo, comeco, meio, fim);
        this.escreverArquivo(texto, arquivo);
        return texto;
         

        }else{
        //System.out.println("Erro, tipo de Arquivo: "+tipo+", Não existe");
            texto = juntarComecoMeioFim(arquivo, "", "", "");
            return texto;
        }
        return null;
    }  
    
//////JUNTA MODIFICAÇÕES NO COMEÇO E FIM E O TEXTO ORIGINAL, SOBESCREVENDO O TEX//////////////////////////////////////
    public String juntarComecoMeioFim(File arquivo, String comeco, String meio, String fim) throws FileNotFoundException, IOException{
        Scanner scanner = new Scanner(new FileInputStream(arquivo.getAbsoluteFile()), "utf-8")
                       .useDelimiter("\\||\\n");
     while (scanner.hasNext()) {
         meio = meio + scanner.nextLine() + "\n";
    }
    scanner.close();
    String texto = comeco + meio + fim;
    //System.out.println(texto);    
    return texto;
    }
    
 /////////////////// CONVERTE ODT PARA TEX//////////////////////////////////////////////////////////////////////////  
    
    //Converte arquivo odt para Tex
    public File odtToTex(File arquivo, String tipo) throws IOException{

//Pega nome da File (Ex: desktop/pasta/documento para documento
String nomeArquivo = arquivo.getName().replace( ".odt", "" ); 
//nomeArquivo = nomeArquivo.replace( " ", "_" ); 
if(!tipo.equals("")){
nomeArquivo = tipo;
}
    // Cria um conversor LaTeX
Converter converter =
    ConverterFactory.createConverter("application/x-latex");

// Configura o conversor por meio do arquivo (myoconfig.xml)
Config config = converter.getConfig();
config.read(new File("myconfig.xml"));
config.setOption("inputencoding","UTF-8");

// Converte o documento odt para tex)
ConverterResult result =
    converter.convert(arquivo,
        nomeArquivo+".tex");

// Escreve o arquivo convertido
result.write(new File("LaTex"));
//Apaga todo código ruim gerado pelo Writer2LaTe
apagarTextoArquivo(new File("LaTex\\"+nomeArquivo+".tex"));
        return new File("LaTex\\"+nomeArquivo+".tex");
        
    }


/////////////////// APAGAR CÓDIGO DESNECESSÁRIO GERADO PELO WRITER2LATEX //////////////////////////////////////////////////////////////////////////////////////////////      

public static void apagarTextoArquivo(File arquivo) throws IOException{
Path path = Paths.get(arquivo.getAbsoluteFile().toString());


String content = new String(Files.readAllBytes(path));

//Para trocar uma barra \ com o replace é necessário escrever 4
content = content.replace("\\rmfamily", "");
content = content.replace("\\selectlanguage{portuges}","");
content = content.replace("\\endinput", "");
content = content.replace("\\bfseries", "");

content = content.replace("\\ {}- ", " - ");
content = content.replace("\\ {}-\\ ", " - ");


//content = content.replaceAll("\bfseries", "");
//content = content.replaceAll("\\\\clearpage$2", "");//como remover isso\clearpage{
//content = content.replaceAll("\\\\clearpage", "");

//content = content.replaceAll("\\pagestyle{MP}", "");
Files.write(path, content.getBytes());

//arrumar?
}

 /////////////////// GERADOR DE ARQUIVO TEX APARTIR DE STRING DE TEXTO /////////////////////////////////////////////////////////////////////////////  
    
//método que transforma uma String (texto) em um arquivo
public static void escreverArquivo(String texto, File arquivoTex) throws UnsupportedEncodingException, FileNotFoundException, IOException{
String caminhoArquivo = arquivoTex.getAbsoluteFile().toString();
OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream(caminhoArquivo),"UTF-8");
bufferOut.write(texto);
bufferOut.flush();
bufferOut.close();

}

 /////////////////// GERADOR DE ARQUIVO PDF APARTIR DE ARQUIVO TEX /////////////////////////////////////////////////////////////////////////////  
public void gerarPDF(File caminho) throws UnsupportedEncodingException,
        FileNotFoundException, IOException{
    File tex, pdf, workingDirectory, salvarEm;
    String current = new java.io.File( "." ).getCanonicalPath();
    tex = new File(current+"\\LaTex\\principal.tex");
    workingDirectory = caminho;
    salvarEm = caminho;
    
    JLRGenerator pdfGen = new JLRGenerator();
    
    pdfGen.generate(tex, salvarEm, workingDirectory);
    pdfGen.generate(tex, salvarEm, workingDirectory);
    
    pdf = pdfGen.getPDF();
    JLROpener.open(pdf);
}
}