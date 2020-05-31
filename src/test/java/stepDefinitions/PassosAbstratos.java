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

    public static void EsperarElementoDesaparecer(WebDriver driverAtivo, WebElement webElement){
        EsperarElementoDesaparecer(driverAtivo, webElement, 30);
    }

    public static void EsperarElementoDesaparecer (WebDriver driverAtivo, WebElement webElement, Integer timeoutSegundos){
        explicitWaitMs(500);

        WebDriverWait wait = new WebDriverWait(driverAtivo, timeoutSegundos);
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public static void explicitWaitMs (Integer waitMs){
        try{
            Thread.sleep(waitMs);
        } catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
