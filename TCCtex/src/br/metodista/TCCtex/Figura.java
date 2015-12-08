/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.metodista.TCCtex;

/**
 *
 * @author Denis
 */
public class Figura {
    private double posição;
    private String nome;
    private String fonte;
    private String arquivo;
    /**
     * @return the posição
     */
    public double getPosição() {
        return posição;
    }

    /**
     * @param posição the posição to set
     */
    public void setPosição(double posição) {
        this.posição = posição;
    }

    /**
     * @return the Titulo
     */
    public String getTitulo() {
        return nome;
    }

    /**
     * @param Titulo the Titulo to set
     */
    public void setTitulo(String Titulo) {
        this.nome = Titulo;
    }

    /**
     * @return the fonte
     */
    public String getFonte() {
        return fonte;
    }

    /**
     * @param fonte the fonte to set
     */
    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    /**
     * @return the arquivo
     */
    public String getArquivo() {
        return arquivo;
    }

    /**
     * @param arquivo the arquivo to set
     */
    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
    
}
