/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.negocio.CatalogoService;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.entidades.FamiliaProduto;
import jpa.entidades.Produto;

/**
 *
 * @author desenvolvimento
 */
@ManagedBean(name = "catalogoController")
@SessionScoped
public class CatalogoController extends BaseController {

    //atributos
    private FamiliaProduto familiaProdutoActual = null;
    private Produto produtoActual = null;
    private List<FamiliaProduto> familiaProdutos = null;
    private List<Produto> produtos = null;
    private String consultaDescricao;
    private String consultaMarca;

    @EJB
    CatalogoService catalogoService;

    public CatalogoController() {
    }

    //Accoes para as paginas JSF
    public String doBuscaDescricao() {
        String destino = null;
        familiaProdutoActual = null;

        if (consultaDescricao != null) {

            produtos = catalogoService.produtosPorDescricao(consultaDescricao);

            if (produtos != null) {
                if (produtos.isEmpty()) {
                    destino = "busca.vazia";
                } else {

                    destino = "busca.resultados";
                }

            }

        }

        return destino;

    }

    public String doBucaMarca() {
        String destino = null;
        familiaProdutoActual = null;

        if (consultaDescricao != null) {
            produtos = catalogoService.produtosPorMarca(consultaMarca);
            if (produtos.isEmpty()) {
                destino = "busca.vazia";
            } else {

                destino = "busca.resultados";
            }

        }

        return destino;
    }

    public String doSeleccionarFamilia() {
        String destino = null;
        long idFamilia = recuperaIdParametro("idFamilia");
        familiaProdutoActual = catalogoService.familiaPorID(idFamilia);
        produtos = catalogoService.produtosPorFamilia(familiaProdutoActual.getId());

        if (produtos != null) {
            if (produtos.isEmpty()) {
                destino = "busca.vazia";
            } else {
                destino = "busca.resultados";

            }
        }
        return destino;
    }

    public String doVerTodos() {
        String destino = null;
        familiaProdutoActual = null;
        produtos = catalogoService.produtos();

        if (produtos != null) {
            if (produtos.isEmpty()) {
                destino = "busca.vazio";
            } else {

                destino = "busca.resultados";
            }

        }

        return destino;
    }

    public String doVerProdutoActual(long idProduto) {
        String destino = null;
        produtoActual = catalogoService.produtoPorID(idProduto);

        System.out.println(">>>>>> buscar produtor com _ID" + idProduto + "-->" + produtoActual + " Existencias" + produtoActual.getExistencias());
        if (produtoActual != null) {
            destino = "detalhe.produto";
        }

        return destino;
    }

    public FamiliaProduto getFamiliaProdutoActual() {
        return familiaProdutoActual;
    }

    public void setFamiliaProdutoActual(FamiliaProduto familiaProdutoActual) {
        this.familiaProdutoActual = familiaProdutoActual;
    }

    public Produto getProdutoActual() {
        return produtoActual;
    }

    public void setProdutoActual(Produto produtoActual) {
        this.produtoActual = produtoActual;
    }

    public List<FamiliaProduto> getFamiliaProdutos() {
        if (familiaProdutos == null) {

            familiaProdutos = catalogoService.familias();
        }
        return familiaProdutos;
    }

    public void setFamiliaProdutos(List<FamiliaProduto> familiaProdutos) {
        this.familiaProdutos = familiaProdutos;
    }

    public List<Produto> getProdutos() {
        if(produtos == null){
        produtos = catalogoService.produtos();
        }
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getConsultaDescricao() {
        return consultaDescricao;
    }

    public void setConsultaDescricao(String consultaDescricao) {
        this.consultaDescricao = consultaDescricao;
    }

    public String getConsultaMarca() {
        return consultaMarca;
    }

    public void setConsultaMarca(String consultaMarca) {
        this.consultaMarca = consultaMarca;
    }

}
