package paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PaginaInicial {

    public WebDriver driver;

    public PaginaInicial(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Welcome
    @FindBy(how = How.XPATH, using = "//p[@class='welcome']")
    public WebElement welcome;

    //Campo de pesquisa
    @FindBy(how = How.XPATH, using = "//input[@class='bf-search__input']")
    public WebElement campoPesquisa;

    //Botão pesquisa
    @FindBy(how = How.XPATH, using = "//button[@class='bf-search__submit']")
    public WebElement botaoPesquisa;

    public void efetuarPesquisa (String valorItem){
        //Preencher campo de pesquisa
        campoPesquisa.clear();
        campoPesquisa.sendKeys(valorItem);

        //clicar no botão pesquisar
        botaoPesquisa.click();
    }

    //Quantidade itens carrinho
    @FindBy(how = How.XPATH, using = "//div[@class='bf-badge bf-badge--amount-in-cart']/span")
    public WebElement qtdItensCarrinho;

    //Link ícone do carrinho
    @FindBy(how = How.XPATH, using = "//div[@class='bf-header-page__mini-cart bf-js-header-page__mini-cart bf-minicart__toggle d-none d-sm-block']")
    public WebElement linkIconeCarrinho;

    //Mini página carrinho - botão Finalizar Compra
    @FindBy(how = How.XPATH, using = "//div[@class='bf-minicart__btns']/a[@class='bf-minicart__btn bf-minicart__btn--checkout']")
    public WebElement botaoFinalizarCompra;

    //Mini página carrinho - link Continuar comprando
    @FindBy(how = How.XPATH, using = "//div[@class='bf-minicart__btns']/button[@class='bf-minicart__btn']")
    public WebElement botaoContinuarComprando;

    //Mini página carrinho - descrição do produto
    @FindBy(how = How.XPATH, using = "//h1[@class='bf-cart__title']/a")
    public WebElement descricaoProduto;

    //Mini página carrinho - valor total
    @FindBy(how = How.XPATH, using = "//p[@class='bf-minicart__total']/span[2]")
    public WebElement valorTotal;
}
