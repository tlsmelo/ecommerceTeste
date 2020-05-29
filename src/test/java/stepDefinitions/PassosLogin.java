package stepDefinitions;

import ForneceDados.ConfigFileReader;
import cucumber.api.java.pt.Dado;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PassosLogin {

    @Dado("^Que eu navego para o site do E-Commerce (.*)$")
    public void euNavegoParaOSite(String url){
        if(url.equals("casasbahia")){
            this.navegarParaOSite(ConfigFileReader.getInstance().getCasasBahiaUrl());
        }
    }

    private void navegarParaOSite(String url){
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }
}
