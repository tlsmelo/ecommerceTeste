package stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import forneceDados.ConfigFileReader;
import static org.junit.Assert.assertTrue;

import modeloDados.Carrinho;
import modeloDados.Inicio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import paginas.PaginaCarrinho;
import paginas.PaginaDescricao;
import paginas.PaginaInicial;
import paginas.PaginaVitrine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PassosTeste extends PassosAbstratos {

    private WebDriver driver;
    private PaginaInicial paginaInicial;
    private PaginaVitrine paginaVitrine;
    private PaginaDescricao paginaDescricao;
    private PaginaCarrinho paginaCarrinho;

    private Carrinho carrinhoInfo;
    private Inicio inicioInfo;

    public PassosTeste(){
        this.carrinhoInfo = Carrinho.getInstance();
        this.inicioInfo = Inicio.getInstance();
    }

    @Before
    public void beforeScenario(){
        driver = new ChromeDriver();
    }

    @After
    public void afterScenario(){
        driver.quit();
    }

    @Dado("^Que eu navego para o site do E-Commerce (.*)$")
    public void euNavegoParaOSite(String url){
        if(url.equals("eletrum")){
            this.navegarParaOSite(ConfigFileReader.getInstance().getEletrumUrl());
        }
    }

    @Quando("^Eu realizo a busca do item (.*)$")
    public void euRealizoABuscadoItemX (String item){
        paginaVitrine = new PaginaVitrine(driver);
        paginaInicial.efetuarPesquisa(item);
        EsperarElementoCarregar(driver, paginaVitrine.vitrine);
    }

    @Então("^Eu posso ver o item (.*) retornado na vitrine$")
    public void euPossoVerOItemXRetornadoNaVitrine (String itemRetornado) throws InterruptedException {
        Thread.sleep(1000);
        EsperarElementoCarregar(driver, paginaVitrine.botaoComprar);
        String itemRetornado1 = itemRetornado.toLowerCase();
        Boolean itemPresente = !driver.findElements(By.xpath("//div[@class='bf-shelf bf-shelf--aside']/ul/li/article/div[3]/a/h2[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + itemRetornado1 + "')]")).isEmpty();

        assertTrue("Erro: Item " + itemRetornado + " não retornado na pesquisa.", itemPresente);
    }

    @Quando("^Eu seleciono um item da vitrine em Comprar$")
    public void euSelecionUmItemDaVitrineEmComprar (){
        paginaDescricao = new PaginaDescricao(driver);
        paginaVitrine.botaoComprar.click();
        EsperarElementoCarregar(driver, paginaDescricao.estrelas);

    }

    @Então("^Eu adiciono o item no carrinho em Comprar agora$")
    public void euAdicionoOItemNoCarrinhoEmComprarAgora (){
        paginaCarrinho = new PaginaCarrinho(driver);
        paginaDescricao.botaoComprarAgora.click();
        EsperarElementoDesaparecer(driver, paginaCarrinho.loadingSpinner);
        EsperarElementoCarregar(driver, paginaCarrinho.botaoFecharPedido);

    }

    @Então("^Eu estou no carrinho com os valores atualizados$")
    public void euEstouNoCarrinhoComOsValoresAtualizados (){
        salvarValorProduto();
        salvarValorTotalProduto();
        salvarValorTotalCompra();
        System.out.println("Valor do produto por unidade é: " + carrinhoInfo.getPrecoProduto());
        System.out.println("Valor do produto por unidade é: " + carrinhoInfo.getPrecoTotalProduto());
        System.out.println("Valor total da compra é: " + carrinhoInfo.getPrecoTotalCompra());
        System.out.println("Quantidade de produtos no carrinho é: " + carrinhoInfo.getQuantidadePorCalculo());
    }

    @Quando("^Eu escolho comprar mais produtos$")
    public void euEscolhoComprarMaisProdutos (){
        paginaCarrinho.linkEscolherMaisProdutos.click();
        EsperarElementoCarregar(driver, paginaInicial.welcome);
    }

    @Então("^Eu posso ver o número correto de itens no carrinho na página inicial$")
    public void euPossoVerONumeroCorretoDeItensNoCarrinhoNaPaginaInicial () throws InterruptedException {
        EsperarElementoCarregar(driver, paginaInicial.qtdItensCarrinho);
        Thread.sleep(1000);
        String quantidadeCarrinho = carrinhoInfo.getQuantidade();
        String quantidadePaginaInicial = recuperarQuantidadeIconeCarrinho();
        assertTrue("Quantidade está incorreta, no carrinho tem " + quantidadeCarrinho + " itens e na página inicial posso ver " + quantidadePaginaInicial, quantidadeCarrinho.equalsIgnoreCase(quantidadePaginaInicial));
        System.out.println("\nQuantidade de produtos no ícone do carrinho está correta: " + quantidadePaginaInicial);
    }

    @Quando("^Eu clico no ícone do carrinho na página inicial$")
    public void euClicoNoIconeDoCarrinhoNaPaginaInicial (){
        paginaInicial.linkIconeCarrinho.click();
        EsperarElementoCarregar(driver, paginaInicial.botaoFinalizarCompra);
    }

    @Então("^Eu posso ver uma nova página contendo o item (.*), valor total e opções de compra$")
    public void euPossoVerUmaNovaPaginaContendoItemValorTotalOpcoesCompra (String item){
        salvarTotal();
        String produto = paginaInicial.descricaoProduto.getText();
        String produtoLower = paginaInicial.descricaoProduto.getText().toLowerCase();
        String itemLower = item.toLowerCase();
        String total = inicioInfo.getTotal();

        assertTrue("Item está incorreto. Foi exibido "+ produto + " mas deveria conter " + item.toLowerCase(), produtoLower.contains(itemLower));
        assertTrue("Valor total está incorreto. Foi exibido " + total + " mas deveria ser " + carrinhoInfo.getPrecoTotalProduto(), carrinhoInfo.getPrecoTotalProduto().equalsIgnoreCase(total));
        assertThat("Botão de Finalizar Compra não encontrado.", paginaInicial.botaoFinalizarCompra.isDisplayed(), is(equalTo(true)));
        assertThat("Link de Continuar comprando não encontrado.", paginaInicial.botaoContinuarComprando.isDisplayed(), is(equalTo(true)));
        System.out.println("\nValor total na mini página é: " + total);
        System.out.println("Item exibido na mini página é: " + produto);
    }

    @Quando("^Eu clico em Finalizar Compra na mini página do carrinho$")
    public void euClicoEmFinalizarCompraNaMiniPaginaDoCarrinho (){
        paginaInicial.botaoFinalizarCompra.click();
        EsperarElementoCarregar(driver, paginaCarrinho.botaoFecharPedido);
    }

    @Quando("^Eu aumento a quantidade do item no carrinho$")
    public void euAumentoAQuantidadeDoItemNoCarrinho (){
        paginaCarrinho.botaoAumentarQuantidade.click();
        EsperarElementoDesaparecer(driver, paginaCarrinho.loadingSpinnerCarrinho);
        aumentarQtd();
        salvarValorTotalProduto();
        salvarValorTotalCompra();
    }

    @Quando("^Eu diminuo a quantidade do item no carrinho$")
    public void euDiminuoAQuantidadeDoItemNoCarrinho (){
        paginaCarrinho.botaoDiminuirQuantidade.click();
        EsperarElementoDesaparecer(driver, paginaCarrinho.loadingSpinnerCarrinho);
        diminuirQtd();
        salvarValorTotalProduto();
        salvarValorTotalCompra();
    }

    @Então("^Eu vejo o valor da compra atualizado$")
    public void euVejoOValorDaCompraAtualizado (){
        String quantidadeCarrinhoNovo = carrinhoInfo.getQuantidade();
        String valorTotalTela = carrinhoInfo.getPrecoTotalProduto();
        String valorTotalProdutoCalculado = recuperarValorTotalCalculadoAposClique();
        String valorTotalCompraNovo = carrinhoInfo.getPrecoTotalCompra();

        assertTrue("Valor está incorretamente calculado, encontrado " + valorTotalTela + " mas deveria ser " + valorTotalProdutoCalculado, valorTotalProdutoCalculado.equalsIgnoreCase(valorTotalTela));
        assertTrue("Valor total da compra incorretamente calculado, encontrado" + valorTotalCompraNovo + " mas deveria ser " + valorTotalProdutoCalculado, valorTotalCompraNovo.equalsIgnoreCase(valorTotalProdutoCalculado));
        System.out.println("\nNovo valor do produto calculado : " + valorTotalProdutoCalculado);
        System.out.println("Novo valor total da compra atualizado : " + valorTotalCompraNovo);
        System.out.println("Quantidade de produtos no carrinho atualizado para: " + carrinhoInfo.getQuantidade());
    }

    @Quando("^Eu removo o item do carrinho$")
    public void euRemovoOItemDoCarrinho (){
        paginaCarrinho.removerItem.click();
        EsperarElementoDesaparecer(driver, paginaCarrinho.loadingSpinnerCarrinho);
        zerarQtd();
    }

    @Então("^Eu vejo a mensagem Seu carrinho está vazio$")
    public void euVejoAMensagemSeuCarrinhoEstaVazio (){
        String mensagem = paginaCarrinho.mensagemCarrinhoVazio.getText();
        String mensagemEsperada = "Seu carrinho está vazio.";

        assertTrue("Mensagem de carrinho vazio não encontrada ou incorreta. Foi exibido "+ mensagem + " mas deveria exibir " + mensagemEsperada, mensagemEsperada.equalsIgnoreCase(mensagem));
        System.out.println("\nItem removido com sucesso.");
    }

    @Quando("^Eu clico em Escolher Produtos no carrinho$")
    public void euClicoEmEscolherProdutosNoCarrinho (){
        paginaCarrinho.botaoEscolherProdutos.click();
        EsperarElementoCarregar(driver, paginaInicial.welcome);
    }

    @Então ("^Eu fecho o site do E-Commerce$")
    public void euFechoOSiteDoECommerce() {
        driver.quit();
    }

    private void navegarParaOSite(String url){
        paginaInicial = new PaginaInicial(driver);
        driver.get(url);
        driver.manage().window().maximize();
        EsperarElementoCarregar(driver, paginaInicial.welcome);

        //verificar se estou na página inicial do site
        assertThat("Pagina inicial nao encontrada", paginaInicial.welcome.isDisplayed(), is(equalTo(true)));
    }

    //salvar valor do produto (unidade)
    public void salvarValorProduto (){
        carrinhoInfo.setPrecoProduto(paginaCarrinho.precoProduto.getText());
    }

    //salvar valor do total produto (calculado)
    public void salvarValorTotalProduto (){
        carrinhoInfo.setPrecoTotalProduto(paginaCarrinho.totalProduto.getText());
    }

    //salvar valor do total da compra
    public void salvarValorTotalCompra (){
        carrinhoInfo.setPrecoTotalCompra(paginaCarrinho.totalCompra.getText());
    }

    //recuperar quantidade de itens no ícone Carrinho sem zero a esquerda
    public String recuperarQuantidadeIconeCarrinho() {
        inicioInfo.setIconeCarrinhoQuantidade(paginaInicial.qtdItensCarrinho.getText());
        String carrinhoQuantidade = inicioInfo.getIconeCarrinhoQuantidade();

        return carrinhoQuantidade;
    }

    //salvar total compra da mini página do Carrinho
    public void salvarTotal (){
        inicioInfo.setTotal(paginaInicial.valorTotal.getText());
    }

    //aumentar quantidade no modelo de dados pelo clique no botão aumentar qtd
    public void aumentarQtd (){
        carrinhoInfo.aumentarQuantidadePorClique();
    }

    //diminuir quantidade no modelo de dados pelo clique no botão diminuir qtd
    public void diminuirQtd (){
        carrinhoInfo.diminuirQuantidadePorClique();
    }

    //Zerar quantidade no caso de Seu Carrinho Está Vazio
    public void zerarQtd (){
        carrinhoInfo.zerarQuantidade();
    }

    public String recuperarValorTotalCalculadoAposClique(){
        String precoTotalCalculado = carrinhoInfo.getPrecoTotalProdutoCalculoAposAumentoOuDiminuicao();

        return precoTotalCalculado;
    }

}
