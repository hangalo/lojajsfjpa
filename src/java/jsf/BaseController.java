/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author desenvolvimento
 */
public class BaseController {

    /**
     * Adiciona uma mesnagem de erro a hierarquide componentes da pagina JSF
     *
     * @param messagem
     */
    protected void adicionarMessagemErrot(String messagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messagem, null));

    }

    /**
     * Recupera o valor associad a um paramentro da Pagina JSF
     * @param parametro
     * @return
     */
    protected String recuperarParametro(String parametro) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        return map.get(parametro);
    }
    
    /**
     * Recupera o id do tipo long associado a um parametro da pagina JSF
     * @param parametro
     * @return 
     */
    protected long recuperaIdParametro(String parametro){
    return Long.parseLong(recuperarParametro(parametro));
    
    }
    
    
}
