package paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PaginaDescricao {

    public WebDriver driver;

    public PaginaDescricao(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //estrelas avaliação do produto selecionado (o último a ser carregado na página)
    @FindBy(how = How.XPATH, using = "//div[@class='bf-product__container']/div/div/div/div/div[@class='yv-review-quickreview']")
    public WebElement estrelas;

    //botão Comprar Agora
    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Comprar agora')]")
    public WebElement botaoComprarAgora;

}
