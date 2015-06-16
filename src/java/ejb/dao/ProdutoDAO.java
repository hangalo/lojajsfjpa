/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.dao;

import jpa.entidades.Produto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author praveen
 */
@Stateless
public class ProdutoDAO extends DAOJPAGenerico<Produto> {

    public List<Produto> buscaPorDescricao(String descricao) {

        Query q = em.createQuery("SELECT object(p) FROM Produto AS p WHERE (p.descricao LIKE :parametro) OR(p.detalhe LIKE :parametro)");
        q.setParameter("paramentro", "%" + descricao + "%");
        return q.getResultList();
    }

    public List<Produto> buscaPorMarca(String marca) {

        Query q = em.createQuery("SELECT object(p) FROM Produto p WHERE (p.marca LIKE :parametro)");
        q.setParameter("parametro", marca);
        return q.getResultList();
    }

    public List<Produto> buscaPorModelo(String modelo) {

        Query q = em.createQuery("SELECT object(p) FROM Produto p WHERE (p.modelo LIKE :parametro)");
        q.setParameter("parametro", modelo);
        return q.getResultList();
    }

    public List<Produto> buscaPorFamilia(Long idFamilia) {
        Query q = em.createQuery("SELECT object(p) FROM Produto AS p WHERE (p.familiaProduto.id =:idFamilia)");
        q.setParameter("idFamilia", idFamilia);
        return q.getResultList();

    }

    public List<Produto> buscaTodos() {

        Query q = em.createQuery("SELECT object(p) FROM Produto AS p");
        return q.getResultList();

    }

    public List<Produto> buscaPorIntervalo(int inicio, int tamanho) {

        Query q = em.createQuery("SELECT object(p) FROM Produto AS p");
        q.setFirstResult(inicio);
        q.setMaxResults(tamanho);
        return q.getResultList();

    }

    public int contador() {

        Query q = em.createQuery("SELECT count(p) FROM Produto AS p");
        return q.getFirstResult();

    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
