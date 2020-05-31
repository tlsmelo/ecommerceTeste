package paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PaginaVitrine {

    public WebDriver driver;

    public PaginaVitrine(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Vitrine
    @FindBy(how = How.XPATH, using = "//div[@class='bf-shelf bf-shelf--aside']")
    public WebElement vitrine;

    //botão Comprar
    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Comprar')]")
    public WebElement botaoComprar;

}
