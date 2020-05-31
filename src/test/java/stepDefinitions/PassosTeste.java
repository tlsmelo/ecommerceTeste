package stepDefinitions;

import cucumber.api.java.pt.Então;
import forneceDados.ConfigFileReader;
import paginas.PaginaInicial;
import paginas.PaginaVitrine;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Quando;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PassosTeste extends PassosAbstratos {

    private PaginaInicial paginaInicial;
    private PaginaVitrine paginaVitrine;
    private WebDriver driver;

    @Dado("^Que eu navego para o site do E-Commerce (.*)$")
    public void euNavegoParaOSite(String url){
        if(url.equals("eletrum")){
            this.navegarParaOSite(ConfigFileReader.getInstance().getEletrumUrl());
        }
    }

    private void navegarParaOSite(String url){
        driver = new ChromeDriver();
        paginaInicial = new PaginaInicial(driver);
        driver.get(url);
        driver.manage().window().maximize();
        EsperarElementoCarregar(driver, paginaInicial.welcome);

        //verificar se estou na página inicial do site
        assertThat("Link de acesso a pagina de login nao encontrado", paginaInicial.welcome.isDisplayed(), is(equalTo(true)));
    }

    @Quando("^Eu realizo a busca do item (.*)$")
    public void euRealizoABuscadoItemX (String item){
        paginaInicial.efetuarPesquisa(item);
        paginaVitrine = new PaginaVitrine(driver);
        EsperarElementoCarregar(driver, paginaVitrine.vitrine);
    }


    @Então ("^Eu fecho o site do E-Commerce$")
    public void euFechoOSiteDoECommerce() {
        driver.quit();
    }
}
