/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.metodista.TCCtex;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author Denis
 */
public class PosTextuais {
        static String alinhamentoPDF, pdfSemExt;
    private File referencias, obrasConsultadas, rrbq, cronograma;
    private LinkedList<File> atas = new LinkedList();
    static int cont;
    String atasTex;
    LinkedList<String> apendicesTex = new LinkedList(), 
    anexosTeX = new LinkedList(),
    outrosAnexosTex = new LinkedList();
    Conversor c = new Conversor();
///////////////////////////////////////////////////////////////////////////////
    public void converterPosTextuais() throws IOException{
        (new File("LaTeX/anexo")).mkdirs();
        (new File("LaTeX/apendice")).mkdirs();
        if(referencias.exists() && getRrbq().exists()){
            //Reseta Lista de apendices e anexos
            apendicesTex = new LinkedList();anexosTeX = new LinkedList();atasTex="";
            //Converte ODT das Referencias de ODT para Tex
            if(referencias.toString().endsWith(".odt") && referencias.exists()){
                c.alterarTex(c.odtToTex(referencias,"referencias"),"referencias");
            }//else Insira um arquivo ODT
            
            //Converte RRBQ de PDF para String com código Tex, inserindo na lista de Anexos
            if((getRrbq().toString().endsWith(".pdf") && rrbq.exists())){    
                pdfToTex(getRrbq(),
                tituloSemAcento("Relatório de Recomendações da Banca de Qualificação"),
                    "anexo");
            }//else Insira um arquivo PDF
            try{
                
            //Converte Obras Consultadas de ODT para Tex
            if((obrasConsultadas.toString().endsWith(".odt") && obrasConsultadas.exists())){
            c.alterarTex(c.odtToTex(getObrasConsultadas(),"obras_consultadas"),"obras");   
            }
            }catch(NullPointerException e){
            }
            
            //Converte Atas de PDF para String com código Tex, inserindo na lista de Anexos
            if(atas.size()>0){
            for(int i =0; i<getAtas().size();i++){
            pdfToTex(atas.get(i),"Atas de Reuniões com Orientador","ata");  
            }
            anexosTeX.add(unirAtas());
            }
           
            //Converte Cronograma de PDF para String com código Tex, inserindo na lista de Anexos
            if(cronograma.exists() && cronograma.toString().endsWith(".pdf")){
            pdfToTex(getCronograma(),"Cronograma","anexo");  
            }//else Insira um arquivo PDF
                   
            //Ao final pega pasta do software
            String current = new java.io.File( "." ).getCanonicalPath();
            //Cria um arquivo Anexos.tex com todos anexos
            File anexos = new File(current+"\\LaTex\\anexo\\Anexos.tex");
            c.escreverArquivo(gerarAnexos(), anexos);
            //Salva texto de todos apendices
            String textoApendices = gerarApendices();
            //Se existem apendices então gera arquivo Apendices.tex
            if(!textoApendices.equals("")){
                File apendices = new File(current+"\\LaTex\\apendice\\Apendices.tex");
                c.escreverArquivo(textoApendices, apendices);
            }
            
        
        }//else Insira todos arquivos Necessários   
    }
///////////////Método de TESTE/////////////////////////////////////////////////
    public void pdfToTex(File pdfEntrada, String titulo, String tipo) throws IOException{
        String comeco, meio, fim, angulo= pegaAngulo(pdfEntrada);
     String current = new java.io.File( "." ).getCanonicalPath();
     File pdfSaida;
     if(tipo.equals("ata")){
     pdfSaida = new File(current+"\\LaTex\\anexo\\"+pdfEntrada.getName());
     }else{
     pdfSaida = new File(current+"\\LaTex\\"+tipo+"\\"+pdfEntrada.getName());
     }
     copy(pdfEntrada,pdfSaida);
     
     if(tipo.equals("ata")){
     meio = "\\includepdf[pages=-,pagecommand={},offset=0 -30,templatesize={145mm}{210mm}"+angulo+"]{anexo/"+pdfSaida.getName()+"}\n";
     atasTex = atasTex + meio;
     }
     
     if(tipo.equals("anexo")){
     comeco = "\\chapter{"+titulo+"}\n\n";
     meio = "\\includepdf[pages=-,pagecommand={},offset=0 -30,templatesize={145mm}{210mm}"+angulo+"]{anexo/"+pdfSaida.getName()+"}";
     fim = "";
     anexosTeX.add(comeco+meio+fim);
     }
     
     if(tipo.equals("apendice")){
     comeco = "\\chapter{"+titulo+"}\n\n";
     meio = "\\includepdf[pages=-,pagecommand={},offset=0 -30,templatesize={145mm}{210mm}"+angulo+"]{apendice/"+pdfSaida.getName()+"}";
     fim = "";
     apendicesTex.add(comeco+meio+fim);
     }
     
    }
    
