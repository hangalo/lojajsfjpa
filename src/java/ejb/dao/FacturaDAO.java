/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.dao;

import jpa.entidades.EstadoPedido;
import jpa.entidades.Factura;
import jpa.entidades.ItemFactura;
import jpa.entidades.Produto;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author praveen
 */
@Stateless
public class FacturaDAO extends DAOJPAGenerico<Factura> {

    @EJB
    ProdutoDAO produtoDAO;

    public List<Factura> buscaPorCliente(long idCliente) {
        Query q = em.createQuery("SELECT object(f) FROM Factura AS f WHERE f.cliente.id =:idCliente");
        q.setParameter("idCliente", idCliente);
        return q.getResultList();

    }

    public List<Factura> buscaPorNomeCliente(String nomeCliente) {

        Query q = em.createQuery("SELECT object(f) FROM Factura AS f WHERE (f.cliente.nome LIKE :parametro) OR(f.cliente.sobrenome LIKE :parametro)");
        q.setParameter("parametro", "%" + nomeCliente + "%");
        return q.getResultList();

    }

    public List<Factura> buscaPorDatas(Date dataInicio, Date dataFim) {
        Query q = em.createQuery("SELECT object(f) FROM Factura AS f WHERE (f.dataEmissao BETWEEN :inicio AND :fim)");
        q.setParameter("inicio", dataInicio);
        q.setParameter("fim", dataFim);
        return q.getResultList();

    }

    public List<Factura> buscaPorEstado(EstadoPedido estadoPedido) {
        Query q = em.createQuery("SELECT object(f) FROM Factura AS f WHERE f.estadoPedido =:estado");
        q.setParameter("estado", estadoPedido);
        return q.getResultList();

    }

    public void anularFactura(long numeroFactura) {

        anularFactura(this.buscarPorId(numeroFactura));

    }

    public void anularFactura(Factura factura) {
        //mudar o estado a factura
        factura.setEstadoPedido(EstadoPedido.ANULADO);
        Factura facturaAnulada = actualizar(factura);

        Produto produto;
        for (ItemFactura itemFactura : facturaAnulada.getItemFacturas()) {
            produto = itemFactura.getProduto();
            produto.setExistencias((int) (produto.getExistencias() + itemFactura.getQuantidade()));
            produtoDAO.actualizar(produto);

        }

    }

    public int contador() {
        Query q = em.createQuery("SELECT COUNT(f) FROM Factura AS f");
        return q.getFirstResult();
    }
    
   public Factura buscaPorIdCompleto(long numeroFactura) {
    Factura factura = em.find(Factura.class, numeroFactura);
    factura.getItemFacturas(); // força o carregamento da lista dos itens da Factura
    return factura;
   
   }

}
