/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.TipoUtilizador;
import entidades.Utilizador;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author praveen
 */
@Stateless
public class UtilizadorDAO extends DAOJPAGenerico<Utilizador> {

    public Utilizador buscarPorLogin(String login) {
        Query q = em.createQuery("SELECT object(u) FROM Utilizador AS u WHERE U.login =:login");
        q.setParameter("login", login);

        List<Utilizador> resultados = q.getResultList();

        if (resultados == null) {
            return null;
        } else if (resultados.size() != 1) {
            return null;
        } else {

            return resultados.get(0);
        }
    }

    public List<Utilizador> buscaUtilizadorPorTipo(TipoUtilizador tipoUtilizador) {

        Query q = em.createQuery("SELECT object(u) FROM Utilizador AS u WHERE u.tipoUtilizador =:tipoUtilizador");
        q.setParameter("tipoUtilizador", tipoUtilizador);
        return q.getResultList();

    }

    public List<Utilizador> buscarTodos() {
        Query q = em.createQuery("SELECT object(u) FROM Utilizador AS u");
        return q.getResultList();

    }

    public int contador() {
        Query q = em.createQuery("SELECT count(u) FROM Utilizador AS u");
        return q.getFirstResult();

    }
// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
