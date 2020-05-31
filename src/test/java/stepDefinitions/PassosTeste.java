package stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import forneceDados.ConfigFileReader;
import static org.junit.Assert.assertTrue;

import modeloDados.Carrinho;
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

    public PassosTeste(){
        this.carrinhoInfo = Carrinho.getInstance();
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

    private void navegarParaOSite(String url){
        paginaInicial = new PaginaInicial(driver);
        driver.get(url);
        driver.manage().window().maximize();
        EsperarElementoCarregar(driver, paginaInicial.welcome);

        //verificar se estou na página inicial do site
        assertThat("Pagina inicial nao encontrada", paginaInicial.welcome.isDisplayed(), is(equalTo(true)));
    }

    @Quando("^Eu realizo a busca do item (.*)$")
    public void euRealizoABuscadoItemX (String item){
        paginaVitrine = new PaginaVitrine(driver);
        paginaInicial.efetuarPesquisa(item);
        EsperarElementoCarregar(driver, paginaVitrine.vitrine);
    }

    @Então("^Eu posso ver o item (.*) retornado na vitrine$")
    public void euPossoVerOItemXRetornadoNaVitrine (String itemRetornado) throws InterruptedException {
        EsperarElementoCarregar(driver, paginaVitrine.botaoComprar);
        Thread.sleep(1000);
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
        System.out.println("Valor do produto por unidade é: " + carrinhoInfo.getPrecoProduto());
        System.out.println("Valor do produto por unidade é: " + carrinhoInfo.getPrecoTotalProduto());
        System.out.println("Quantidade de produtos no carrinho é: " + carrinhoInfo.getQuantidadePorCalculo());
    }

    //salvar valor do produto (unidade)
    public void salvarValorProduto (){
        carrinhoInfo.setPrecoProduto(paginaCarrinho.precoProduto.getText());
    }

    //salvar valor do total produto (calculado)
    public void salvarValorTotalProduto (){
        carrinhoInfo.setPrecoTotalProduto(paginaCarrinho.totalProduto.getText());
    }


    @Então ("^Eu fecho o site do E-Commerce$")
    public void euFechoOSiteDoECommerce() {
        driver.quit();
    }

}
