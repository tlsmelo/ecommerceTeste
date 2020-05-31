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

}
