/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.negocio;

import ejb.dao.FamiliaProdutoDAO;
import ejb.dao.ProdutoDAO;
import jpa.entidades.FamiliaProduto;
import jpa.entidades.Produto;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author praveen
 */
@Stateless
public class CatalogoService {

    @EJB
    FamiliaProdutoDAO familiaDAO;
    @EJB
    ProdutoDAO produtoDAO;

    public List<Produto> produtosPorDescricao(String descricao) {
        return produtoDAO.buscaPorDescricao(descricao);

    }

    public List<Produto> produtosPorFamilia(Long idFamilia) {

        return produtoDAO.buscaPorFamilia(idFamilia);
    }

    public List<Produto> produtosPorMarca(String marca) {
        return produtoDAO.buscaPorMarca(marca);

    }

    public List<Produto> produtos() {
        return produtoDAO.buscaTodos();
    }

    public List<Produto> buscarPorIntervalo(int inicio, int tamanho) {
        return produtoDAO.buscaPorIntervalo(inicio, tamanho);
    }

    public Produto produtoPorID(long idProduto) {
        return produtoDAO.buscarPorId(idProduto);
    }

    public List<FamiliaProduto> familiasPorNome(String nome) {
        return familiaDAO.buscaPorNome(nome);

    }

    public List<FamiliaProduto> familias() {
        return familiaDAO.bustaTudasFamilias();
    }

    public FamiliaProduto familiaPorID(long idFamilia) {
        return familiaDAO.buscarPorId(idFamilia);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
