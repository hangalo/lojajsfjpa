/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.negocio.GestorUtilizadorService;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.entidades.Cliente;
import jpa.entidades.Utilizador;

/**
 *
 * @author desenvolvimento
 */
@ManagedBean(name = "utilizadorController")
@SessionScoped
public class UtilizadorController extends BaseController {

    private Utilizador utilizadorActual = null;
    private Cliente clienteActual = null;
    private String login = "";
    private String password = "";
    private String password2 = "";
    private boolean novoUtilizador = true;

    @EJB
    private GestorUtilizadorService gestorUtilizadorService;

    public UtilizadorController() {
    }
    // Accoes para as paginas JSF

    public String doLogin() {
        String destino = null;

        if (gestorUtilizadorService.autenticarUtilizador(login, password)) {
            clienteActual = gestorUtilizadorService.recuperarDadosCleinte(login);
            utilizadorActual = clienteActual.getUtilizador();
            password2 = password;
            novoUtilizador = false;
            destino = "exito.login";

        } else {

            destino = "falha.login";
        }

        return destino;
    }

    public String doLogout() {

        String destino = null;
        if (utilizadorActual != null) {
            gestorUtilizadorService.actualizarUltimoAcesso(utilizadorActual.getId());

        }
        utilizadorActual = null;
        clienteActual = null;
        login = "";
        password = "";
        password2 = "";
        novoUtilizador = true;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        destino = "exito.logoute";
        return destino;
    }

    public String doNovoUtilizador() {
        novoUtilizador = true;
        utilizadorActual = new Utilizador();
        clienteActual = new Cliente();
        login = "";
        password = "";
        password2 = "";

        return "utilizador.novo";
    }

    public String doCriaUtilizador() {
        String destino = null;
        if (login.equals("")) {

            adicionarMessagemErrot("Nao foi indicado o nome do utilizador");
        } else if (password.equals("")) {
            adicionarMessagemErrot("Nao foi inserida a password");
        } else if (password2.equals("")) {
            adicionarMessagemErrot("Deve repetir a insercao da passaword");
        } else if (!password.equals(password2)) {
            adicionarMessagemErrot("As passwords introduzidas nao coincidem");
        } else if (gestorUtilizadorService.existeUtilizador(login)) {
            adicionarMessagemErrot("O nome do utilizador" + login + "ja existe");
        } else {

            clienteActual = gestorUtilizadorService.criarNovoCliente(login, password, clienteActual);
            utilizadorActual = clienteActual.getUtilizador();
            novoUtilizador = false;
            destino = "utilizador.criado";

        }
        return destino;

    }

    public String doActualizarUtilizador() {
        String destino = null;
        if (password.equals("")) {
            adicionarMessagemErrot("Nao foi introduzida nenhuma password");
        } else if (password2.equals("")) {
            adicionarMessagemErrot("Nao foi repetida a password - reintroduzir");
        } else if (!password.equals(password2)) {
            adicionarMessagemErrot("As passwords introduzidas nao coincidem");

        } else {
            gestorUtilizadorService.actualizarPassword(utilizadorActual.getId(), password);
            gestorUtilizadorService.actualizarDadosCliente(clienteActual);
            destino = "utilizador.actualizado";

        }
        return destino;
    }

    public String doAnularAlteracaoUtilizador() {

        String destino = null;
        if (novoUtilizador) {
    // anular os dados dados do cliente nao guardado
            utilizadorActual = null;
            clienteActual = null;

        } else {
// recupera os dados originais do cliente
            clienteActual = gestorUtilizadorService.recuperarDadosCleinte(utilizadorActual.getLogin());
            utilizadorActual = clienteActual.getUtilizador();

        }
        destino = "utilizador.anulado";
        return destino;
    }
    
    
    
    public String doVerPerfil(){
    
    login = utilizadorActual.getLogin();
    password=utilizadorActual.getPassword();
    password2=password;
    return "utilizador.perfil";
    
    }

    public Utilizador getUtilizadorActual() {
        return utilizadorActual;
    }

    public void setUtilizadorActual(Utilizador utilizadorActual) {
        this.utilizadorActual = utilizadorActual;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public boolean isNovoUtilizador() {
        return novoUtilizador;
    }

    public void setNovoUtilizador(boolean novoUtilizador) {
        this.novoUtilizador = novoUtilizador;
    }
    
    
    
    
    
    
}


























































































































































































