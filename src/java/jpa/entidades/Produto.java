/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 *
 * @author desenvolvimento
 */
@Entity
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    private String detalhe;
    private Double preco;
    private Integer existencias;
    private String marca;
    private String modelo;
    
    @ManyToOne
    private FamiliaProduto familiaProduto;
    
    @Version
    private Integer versao;

    public Produto() {
    }

    public Produto(Long id, String descricao, String detalhe, Double preco, Integer existencias, String marca, String modelo, FamiliaProduto familiaProduto) {
        this.id = id;
        this.descricao = descricao;
        this.detalhe = detalhe;
        this.preco = preco;
        this.existencias = existencias;
        this.marca = marca;
        this.modelo = modelo;
        this.familiaProduto = familiaProduto;
       
    }
    
    
         

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public FamiliaProduto getFamiliaProduto() {
        return familiaProduto;
    }

    public void setFamiliaProduto(FamiliaProduto familiaProduto) {
        this.familiaProduto = familiaProduto;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Produto[ id=" + id + " ]";
    }
    
}


















































