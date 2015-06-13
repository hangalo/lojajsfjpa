/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepcoes;

/**
 *
 * @author desenvolvimento
 */
public class ExcepcaoEntidade extends Exception{
    
    public static final int ID_NAO_EXISTENTE=1;
    
    private int tipo;
    private long id;

    public ExcepcaoEntidade(int tipo, long id, String message) {
        super(message);
        this.tipo = tipo;
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
}















































