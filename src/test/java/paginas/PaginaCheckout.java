package paginas;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PaginaCheckout {

    public WebDriver driver;

    public PaginaCheckout(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Campo Email
    @FindBy(how = How.XPATH, using = "//input[@id='client-email']")
    public WebElement campoEmail;

    //Campo Primeiro Nome
    @FindBy(how = How.XPATH, using = "//input[@id='client-first-name']")
    public WebElement campoPrimeiroNome;

    //Campo Ultimo Nome
    @FindBy(how = How.XPATH, using = "//input[@id='client-last-name']")
    public WebElement campoUltimoNome;

    //Campo CPF
    @FindBy(how = How.XPATH, using = "//input[@id='client-document']")
    public WebElement campoCPF;

    //Campo Telefone
    @FindBy(how = How.XPATH, using = "//input[@id='client-phone']")
    public WebElement campoTelefone;

    //Botão Ir Para a Entrega
    @FindBy(how = How.XPATH, using = "//button[@id='go-to-shipping']")
    public WebElement botaoIrParaEntrega;

    //Botão Ir Para o Pagamento
    @FindBy(how = How.XPATH, using = "//button[@class='submit btn btn-large btn-success btn-go-to-payment']")
    public WebElement botaoIrPagamento;

    //caixa fechada com dados preenchidos - Dados Pessoais
    @FindBy(how = How.XPATH, using = "//div[@class='step accordion-group client-profile-data filled']")
    public WebElement dadosPessoaisPreenchidos;

    //Email preenchido - Dados Pessoais
    @FindBy(how = How.XPATH, using = "//div[@class='step accordion-group client-profile-data filled']/div[2]/div/div/div/p[1]/span[1]")
    public WebElement emailPreenchido;

    //Nome preenchido - Dados Pessoais
    @FindBy(how = How.XPATH, using = "//div[@class='step accordion-group client-profile-data filled']/div[2]/div/div/div/p[2]/span[2]")
    public WebElement nomePreenchido;

    //Telefone preenchido - Dados Pessoais
    @FindBy(how = How.XPATH, using = "//div[@class='step accordion-group client-profile-data filled']/div[2]/div/div/div/p[2]/span[4]")
    public WebElement telefonePreenchido;

    //Campo CEP (só exibido quando preencher os primeiros dados e não tiver calculado o frete antes)
    @FindBy(how = How.XPATH, using = "//input[@id='ship-postal-code']")
    public WebElement campoCEP;

    //Campo Número (só exibido após preencher campo CEP)
    @FindBy(how = How.XPATH, using = "//input[@id='ship-number']")
    public WebElement campoNumero;

    //Campo Complemento (Opcional)
    @FindBy(how = How.XPATH, using = "//input[@id='ship-more-info']")
    public WebElement campoComplemento;

    //Campo Cidade (já vem com o valor)
    @FindBy(how = How.XPATH, using = "//input[@id=ship-city']")
    public WebElement campoCidade;

    //Campo Estado (já vem com o valor)
    @FindBy(how = How.XPATH, using = "//input[@id=ship-state']")
    public WebElement campoEstado;

    //Campo Destinatário (já vem com o valor)
    @FindBy(how = How.XPATH, using = "//input[@id=ship-name']")
    public WebElement campoDestinatario;

    //Valor Frete selecionado (o primeiro normalmente é o selecionado)
    @FindBy(how = How.XPATH, using = "//div[@class='span btn-group btn-group-vertical']/label/i[@class='icon-ok-circle']/../span/span[3]")
    public WebElement valorFreteSelecionado;

    //Link alterar opções de entrega
    @FindBy(how = How.XPATH, using = "//a[@class='link-change-shipping']")
    public WebElement linkAlterarEntrega;

    //Opção Pagamento - Cartão de Crédito
    @FindBy(how = How.XPATH, using = "//div[@class='required payment-group-list-btn']/a[2]")
    public WebElement opcaoCartaoCredito;

    //Campo Número Cartão de crédito
    @FindBy(how = How.XPATH, using = "//input[@id='creditCardpayment-card-0Number']")
    public WebElement campoNumeroCartaoCredito;

    //Dropdown Quantidade de Parcelas
    @FindBy(how = How.XPATH, using = "//select[@id='creditCardpayment-card-0Brand']")
    public WebElement quantidadeParcelas;

    public void SetQuantidadeParcelas (String valorQtdParcelas){
        Select select = new Select(quantidadeParcelas);
        select.selectByValue(valorQtdParcelas);
    }

    public String GetPrimeiraOpcaoQuantidadeParcelas (){
        Select select = new Select(quantidadeParcelas);
        String primeiraOpcao = select.getFirstSelectedOption().getText();

        return primeiraOpcao;
    }

    //Campo Nome Impresso
    @FindBy(how = How.XPATH, using = "//input[@id='creditCardpayment-card-0Name']")
    public WebElement campoNomeImpresso;

    //Dropdown Mês de validade cartão
    @FindBy(how = How.XPATH, using = "//select[@id='creditCardpayment-card-0Month']")
    public WebElement mesValidade;

    public void SetMesValidade (String valorMes){
        Select select = new Select(mesValidade);
        select.selectByValue(valorMes);
    }

    //Dropdown Ano de validade cartão
    @FindBy(how = How.XPATH, using = "//select[@id='creditCardpayment-card-0Year']")
    public WebElement anoValidade;

    public void SetAnoValidade (String valorAno){
        Select select = new Select(anoValidade);
        select.selectByValue(valorAno);
    }

    //Campo Código de Segurança
    @FindBy(how = How.XPATH, using = "//input[@id='creditCardpayment-card-0Code']")
    public WebElement campoCodigoSeguranca;

    //Campo CPF do Titular
    @FindBy(how = How.XPATH, using = "//input[@id='holder-document-0']")
    public WebElement campoCPFTitular;

    //Valor Produto
    @FindBy(how = How.XPATH, using = "//div[@class='description']/strong[3]")
    public WebElement valorProduto;

    //Subtotal
    @FindBy(how = How.XPATH, using = "//div[@class='summary-template-holder']/div/div/div/div[2]/div/table/tbody[1]/tr[@class='Items']/td[3]")
    public WebElement subtotal;

    //Valor Entrega
    @FindBy(how = How.XPATH, using = "//table[@class='table']/tbody[1]/tr[@class='Shipping']/td[3]")
    public WebElement valorEntrega;

    //Valor Desconto (se for à vista)
    @FindBy(how = How.XPATH, using = "//table[@class='table']/tbody[1]/tr[@class='Discounts']/td[3]")
    public WebElement valorDesconto;

    public boolean ElementoPresenteDesconto (WebDriver driverAtivo){
        try {
            driverAtivo.findElement(By.className("Discounts"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    //Valor Total
    @FindBy(how = How.XPATH, using = "//table[@class='table']/tfoot/tr/td[3]")
    public WebElement valorTotal;

    //Botão FinalizarCompra
    @FindBy(how = How.XPATH, using = "//button[@id='payment-data-submit']")
    public WebElement botaoFinalizarCompra;

    //Botão FinalizarCompra - ícone cadeado
    @FindBy(how = How.XPATH, using = "//button[@id='payment-data-submit']/i[@class='icon-lock']")
    public WebElement finalizarCompraIconeCadeado;

    //Botão FinalizarCompra - loading spinner
    @FindBy(how = How.XPATH, using = "//button[@id='payment-data-submit']/i[@class='icon-spinner icon-spin']")
    public WebElement finalizarCompraLoadingSpinner;

    //Descrição opção boleto (o primeiro a aparecer)
    @FindBy(how = How.XPATH, using = "//p[@class='payment-description']")
    public WebElement descricaoBoleto;

}
