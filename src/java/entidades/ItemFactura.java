/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author desenvolvimento
 */
@Entity
public class ItemFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Factura factura;
    @ManyToOne
    private Produto produto;
    private Long quantidade;
    private Double preco;
    private Double desconto;

    @Version
    private Integer version;

    @Transient
    private Double totalItem;

    public ItemFactura() {
        this.totalItem = 0.0;
    }

    public ItemFactura(Long id, Factura factura, Produto produto, Long quantidade, Double preco, Double desconto) {
        this.id = id;
        this.factura = factura;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.desconto = desconto;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    
    
    private void calcularTotalItem() {

        if ((this.quantidade != null) && (this.preco != null) && (this.desconto != null)) {
            totalItem = (quantidade * preco) * ((100 - desconto) / 100);

        } else {

            this.totalItem = 0.0;
        }

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
        if (!(object instanceof ItemFactura)) {
            return false;
        }
        ItemFactura other = (ItemFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ItemFactura[ id=" + id + " ]";
    }

}
