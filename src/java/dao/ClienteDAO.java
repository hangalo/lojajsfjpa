/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.Cliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author praveen
 */
@Stateless
public class ClienteDAO extends DAOJPAGenerico<Cliente> {

    public Cliente buscaPorNIF(String nif) {
        Query q = em.createQuery("SELECT object(c) FROM Cliente AS c WHERE c.nif =:nif");
        q.setParameter("nif", nif);

        List<Cliente> resultados = q.getResultList();
        if (resultados == null) {

            return null; // nao encontrado;
        } else if (resultados.size() != 1) {
            return null; //nao encontrador

        } else {

            return resultados.get(0); // Devolve o encontrado
        }

    }

    public List<Cliente> buscarPorNome(String nome) {

        Query q = em.createQuery("SELECT object(c) FROM Cliente AS c WHERE (c.nome LIKE :parametro OR c.sobrenome LIKE :parametro)");
        q.setParameter("parametro", "%" + nome + "%");
        return q.getResultList();
    }

    public Cliente buscaPorLogin(String login) {
        Query q = em.createQuery("SELECT object(c) FROM Cliente AS c WHERE c.utilizador.login =:login");
        q.setParameter("login", login);
        List<Cliente> resultados = q.getResultList();
        if (resultados == null) {
            return null; //nao encontrado
        } else if (resultados.size() != 1) {
            return null;
        } else {
            return resultados.get(0);
        }

    }

    public int contador() {
        Query q = em.createQuery("SELECT COUNT(c) FROM Cliente AS c");
        return q.getFirstResult();
    }

    public List<Cliente> buscaTodosClientes() {
        Query q = em.createQuery("SELECT object(c) FROM Cliente AS c");
        return q.getResultList();

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
