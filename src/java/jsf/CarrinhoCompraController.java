/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.negocio.CompraService;
import ejb.negocio.ProdutoCompra;
import excepcoes.ExcepcaoExistencias;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import jpa.entidades.Factura;
import jpa.entidades.Produto;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author desenvolvimento
 */
@ManagedBean(name = "carrinhoCompraController")
@SessionScoped
public class CarrinhoCompraController extends BaseController {

    ProdutoCompra produtoCompraActual = null;
    Factura factura = null;
    double montanteTotal;
    Produto produtoSeleccionado = null;
    @ManagedProperty(value = "#{utilizadorController}")
    UtilizadorController utilizadorController;

    @EJB
    CompraService compraService;

    public CarrinhoCompraController() {
    }

    public UtilizadorController getUtilizadorController() {
        return utilizadorController;
    }

    public void setUtilizadorController(UtilizadorController utilizadorController) {
        this.utilizadorController = utilizadorController;
    }

    //accoes
    public String doAdicionarProtudo(long idProduto) {

        String destino = null;
        compraService.adicionarProduto(idProduto, 1);
        montanteTotal = compraService.calcularMontanteTotal();
        return destino; // fica na mesma pagina (lista de produtos)   
    }

    public String doIncrementarQuantidade(long idProduto) {
        String destino = null;
        compraService.incrementarQuantidade(idProduto, 1);
        montanteTotal = compraService.calcularMontanteTotal();
        return destino; // fica na mesma pagina   
    }

    public String doEliminarProduto(long idProduto) {
        String destino = null;
        compraService.eliminarProduto(idProduto);
        montanteTotal = compraService.calcularMontanteTotal();
        return destino;
    }

    public String doActualizarCarrinho() {
        String destino = null;
        montanteTotal = compraService.calcularMontanteTotal();
        return destino;

    }

    public String doConfirmarCarrinhoCompra() {

        String destino = null;
        if (utilizadorController.getClienteActual() == null) {

            adicionarMessagemErrot("Nenhum cliente resistado esta ligado a compra");
            destino = null;
        } else {
            try {
                compraService.definirCliente(utilizadorController.getClienteActual().getId());
                compraService.confirmarCarrinho();
                factura = compraService.gerarFactura();
                destino = "compra.completa";

            } catch (ExcepcaoExistencias e) {
                adicionarMessagemErrot("Existencias insuficientes do produto " + e.getDescricaoProduto());
                destino = null;
            }

        }
        return destino;
    }

    public String doEsvaziarCarrinhoCompra() {
        String destino = null;
        esvaziarCarrinho();
        return "carrinhoCompra.xhtml";// força a reconstrucao da pagina

    }

    //Metodos get e set
    public double getMontanteTotal() {
        return montanteTotal;
    }

    public void setMontanteTotal(double montanteTotal) {
        this.montanteTotal = montanteTotal;
    }

    public ProdutoCompra getProdutoCompraActual() {
        return produtoCompraActual;
    }

    public void setProdutoCompraActual(ProdutoCompra produtoCompraActual) {
        this.produtoCompraActual = produtoCompraActual;

    }

    public List<ProdutoCompra> getProdutos() {
        return compraService.obterCarrinhoCompras();

    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Produto getProdutoSeleccionado() {

        System.out.println("<<<<<<<< Recupera " + produtoSeleccionado);
        return produtoSeleccionado;
    }

    public void setProdutoSeleccionado(Produto produtoSeleccionado) {
        System.out.println(">>>>>>> actualiza " + produtoSeleccionado);
        this.produtoSeleccionado = produtoSeleccionado;
    }

// Manipuladores de eventos (por exemplo Ajax com primefaces)
    public void onProdutoDrop(DragDropEvent ddEvent) {
        Produto p = ((Produto) ddEvent.getData());
        compraService.adicionarProduto(p.getId(), 1);
        montanteTotal = compraService.calcularMontanteTotal();
    }

    public void onProdutoIncrementoDecremento(Produto produto, int quantidade) {
        compraService.actualizarQuantidade(produto.getId(), quantidade);
        montanteTotal = compraService.calcularMontanteTotal();

    }

    public void onProdutoEliminar(Produto produto) {
        System.out.println("::::::::::::::::::::::::::" + produto);
        compraService.eliminarProduto(produto.getId());
        montanteTotal = compraService.calcularMontanteTotal();

    }

    public void esvaziarCarrinho() {
        compraService.desvaziaCarrinho();
        montanteTotal = 0.0;

    }
}
