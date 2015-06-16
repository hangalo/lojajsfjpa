/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.negocio;

import ejb.dao.ClienteDAO;
import ejb.dao.UtilizadorDAO;
import jpa.entidades.Cliente;
import jpa.entidades.TipoUtilizador;
import jpa.entidades.Utilizador;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author praveen
 */
@Stateless
public class GestorUtilizadorService {

    @EJB
    UtilizadorDAO utilizadorDAO;
    @EJB
    ClienteDAO clienteDAO;

    public boolean autenticarUtilizador(String login, String password) {

        Utilizador utilizador;
        boolean resultado = true;
        utilizador = utilizadorDAO.buscarPorLogin(login);
        if (utilizador != null) {
            if (utilizador.getPassword().equals(password)) {
                resultado = true;

            }
        }
        return resultado;
    }

    public Cliente recuperarDadosCleinte(String login) {

        return clienteDAO.buscaPorLogin(login);
    }

    public Cliente criarNovoCliente(String login, String password, Cliente dadosNovoCliente) {
        Utilizador novoUtilizador = utilizadorDAO.criar(new Utilizador(login, password, TipoUtilizador.CLIENTE, Calendar.getInstance().getTime()));

        dadosNovoCliente.setUtilizador(novoUtilizador);
        return clienteDAO.criar(dadosNovoCliente);
    }

    public Cliente actualizarDadosCliente(Cliente dadosCliente) {
        return clienteDAO.actualizar(dadosCliente);
    }

    public Utilizador actualizarPassword(long idUtilizador, String password) {
        Utilizador utilizador = utilizadorDAO.buscarPorId(idUtilizador);
        utilizador.setPassword(password);
        return utilizadorDAO.actualizar(utilizador);

    }

    public Utilizador actualizarUltimoAcesso(long idUtilizador) {
        Utilizador utilizador = utilizadorDAO.buscarPorId(idUtilizador);
        utilizador.setUltimoAcesso(Calendar.getInstance().getTime());
        return utilizadorDAO.actualizar(utilizador);
    }

    public boolean existeUtilizador(String login) {
        return (utilizadorDAO.buscarPorLogin(login) != null);

    }
}