    private String pegaAngulo(File pdfFile) throws FileNotFoundException, IOException{
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdf = new PDFFile(buf);
                PDFPage pagina = pdf.getPage(1);
             Rectangle folha = new Rectangle(0, 0, (int) pagina.getBBox().getWidth(),
                (int) pagina.getBBox().getHeight());
             if(folha.width > folha.height){
                 System.out.println("Largura: "+folha.width+"\tAltura: "+folha.height);
                 //se horizontal vira 90 graus
                return ",angle=90";
            }else{
                 //se vertical: angulo não declarado
                return "";
        }
    }
    
/////////Utiliza o método pegaImgPag para converter todas páginas de um PDF para imagem PNG////
    private static void pdfToImg(File pdfFile,String tipo) throws FileNotFoundException, IOException {
     RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdf = new PDFFile(buf);
         String current = new java.io.File( "." ).getCanonicalPath();
            pdfSemExt = pdfFile.getName().toString().replace(".pdf", "");
            if(tipo.equals("ata"))
                tipo = "anexo";
            File pasta = new File(current+"\\LaTex\\"+tipo+"\\"+pdfSemExt+"-img");
            pasta.mkdir();
        for (cont=1; cont<=pdf.getNumPages(); cont++)
            pegaImgPag(pdf.getPage(cont), pasta+"\\"+cont+".jpg");
        
             
            System.out.println(alinhamentoPDF);    
    
    }
///////////Converte uma página de um arquivo PDF para imagen JPG////////////////
    private static void pegaImgPag(PDFPage page, String destination) throws IOException{
        Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
                (int) page.getBBox().getHeight()); 
        BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height,
                         BufferedImage.TYPE_INT_RGB);

        Image image = page.getImage(rect.width, rect.height,    // width & height
                   rect,                       // clip rect
                   null,                       // null for the ImageObserver
                   true,                       // fill background with white
                   true                        // block until drawing is done
        );
        Graphics2D bufImageGraphics = bufferedImage.createGraphics();
        bufImageGraphics.drawImage(image, 0, 0, null);
        ImageIO.write(bufferedImage, "JPG", new File( destination ));
        
        //Se largura for maior que altura
        if(rect.width > rect.height){
            alinhamentoPDF = "horizontal";
            System.out.println("Largura: "+rect.width+"\tAltura: "+rect.height);
        }else{
            alinhamentoPDF = "vertical";
        }
    }
////////////Cria o código Tex com todas imagens de apenas um Anexo//////////////
        public void imgToTex(File pdf, String titulo, String tipo) throws IOException {
        String comeco, meio="", fim, pasta;
        pdfToImg(pdf,tipo);
        String current = new java.io.File( "." ).getCanonicalPath();
        if (tipo.equals("ata")){
            pasta = "anexo/"+pdfSemExt+"-img";
        }else{
            pasta = tipo+"/"+pdfSemExt+"-img";        
        }
            comeco = "\n\n\\chapter{ "+titulo+"}\n"
                    + "\\begin{changemargin}{-2cm}{0.5cm}\n"
                    + "\\begin{center}\n";
            for(int i = 0; i<(cont-1);i++){
            if(alinhamentoPDF.equals("horizontal")){
            meio = meio+"\\includegraphics[angle =90, max height=680px, max width=505px, keepaspectratio]{"
                    +pasta+"/"+ (i+1) +".jpg}\\\\\n";
            }else{
            meio = meio+"\\includegraphics[max height=680px,max width=505px, keepaspectratio]{"
                    +pasta+"/"+ (i+1) +".jpg}\\\\\n";
            }
            }
            fim = "\\end{center}\n"
                + "\\end{changemargin}";
        if(tipo.equals("anexo")){
        anexosTeX.add(comeco+meio+fim);
        }else if(tipo.equals("apendice")){
        apendicesTex.add(comeco+meio+fim);
        }else if(tipo.equals("ata")){
        atasTex = atasTex + meio;
        }
    }
////////// Uni todo código das Atas em um só código Tex/////////////////////////
        public String unirAtas(){
            String comeco, meio, fim;
        comeco = "\n\n\\chapter{Atas de Reuniões com Orientador}\n"
                    + "\\begin{changemargin}{-2cm}{0.5cm}\n"
                    + "\\begin{center}\n";
        meio = atasTex;
        fim = "\\end{center}\n"
                + "\\end{changemargin}";
        return comeco+meio+fim;
        }
//////////Gera uma String com código Tex, com todos Apendices///////////////////
      public String gerarApendices(){
          if(apendicesTex.size()>0){
      String comeco, meio="";
      comeco = 
"\\apendices\n" +
"\n" +
"\\renewcommand{\\ABNTtravessao}{-}\n" +
"\\setlength{\\ABNTanapindent}{0cm}\n";
      for(int i=0; i<apendicesTex.size(); i++){
      meio = meio + apendicesTex.get(i);
      }
      return comeco + meio;
          }
            return "";
      }
