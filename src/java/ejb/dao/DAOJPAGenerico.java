/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.dao;

import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author desenvolvimento
 */
public abstract class DAOJPAGenerico<T> implements InterfaceGenericaDAO<T> {

    @PersistenceContext(unitName = "lojajsfjpaPU")
    protected EntityManager em;

    @Override
    public T criar(T entidade) {
        em.persist(entidade);
        return entidade;
    }

    @Override
    public T actualizar(T entidade) {
        return em.merge(entidade);

    }

    @Override
    public void eliminar(T entidade) {
        em.remove(em.merge(entidade)); // actualiza e el
    }

    @Override
    public T buscarPorId(Object id) {
        Class<T> classEntidade = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        //identifica a class real das ententidade geridas pelo objecto T.Class
        return em.find(classEntidade, id);
    }

}
