/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.negocio;

import ejb.dao.ClienteDAO;
import ejb.dao.FacturaDAO;
import ejb.dao.ProdutoDAO;
import jpa.entidades.Cliente;
import jpa.entidades.EstadoPedido;
import jpa.entidades.Factura;
import jpa.entidades.ItemFactura;
import jpa.entidades.Produto;
import excepcoes.ExcepcaoExistencias;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author desenvolvimento
 */
@Stateful
public class CompraService {

    @EJB
    ProdutoDAO produtoDAO;
    @EJB
    ClienteDAO clienteDAO;
    @EJB
    FacturaDAO facturaDAO;

    private List<ProdutoCompra> carrinhoCompra = null;
    private Cliente cliente = null;
    private boolean confirmado = false;

    public void inicializarCarrinhoCompra() {
        this.carrinhoCompra = new ArrayList<>();
        this.cliente = null;
        this.confirmado = false;

    }

    public void adicionarProduto(long idProduto, long quantidade) {

        if (carrinhoCompra == null) {
            inicializarCarrinhoCompra();
        }

        if (!confirmado) {
            Produto p = produtoDAO.buscarPorId(idProduto);
            if (p != null) {
                ProdutoCompra itemEncontrado = buscarItem(p);
                System.out.println(">>>>>>>>> EJB " + p.getDescricao());

                if (itemEncontrado != null) {

                    itemEncontrado.incrementarQuantidade(quantidade);
                } else {
                    carrinhoCompra.add(new ProdutoCompra(p, quantidade));
                }
            }
        }

    }

    public void incrementarQuantidade(long idProduto, long incremento) {

        if (!confirmado) {
            ProdutoCompra itemEncontrado = buscarItem(idProduto);
            if (itemEncontrado != null) {
                itemEncontrado.incrementarQuantidade(incremento);
            }

        }

    }

    public void decrementarQuantidade(long idProduto, long reducao) {

        if (!confirmado) {
            ProdutoCompra itemEncontrado = buscarItem(idProduto);
            if (itemEncontrado != null) {
                itemEncontrado.decrementarQuantidade(reducao);

            }

        }

    }

    public void desvaziaCarrinho() {
        if (carrinhoCompra == null) {
            inicializarCarrinhoCompra();
        } else {

            carrinhoCompra.clear();
            confirmado = false;
        }

    }

    public void confirmarCarrinho() throws ExcepcaoExistencias {
        if (confirmarExistenciasCarrinho()) {
            this.confirmado = true;
        }
    }

    public void eliminarProduto(long idProduto) {

        if (!confirmado) {
            if (carrinhoCompra != null) {
                int idx = 0;
                for (ProdutoCompra item : carrinhoCompra) {
                    if (item.getProduto().getId() == idProduto) {
                        carrinhoCompra.remove(idx);
                        break; //sair do loop
                    }
                    idx++;
                }
            }

        }

    }

    public void actualizarQuantidade(long idProduto, long novaQuantidade) {
        if (!confirmado) {
            ProdutoCompra itemEncontrado = buscarItem(idProduto);

            if (itemEncontrado != null) {
                itemEncontrado.setQuantidade(novaQuantidade);
            }

        }

    }

    private boolean confirmarExistenciasCarrinho() throws ExcepcaoExistencias {
        boolean existenciasConfirmadas = true;
        Produto p;
        if (carrinhoCompra != null) {
            for (ProdutoCompra item : carrinhoCompra) {

                if (!produtoDisponivel(item.getProduto().getId(), item.getQuantidade())) {
                    existenciasConfirmadas = false;
                    throw new ExcepcaoExistencias(item.getProduto().getId(), item.getQuantidade(), item.getProduto().getDescricao());
                }
            }

        } else {
            existenciasConfirmadas = false;
        }
        return existenciasConfirmadas;
    }

    public boolean produtoDisponivel(long idProduto, long quantidade) {
        Produto p;
        p = produtoDAO.buscarPorId(idProduto);

        return (p.getExistencias() >= quantidade);

    }

    public void definirCliente(long idCliente) {
        Cliente c = clienteDAO.buscarPorId(idCliente);
        if (c != null) {

            this.cliente = c;
        }

    }

    public Factura gerarFactura() {

        Factura facturaGerada = null;
        List<ItemFactura> itemsFacturaGerada = new ArrayList<>();

        if (confirmado && cliente != null) {
            facturaGerada = new Factura(cliente, Calendar.getInstance().getTime(), EstadoPedido.PENDENTE);
            for (ProdutoCompra item : carrinhoCompra) {
                Produto produto = produtoDAO.buscarPorId(item.getProduto().getId());
                System.out.println(">> Factura: " + facturaGerada.toString());
                long quantidadeCompra = Math.min(item.getQuantidade(), produto.getExistencias());
                ItemFactura itemFactura = new ItemFactura(facturaGerada, produto, quantidadeCompra, produto.getPreco(), calcularDesconto(cliente, produto, quantidadeCompra));
                System.err.println(">> ItemFactura: " + itemFactura.toString());
                itemsFacturaGerada.add(itemFactura);
            }
            facturaGerada.setItemFacturas(itemsFacturaGerada);
            facturaGerada = facturaDAO.criar(facturaGerada); // Cria factura e insere os itens
            //Inicializa o carrinho de compra para criar outro pedido
            desvaziaCarrinho();
        }
        return facturaGerada;
    }

    public List<ProdutoCompra> obterCarrinhoCompras() {
        return carrinhoCompra;
    }

    public double calcularMontanteTotal() {

        double total = 0.0;

        if (carrinhoCompra != null) {

            for (ProdutoCompra p : carrinhoCompra) {
                double parcial = (p.getQuantidade() * p.getProduto().getPreco());
                if (cliente != null) {
                    parcial = parcial * (1 - calcularDesconto(cliente, p.getProduto(), p.getQuantidade()) / 100);
                }
                total = total +parcial;
            }

        }
        return  total;
    }

    private ProdutoCompra buscarItem(long idProduto) {
        ProdutoCompra itemEncontrado = null;
        if (carrinhoCompra != null) {

            for (ProdutoCompra item : carrinhoCompra) {

                if (item.getProduto().getId() == idProduto) {
                    itemEncontrado = item;
                    break; // sai do loop
                }
            }
        }
        return itemEncontrado;
    }

    private ProdutoCompra buscarItem(Produto p) {
        return buscarItem(p.getId());
    }

    private double calcularDesconto(Cliente cliente, Produto produto, long quantidadeCompra) {
        // nao se fazem descontos (... por agora)
        return 0.0;
    }
}
