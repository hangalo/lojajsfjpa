/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dao.ClienteDAO;
import dao.UtilizadorDAO;
import entidades.Utilizador;
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

}
