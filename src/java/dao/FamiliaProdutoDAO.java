/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.FamiliaProduto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author praveen
 */
@Stateless
public class FamiliaProdutoDAO extends DAOJPAGenerico<FamiliaProduto> {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List< FamiliaProduto> buscaPorNome(String nome) {
        Query q = em.createQuery("SELECT object(f) FROM FamiliaProduto as f WHERE f.nome LIKE :parametro");
        q.setParameter("paramento", "%" + nome + "%");
        return q.getResultList();
    }

    public List<FamiliaProduto> buscaPorAbreviatura(String abreviatura) {

        Query q = em.createQuery("SELECT object(f) FROM FamiliaProduto AS f WHERE f.abreviatura LIKE :parametro");
        q.setParameter("paramentro", "%" + abreviatura + "%");
        return q.getResultList();
    }
    
    
    public List<FamiliaProduto> bustaTudasFamilias(){
    Query q = em.createQuery("SELECT object(f) FROM FamiliaProduto AS f");
    return q.getResultList();    
    }
    
    
    public List<FamiliaProduto> buscaPorIntervaloFamilia(int inicio, int tamanho){
      Query q = em.createQuery("SELECT object(f) FROM FamiliaProduto AS f");
      q.setFirstResult(inicio);
      q.setMaxResults(tamanho);
      return q.getResultList();
    
    }
    
    
    public int contador(){
    Query q = em.createQuery("SELECT COUNT(f) FROM FamiliaProduto AS f");
    return q.getFirstResult();    
    }
    
    
}
