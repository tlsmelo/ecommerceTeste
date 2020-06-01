package paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PaginaCarrinho {

    public WebDriver driver;

    public PaginaCarrinho(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Loading Spinner
    @FindBy(how = How.XPATH, using = "//div[@class='loading loading-bg']")
    public WebElement loadingSpinner;

    //botão Fechar Pedido
    @FindBy(how = How.XPATH, using = "//a[@id='cart-to-orderform']")
    public WebElement botaoFecharPedido;

    //Preço atual produto (unidade)
    @FindBy(how = How.XPATH, using = "//span[@class='new-product-price']")
    public WebElement precoProduto;

    //Preço total produto (unidades calculadas)
    @FindBy(how = How.XPATH, using = "//span[@class='total-selling-price']")
    public WebElement totalProduto;

    //Subtotal da compra
    @FindBy(how = How.XPATH, using = "//table[@class='table']/tbody[1]/tr/td[3][@data-bind='text: valueLabel']")
    public WebElement subTotalCompra;

    //Total da compra
    @FindBy(how = How.XPATH, using = "//table[@class='table']/tfoot[1]/tr/td[3]")
    public WebElement totalCompra;

    //Link Escolher mais produtos
    @FindBy(how = How.XPATH, using = "//a[@class='more link-choose-more-products']")
    public WebElement linkEscolherMaisProdutos;

    //Botão Aumentar Quantidade item
    @FindBy(how = How.XPATH, using = "//table[@class='table cart-items']/tbody/tr/td[5]/a[2]")
    public WebElement botaoAumentarQuantidade;

    //Botão Diminuir Quantidade item
    @FindBy(how = How.XPATH, using = "//table[@class='table cart-items']/tbody/tr/td[5]/a[1]")
    public WebElement botaoDiminuirQuantidade;

    //Botão Remover item
    @FindBy(how = How.XPATH, using = "//table[@class='table cart-items']/tbody/tr/td[7]/a")
    public WebElement removerItem;

    //Loading Spinner carrinho
    @FindBy(how = How.XPATH, using = "//table[@class='table cart-items']/tbody/tr/td[7]/i")
    public WebElement loadingSpinnerCarrinho;

    //Mensagem de carrinho vazio
    @FindBy(how = How.XPATH, using = "//div[@class='empty-cart-content']/h2")
    public WebElement mensagemCarrinhoVazio;

    //Botão Escolher Produtos
    @FindBy(how = How.XPATH, using = "//a[@class='btn btn-large btn-success link-choose-products']")
    public WebElement botaoEscolherProdutos;

}
