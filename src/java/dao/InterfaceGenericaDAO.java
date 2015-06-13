/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author desenvolvimento
 */
public interface InterfaceGenericaDAO <T>{
    T criar(T entidade);
    T actualizar(T entidade);
    void eliminar(T entidade);
    T buscarPorId(Object id);
}



















