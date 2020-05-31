package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PassosAbstratos {

    public static void EsperarElementoCarregar(WebDriver driverAtivo, WebElement webElement){
        WebDriverWait wait = new WebDriverWait(driverAtivo, 30);
        wait.until((ExpectedConditions.visibilityOf(webElement)));
    }
}
