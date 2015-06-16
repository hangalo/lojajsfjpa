/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author desenvolvimento
 */
@Entity
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Cliente cliente;
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    private EstadoPedido estadoPedido;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "factura")
    private List<ItemFactura> itemFacturas;

    @Transient
    private Double totalFactura;

    @Version
    private Integer versao;

    public Factura() {
        this.totalFactura = 0.0;
    }

    public Factura(Cliente cliente, Date dataEmissao, EstadoPedido estadoPedido) {
      
        this.cliente = cliente;
        this.dataEmissao = dataEmissao;
        this.estadoPedido = estadoPedido;
        this.itemFacturas = new ArrayList<>();
        this.totalFactura = 0.0;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public List<ItemFactura> getItemFacturas() {
        return itemFacturas;
    }

    public void setItemFacturas(List<ItemFactura> itemFacturas) {
        this.itemFacturas = itemFacturas;
        this.totalFactura = acumularTotalItens();
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    private Double acumularTotalItens() {
        double soma = 0;
        for (ItemFactura item : itemFacturas) {

            soma +=item.getTotalItem();
        }
        return soma;
    }
    
    public void adicionarItemAFactura(ItemFactura itemFactura){
    if(this.itemFacturas == null){
        System.out.println("Itens da factura com valor null");
    }
    if(itemFactura == null){
        System.out.println("Item com valor nulll");
    }
    this.itemFacturas.add(itemFactura);
    this.totalFactura +=itemFactura.getTotalItem();
    
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Factura[ id=" + id + " ]";
    }

}