/////////Gera uma String com código Tex, com todos anexos//////////////////////      
      public String gerarAnexos(){
          if(!anexosTeX.get(0).equals("")){
      String comeco, meio="";
      comeco = 
"\\apendices\n" +
"\n" +
"\\renewcommand{\\ABNTtravessao}{-}\n" +
"\\setlength{\\ABNTanapindent}{0cm}\n\n"+ 
"\\renewcommand{\\ABNTanapsize}{\\flushleft\\large} %Altera o tamanho do titulo de Anexo\n\n";
      for(int i=0; i<anexosTeX.size(); i++){
      meio = meio + anexosTeX.get(i);
      }
      for(int i=0; i<outrosAnexosTex.size(); i++){
      meio = meio + outrosAnexosTex.get(i)+"\n\n";
      }
      return comeco + meio;
          }
          return "";
      }
/////////Troca Acentuação de títulos para o padrão LaTeX////////////////////////
            public String tituloSemAcento(String a){
                
        a = a.replace("à", "\\`a");
        a = a.replace("á", "\\'a");
        a = a.replace("ã", "\\~a");  
        a = a.replace("â", "\\^a");
        a = a.replace("é", "\\'e"); 
        a = a.replace("ê", "\\^e"); 
        a = a.replace("ó", "\\'o"); 
        a = a.replace("õ", "\\~o"); 
        a = a.replace("ô", "\\^o"); 
        a = a.replace("ú", "\\’u"); 
        
        a = a.replace("À", "\\`A");
        a = a.replace("Á", "\\'A");
        a = a.replace("Ã", "\\~A");  
        a = a.replace("Â", "\\^A");
        a = a.replace("É", "\\'E"); 
        a = a.replace("Ê", "\\^E"); 
        a = a.replace("Ó", "\\'O"); 
        a = a.replace("Õ", "\\~O"); 
        a = a.replace("Ô", "\\^O"); 
        a = a.replace("Ú", "\\'U");

        
        a = a.replace("í", "\\’{\\i}"); 
        a = a.replace("Í", "\\’I"); 
        a = a.replace("ç", "\\c{c}"); 
        a = a.replace("Ç", "\\c{C}");

          return a;
          
      }
/////////////Método de TESTE para copiar ARquivo/////////////////////////////////
    public static void copy(File origem, File destino) throws IOException {
        Date date = new Date();
        InputStream in = new FileInputStream(origem);
        OutputStream out = new FileOutputStream(destino);           
        // Transferindo bytes de entrada para saída
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght= in.read(buffer)) > 0) {
            out.write(buffer, 0, lenght);
        }
        in.close();
        out.close();
        Long time = new Date().getTime() - date.getTime();
    }
   
    /** 
     * Copia arquivos de um local para o outro 
     * @param origem - Arquivo de origem 
     * @param destino - Arquivo de destino 
     * @param overwrite - Confirmação para sobrescrever os arquivos 
     * @throws IOException 
     */ 
    public static void copy(File origem, File destino, boolean overwrite) throws IOException{ 
        Date date = new Date();
       if (destino.exists() && !overwrite){ 
          System.err.println(destino.getName()+" já existe, ignorando..."); 
          return; 
       } 
       FileInputStream fisOrigem = new FileInputStream(origem); 
       FileOutputStream fisDestino = new FileOutputStream(destino); 
       FileChannel fcOrigem = fisOrigem.getChannel();   
       FileChannel fcDestino = fisDestino.getChannel();   
       fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);   
       fisOrigem.close();   
       fisDestino.close(); 
       Long time = new Date().getTime() - date.getTime();
       System.out.println("Saiu copy"+time);
    }

////////////////Getters e Setters///////////////////////////////////////////////
    /**
     * @return the referencias
     */
    public File getReferencias() {
        return referencias;
    }

    /**
     * @param referencias the referencias to set
     */
    public void setReferencias(File referencias) {
        this.referencias = referencias;
    }

    /**
     * @return the rrbq
     */
    public File getRrbq() {
        return rrbq;
    }

    /**
     * @param rrbq the rrbq to set
     */
    public void setRrbq(File rrbq) {
        this.rrbq = rrbq;
    }

    /**
     * @return the cronograma
     */
    public File getCronograma() {
        return cronograma;
    }

    /**
     * @param cronograma the cronograma to set
     */
    public void setCronograma(File cronograma) {
        this.cronograma = cronograma;
    }

    /**
     * @return the atas
     */
    public LinkedList<File> getAtas() {
        return atas;
    }

    /**
     * @param atas the atas to set
     */
    public void setAtas(LinkedList<File> atas) {
        this.atas = atas;
    }

    /**
     * @return the obrasConsultadas
     */
    public File getObrasConsultadas() {
        return obrasConsultadas;
    }

    /**
     * @param obrasConsultadas the obrasConsultadas to set
     */
    public void setObrasConsultadas(File obrasConsultadas) {
        this.obrasConsultadas = obrasConsultadas;
    }

}
