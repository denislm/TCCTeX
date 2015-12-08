package br.metodista.TCCtex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import de.nixosoft.jlr.JLRConverter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Denis
 */
public class InfoEssenciais {
    private String curso;
    private String faculdade;
    private ArrayList<String> autor = new ArrayList<String>();
    private String titulo;
    private String subTitulo;
    private String instituicao;
    private String cidade;
    private int ano=0;
    private String orientador;
    private String coorientador;
    private String natureza;
    private String tipoCurso;
    private ArrayList<String> banca = new ArrayList<String>();
    
// public void preencherTodosLayouts() throws IOException{
// this.preencherLayout(new File("Templates\\principal.tex"));
// }
    //Método não terminado
public void preencherLayout(File t) throws IOException{
File workingDirectory = new File("Templates\\");
JLRConverter converter = new JLRConverter(workingDirectory);
File template = new File("LaTex\\" + t.getName());
File gerado = new File("LaTex\\" + t.getName());

converter.replace("Faculdade", faculdade);

ArrayList<ArrayList<String>> services = new ArrayList<ArrayList<String>>();
services.add(autor);
converter.replace("Instituicao", instituicao);
converter.replace("Autores", autor);
converter.replace("Curso", curso);
converter.replace("Titulo", titulo.toUpperCase());
converter.replace("SubTitulo", subTitulo.toUpperCase());
converter.replace("Ano", ano);
converter.replace("Cidade", cidade.toUpperCase());
converter.replace("TipoCurso", tipoCurso.toUpperCase());
converter.replace("Faculdade", faculdade);
converter.replace("Orientador", orientador);
converter.replace("Coorientador", coorientador);
converter.replace("Banca", banca);
converter.parse(template, gerado);

autor.clear();
}

public boolean verificarInformacoesPreenchidas(){
    if(!(instituicao.equals("") || faculdade.equals("") || autor.get(0).equals("") 
            || titulo.equals("") || cidade.equals("") || ano==0
            || natureza.equals("") || tipoCurso.equals("") 
            || orientador.equals("") 
            )){
    return true;
    }
    return false;
}


    /**
     * Getters e Setters
     */
    public String getInstituicao() {
        return instituicao;
    }

    /**
     * @param instituicao the instituicao to set
     */
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public void setFaculdade(String x) {
        this.faculdade = x;
    }

    /**
     * @return the faculdade
     */
    public String getFaculdade() {
        return faculdade;
    }

    /**
     * @return the curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the subTitulo
     */
    public String getSubTitulo() {
        return subTitulo;
    }

    /**
     * @param subTitulo the subTitulo to set
     */
    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * @return the orientador
     */
    public String getOrientador() {
        return orientador;
    }

    /**
     * @param orientador the orientador to set
     */
    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }

    /**
     * @return the coOrientador
     */
    public String getCoOrientador() {
        return coorientador;
    }

    /**
     * @param coOrientador the coOrientador to set
     */
    public void setCoOrientador(String coOrientador) {
        this.coorientador = coOrientador;
    }

    /**
     * @return the natureza
     */
    public String getNatureza() {
        return natureza;
    }

    /**
     * @param natureza the natureza to set
     */
    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    /**
     * @return the tipoCurso
     */
    public String getTipoCurso() {
        return tipoCurso;
    }

    /**
     * @param tipoCurso the tipoCurso to set
     */
    public void setTipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    /**
     * @return the autor
     */
    public ArrayList<String> getAutor() {
        return autor;
    }

    
public ArrayList<String> separarAutores(String x){
        String[] autores = x.split(", ");
        for(int i = 0; i<=autores.length-1; i++){
            
            this.autor.add(autores[i]);
        }    
    
    return autor;

}
    public void setAutor(ArrayList<String> x) {
        this.autor = x;
    }
    
public ArrayList<String> separarBanca(String x){
        String[] professoresBanca = x.split(", ");
        for(int i = 0; i<=professoresBanca.length-1; i++){
            
            this.getBanca().add(professoresBanca[i]);
        }    
    
    return autor;

}

    /**
     * @return the banca
     */
    public ArrayList<String> getBanca() {
        return banca;
    }

    /**
     * @param banca the banca to set
     */
    public void setBanca(ArrayList<String> banca) {
        this.banca = banca;
    }

    void preencherTodosLayouts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
