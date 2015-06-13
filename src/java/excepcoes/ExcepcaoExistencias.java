/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepcoes;

/**
 *
 * @author desenvolvimento
 */
public class ExcepcaoExistencias extends Exception{
    long idProduto;
    long quantidade;
    String descricaoProduto;

    public ExcepcaoExistencias(long idProduto, long quantidade, String descricaoProduto) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.descricaoProduto = descricaoProduto;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
    
}






































