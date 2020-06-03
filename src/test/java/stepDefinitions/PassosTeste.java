package stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import forneceDados.ConfigFileReader;
import static org.junit.Assert.assertTrue;

import modeloDados.Carrinho;
import modeloDados.Checkout;
import modeloDados.Inicio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import paginas.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PassosTeste extends PassosAbstratos {

    private WebDriver driver;
    private PaginaInicial paginaInicial;
    private PaginaVitrine paginaVitrine;
    private PaginaDescricao paginaDescricao;
    private PaginaCarrinho paginaCarrinho;
    private PaginaCheckout paginaCheckout;

    private Carrinho carrinhoInfo;
    private Inicio inicioInfo;
    private Checkout checkoutInfo;

    public PassosTeste(){
        this.carrinhoInfo = Carrinho.getInstance();
        this.inicioInfo = Inicio.getInstance();
        this.checkoutInfo = Checkout.getInstance();
    }

    @Before
    public void beforeScenario(){
        driver = new ChromeDriver();
    }

    @After
    public void afterScenario(){
        driver.quit();
    }

    @Dado("^Que eu navego para o site do E-Commerce (.*)$")
    public void euNavegoParaOSite(String url){
        if(url.equals("eletrum")){
            this.navegarParaOSite(ConfigFileReader.getInstance().getEletrumUrl());
        }
    }

    @Quando("^Eu realizo a busca do item (.*)$")
    public void euRealizoABuscadoItemX (String item){
        paginaVitrine = new PaginaVitrine(driver);
        paginaInicial.efetuarPesquisa(item);
        EsperarElementoCarregar(driver, paginaVitrine.vitrine);
    }

    @Então("^Eu posso ver o item (.*) retornado na vitrine$")
    public void euPossoVerOItemXRetornadoNaVitrine (String itemRetornado) throws InterruptedException {
        Thread.sleep(1500);
        EsperarElementoCarregar(driver, paginaVitrine.botaoComprar);
        String itemRetornado1 = itemRetornado.toLowerCase();
        Boolean itemPresente = !driver.findElements(By.xpath("//div[@class='bf-shelf bf-shelf--aside']/ul/li/article/div[3]/a/h2[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + itemRetornado1 + "')]")).isEmpty();

        assertTrue("Erro: Item " + itemRetornado + " não retornado na pesquisa.", itemPresente);
    }

    @Quando("^Eu seleciono um item da vitrine em Comprar$")
    public void euSelecionUmItemDaVitrineEmComprar (){
        paginaDescricao = new PaginaDescricao(driver);
        paginaVitrine.botaoComprar.click();
        EsperarElementoCarregar(driver, paginaDescricao.estrelas);

    }

    @Então("^Eu adiciono o item no carrinho em Comprar agora$")
    public void euAdicionoOItemNoCarrinhoEmComprarAgora (){
        paginaCarrinho = new PaginaCarrinho(driver);
        paginaDescricao.botaoComprarAgora.click();
        EsperarElementoDesaparecer(driver, paginaCarrinho.loadingSpinner);
        EsperarElementoCarregar(driver, paginaCarrinho.botaoFecharPedido);

    }

    @Então("^Eu estou no carrinho com os valores atualizados$")
    public void euEstouNoCarrinhoComOsValoresAtualizados (){
        salvarValorProduto();
        salvarValorTotalProduto();
        salvarValorTotalCompra();
        System.out.println("Valor do produto por unidade é: " + carrinhoInfo.getPrecoProduto());
        System.out.println("Valor do produto por unidade é: " + carrinhoInfo.getPrecoTotalProduto());
        System.out.println("Valor total da compra é: " + carrinhoInfo.getPrecoTotalCompra());
        System.out.println("Quantidade de produtos no carrinho é: " + carrinhoInfo.getQuantidadePorCalculo());
    }

    @Quando("^Eu escolho comprar mais produtos$")
    public void euEscolhoComprarMaisProdutos (){
        paginaCarrinho.linkEscolherMaisProdutos.click();
        EsperarElementoCarregar(driver, paginaInicial.welcome);
    }

    @Então("^Eu posso ver o número correto de itens no carrinho na página inicial$")
    public void euPossoVerONumeroCorretoDeItensNoCarrinhoNaPaginaInicial () throws InterruptedException {
        EsperarElementoCarregar(driver, paginaInicial.qtdItensCarrinho);
        Thread.sleep(1500);
        String quantidadeCarrinho = carrinhoInfo.getQuantidade();
        String quantidadePaginaInicial = recuperarQuantidadeIconeCarrinho();
        assertTrue("Quantidade está incorreta, no carrinho tem " + quantidadeCarrinho + " itens e na página inicial posso ver " + quantidadePaginaInicial, quantidadeCarrinho.equalsIgnoreCase(quantidadePaginaInicial));
        System.out.println("\nQuantidade de produtos no ícone do carrinho está correta: " + quantidadePaginaInicial);
    }

    @Quando("^Eu clico no ícone do carrinho na página inicial$")
    public void euClicoNoIconeDoCarrinhoNaPaginaInicial (){
        paginaInicial.linkIconeCarrinho.click();
        EsperarElementoCarregar(driver, paginaInicial.botaoFinalizarCompra);
    }

    @Então("^Eu posso ver uma nova página contendo o item (.*), valor total e opções de compra$")
    public void euPossoVerUmaNovaPaginaContendoItemValorTotalOpcoesCompra (String item){
        salvarTotal();
        String produto = paginaInicial.descricaoProduto.getText();
        String produtoLower = paginaInicial.descricaoProduto.getText().toLowerCase();
        String itemLower = item.toLowerCase();
        String total = inicioInfo.getTotal();

        assertTrue("Item está incorreto. Foi exibido "+ produto + " mas deveria conter " + item.toLowerCase(), produtoLower.contains(itemLower));
        assertTrue("Valor total está incorreto. Foi exibido " + total + " mas deveria ser " + carrinhoInfo.getPrecoTotalProduto(), carrinhoInfo.getPrecoTotalProduto().equalsIgnoreCase(total));
        assertThat("Botão de Finalizar Compra não encontrado.", paginaInicial.botaoFinalizarCompra.isDisplayed(), is(equalTo(true)));
        assertThat("Link de Continuar comprando não encontrado.", paginaInicial.botaoContinuarComprando.isDisplayed(), is(equalTo(true)));
        System.out.println("\nValor total na mini página é: " + total);
        System.out.println("Item exibido na mini página é: " + produto);
    }

    @Quando("^Eu clico em Finalizar Compra na mini página do carrinho$")
    public void euClicoEmFinalizarCompraNaMiniPaginaDoCarrinho (){
        paginaInicial.botaoFinalizarCompra.click();
        EsperarElementoCarregar(driver, paginaCarrinho.botaoFecharPedido);
    }

    @Quando("^Eu aumento a quantidade do item no carrinho$")
    public void euAumentoAQuantidadeDoItemNoCarrinho (){
        paginaCarrinho.botaoAumentarQuantidade.click();
        EsperarElementoDesaparecer(driver, paginaCarrinho.loadingSpinnerCarrinho);
        aumentarQtd();
        salvarValorTotalProduto();
        salvarValorTotalCompra();
    }

    @Quando("^Eu diminuo a quantidade do item no carrinho$")
    public void euDiminuoAQuantidadeDoItemNoCarrinho (){
        paginaCarrinho.botaoDiminuirQuantidade.click();
        EsperarElementoDesaparecer(driver, paginaCarrinho.loadingSpinnerCarrinho);
        diminuirQtd();
        salvarValorTotalProduto();
        salvarValorTotalCompra();
    }

    @Então("^Eu vejo o valor da compra atualizado$")
    public void euVejoOValorDaCompraAtualizado (){
        String quantidadeCarrinhoNovo = carrinhoInfo.getQuantidade();
        String valorTotalTela = carrinhoInfo.getPrecoTotalProduto();
        String valorTotalProdutoCalculado = recuperarValorTotalCalculadoAposClique();
        String valorTotalCompraNovo = carrinhoInfo.getPrecoTotalCompra();

        assertTrue("Valor está incorretamente calculado, encontrado " + valorTotalTela + " mas deveria ser " + valorTotalProdutoCalculado, valorTotalProdutoCalculado.equalsIgnoreCase(valorTotalTela));
        assertTrue("Valor total da compra incorretamente calculado, encontrado" + valorTotalCompraNovo + " mas deveria ser " + valorTotalProdutoCalculado, valorTotalCompraNovo.equalsIgnoreCase(valorTotalProdutoCalculado));
        System.out.println("\nNovo valor do produto calculado : " + valorTotalProdutoCalculado);
        System.out.println("Novo valor total da compra atualizado : " + valorTotalCompraNovo);
        System.out.println("Quantidade de produtos no carrinho atualizado para: " + carrinhoInfo.getQuantidade());
    }

    @Quando("^Eu removo o item do carrinho$")
    public void euRemovoOItemDoCarrinho (){
        paginaCarrinho.removerItem.click();
        zerarQtd();
    }

    @Então("^Eu vejo a mensagem Seu carrinho está vazio$")
    public void euVejoAMensagemSeuCarrinhoEstaVazio (){
        EsperarElementoCarregar(driver, paginaCarrinho.mensagemCarrinhoVazio);
        String mensagem = paginaCarrinho.mensagemCarrinhoVazio.getText();
        String mensagemEsperada = "Seu carrinho está vazio.";

        assertTrue("Mensagem de carrinho vazio não encontrada ou incorreta. Foi exibido " + mensagem + " mas deveria exibir " + mensagemEsperada, mensagemEsperada.equalsIgnoreCase(mensagem));
        System.out.println("\nItem removido com sucesso.");
    }

    @Quando("^Eu clico em Escolher Produtos no carrinho$")
    public void euClicoEmEscolherProdutosNoCarrinho (){
        paginaCarrinho.botaoEscolherProdutos.click();
        EsperarElementoCarregar(driver, paginaInicial.welcome);
    }

    @Quando("^Eu fecho o pedido atual$")
    public void euFechoOPedidoAtual (){
        paginaCarrinho.botaoFecharPedido.click();
        EsperarElementoCarregar(driver, paginaCarrinho.botaoContinuar);
    }

    @E("^Eu preencho o email do cliente (.*) e clico em continuar$")
    public void euPreenchoOEmailDoClienteXEClicoEmContinuar (String cliente){
        paginaCheckout = new PaginaCheckout(driver);
        if(cliente.equals("cliente1")){
            cliente = ConfigFileReader.getInstance().getCliente1();
        }
        else if(cliente.equals("cliente2")){
            cliente = ConfigFileReader.getInstance().getCliente2();
        }
        paginaCarrinho.campoEmail.clear();
        paginaCarrinho.campoEmail.sendKeys(cliente);

        paginaCarrinho.botaoContinuar.click();
        EsperarElementoCarregar(driver, paginaCheckout.campoEmail);
    }

    @Quando("^Eu preencho os campos Email (.*), Primeiro Nome (.*), Último Nome (.*), CPF (.*), Telefone (.*) e clico em Ir para a entrega$")
    public void euPreenchoOsCamposEmailPrimeiroNomeUltimoNomeCPFTelefoneEClicoIrParaEntrega (String cliente, String primeiroNome, String ultimoNome, String cpf, String telefone){
        if(cliente.equals("cliente1")){
            cliente = ConfigFileReader.getInstance().getCliente1();
        }
        else if(cliente.equals("cliente2")){
            cliente = ConfigFileReader.getInstance().getCliente2();
        }
        String nomeClienteFormado = primeiroNome + " " + ultimoNome;
        paginaCheckout.campoEmail.clear();
        paginaCheckout.campoEmail.sendKeys(cliente);
        paginaCheckout.campoPrimeiroNome.clear();
        paginaCheckout.campoPrimeiroNome.sendKeys(primeiroNome);
        paginaCheckout.campoUltimoNome.clear();
        paginaCheckout.campoUltimoNome.sendKeys(ultimoNome);
        paginaCheckout.campoCPF.clear();
        paginaCheckout.campoCPF.sendKeys(cpf);
        paginaCheckout.campoTelefone.clear();
        paginaCheckout.campoTelefone.sendKeys(telefone);

        paginaCheckout.botaoIrParaEntrega.click();
        EsperarElementoCarregar(driver, paginaCheckout.dadosPessoaisPreenchidos);
        EsperarElementoCarregar(driver, paginaCheckout.campoCEP);

        String emailPreenchido = paginaCheckout.emailPreenchido.getText();
        String nomeClientePreenchido = paginaCheckout.nomePreenchido.getText();
        String telefonePreenchido = paginaCheckout.telefonePreenchido.getText();

        assertTrue("\nEmail exibido está incorreto. Exibido " + emailPreenchido + "mas deveria ser " + cliente, emailPreenchido.equalsIgnoreCase(cliente));
        assertTrue("Nome exibido está incorreto. Exibido " + nomeClientePreenchido + "mas deveria ser " + nomeClienteFormado, nomeClientePreenchido.equalsIgnoreCase(nomeClienteFormado));
        assertTrue("Telefone exibido está incorreto. Exibido " + telefonePreenchido + "mas deveria ser " + telefone, telefonePreenchido.equalsIgnoreCase(telefone));

        System.out.println("\nEmail exibido corretamente: " + emailPreenchido);
        System.out.println("Nome exibido corretamente: " + nomeClientePreenchido);
        System.out.println("Telefone exibido corretamente: " + telefonePreenchido);

    }

    @E("^Eu preencho o campo CEP (.*), depois o campo Número (.*) e verifico o valor do frete$")
    public void euPreenchoOCampoCEPDepoisOCampoNumeroEVerificoValorFrete (String cep, String numero){
        paginaCheckout.campoCEP.clear();
        paginaCheckout.campoCEP.sendKeys(cep);
        EsperarElementoCarregar(driver, paginaCheckout.campoNumero);
        paginaCheckout.campoNumero.clear();
        paginaCheckout.campoNumero.sendKeys(numero);
        EsperarElementoCarregar(driver, paginaCheckout.valorFreteSelecionado);
        String valorFrete = paginaCheckout.valorFreteSelecionado.getText();
        salvarFrete();

        paginaCheckout.botaoIrPagamento.click();
        EsperarElementoCarregar(driver, paginaCheckout.linkAlterarEntrega);
    }

    @E("^Eu seleciono a opção cartão de crédito e preencho os campos Número do cartão (.*), Quantidade de Parcelas (.*), Nome Impresso (.*), Validade Mês (.*), Validade Ano (.*), Código de Segurança (.*) e CPF do Titular (.*)$")
    public void euSelecionOpcaoCartaCreditoPreenchoCamposNumeroParcelasNomeValidadeMesAnoCodigoSegurancaCPF (String numeroCartao, String qtdParcelas, String nomeImpresso, String mes, String ano, String codigoSeguranca, String cpfTitular) throws InterruptedException {
        EsperarElementoCarregar(driver, paginaCheckout.descricaoBoleto);
        paginaCheckout.opcaoCartaoCredito.click();
        driver.switchTo().frame(0);
        String primeiraOpcaoParcelas = paginaCheckout.GetPrimeiraOpcaoQuantidadeParcelas();
        EsperarElementoCarregar(driver, paginaCheckout.campoNumeroCartaoCredito);
        paginaCheckout.campoNumeroCartaoCredito.clear();
        paginaCheckout.campoNumeroCartaoCredito.sendKeys(numeroCartao);
        Thread.sleep(2000);
        EsperarElementoComTexto(driver, primeiraOpcaoParcelas, paginaCheckout.quantidadeParcelas);
        EsperarElementoCarregar(driver, paginaCheckout.quantidadeParcelas);
        paginaCheckout.SetQuantidadeParcelas(qtdParcelas);
        paginaCheckout.campoNomeImpresso.clear();
        paginaCheckout.campoNomeImpresso.sendKeys(nomeImpresso);
        paginaCheckout.SetMesValidade(mes);
        paginaCheckout.SetAnoValidade(ano);
        paginaCheckout.campoCodigoSeguranca.clear();
        paginaCheckout.campoCodigoSeguranca.sendKeys(codigoSeguranca);
        paginaCheckout.campoCPFTitular.clear();
        paginaCheckout.campoCPFTitular.sendKeys(cpfTitular);

        driver.switchTo().defaultContent();

        salvarValorProdutoCheckout();
        salvarSubtotalCheckout();

        if(qtdParcelas.equals("1")){
            calcularSalvarValorDesconto();
        }
    }

    @Então("^Eu verifico que os valores finais da compra estão corretos$")
    public void euVerificoQueOsValoresFinaisDaCompraEstaoCorretos (){
        String valorSubTotalCheckout = checkoutInfo.getSubTotal();
        String valorEntrega = checkoutInfo.getPrecoFrete();
        String valorTotalTela = paginaCheckout.valorTotal.getAttribute("textContent").replace("R$", "").trim();

        if (paginaCheckout.ElementoPresenteDesconto(driver)){
            String valorDescontoTela = paginaCheckout.valorDesconto.getAttribute("textContent").replace("R$ -", "").trim();
            calcularSalvarValorDesconto();
            assertTrue("Valor de desconto está incorreto. Foi calculado e exibido " + checkoutInfo.getDesconto() + " mas deveria exibir " + valorDescontoTela, checkoutInfo.getDesconto().equalsIgnoreCase(valorDescontoTela));
            System.out.println("Valor do desconto: " +checkoutInfo.getDesconto());
        }

        System.out.println("\nValor do SubTotal é: " + valorSubTotalCheckout);
        System.out.println("\nValor da Entrega é: " + valorEntrega);

        calcularSalvarValorTotalCompra();

        assertTrue("Valor total da compra está incorreto. Foi calculado e exibido " + valorTotalTela + " mas deveria exibir " + checkoutInfo.getPrecoTotalCompra(), valorTotalTela.equalsIgnoreCase(checkoutInfo.getPrecoTotalCompra()));
        System.out.println("\nValor do SubTotal é: " + valorSubTotalCheckout);
        System.out.println("\nValor da Entrega é: " + valorEntrega);
        if(!(checkoutInfo.getDesconto()==null)){
            System.out.println("\nValor do desconto à vista é: " + checkoutInfo.getDesconto());
        }
        System.out.println("\nValor Total da Compra é: " + checkoutInfo.getPrecoTotalCompra());
    }

    @Então ("^Eu fecho o site do E-Commerce$")
    public void euFechoOSiteDoECommerce() {
        driver.quit();
    }

    private void navegarParaOSite(String url){
        paginaInicial = new PaginaInicial(driver);
        driver.get(url);
        driver.manage().window().maximize();
        EsperarElementoCarregar(driver, paginaInicial.welcome);

        //verificar se estou na página inicial do site
        assertThat("Pagina inicial nao encontrada", paginaInicial.welcome.isDisplayed(), is(equalTo(true)));
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos utilizados nos passos acima"
    //salvar valor do produto (unidade)
    public void salvarValorProduto (){
        carrinhoInfo.setPrecoProduto(paginaCarrinho.precoProduto.getText());
    }

    //salvar valor do total produto (calculado)
    public void salvarValorTotalProduto (){
        carrinhoInfo.setPrecoTotalProduto(paginaCarrinho.totalProduto.getText());
    }

    //salvar valor do total da compra
    public void salvarValorTotalCompra (){
        carrinhoInfo.setPrecoTotalCompra(paginaCarrinho.totalCompra.getText());
    }

    //recuperar quantidade de itens no ícone Carrinho sem zero a esquerda
    public String recuperarQuantidadeIconeCarrinho() {
        inicioInfo.setIconeCarrinhoQuantidade(paginaInicial.qtdItensCarrinho.getText());
        String carrinhoQuantidade = inicioInfo.getIconeCarrinhoQuantidade();

        return carrinhoQuantidade;
    }

    //salvar total compra da mini página do Carrinho
    public void salvarTotal (){
        inicioInfo.setTotal(paginaInicial.valorTotal.getText());
    }

    //aumentar quantidade no modelo de dados pelo clique no botão aumentar qtd
    public void aumentarQtd (){
        carrinhoInfo.aumentarQuantidadePorClique();
    }

    //diminuir quantidade no modelo de dados pelo clique no botão diminuir qtd
    public void diminuirQtd (){
        carrinhoInfo.diminuirQuantidadePorClique();
    }

    //Zerar quantidade no caso de Seu Carrinho Está Vazio
    public void zerarQtd (){
        carrinhoInfo.zerarQuantidade();
    }

    public String recuperarValorTotalCalculadoAposClique(){
        String precoTotalCalculado = carrinhoInfo.getPrecoTotalProdutoCalculoAposAumentoOuDiminuicao();

        return precoTotalCalculado;
    }

    //salvar valor do Frete após preencher endereço de entrega
    public void salvarFrete (){
        checkoutInfo.setPrecoFrete(paginaCheckout.valorFreteSelecionado.getText());
    }

    //salvar valor do produto na tela de checkout
    public void salvarValorProdutoCheckout (){
        checkoutInfo.setPrecoProduto(paginaCheckout.valorProduto.getText());
    }

    //salvar subtotal na tela de checkout
    public void salvarSubtotalCheckout (){
        checkoutInfo.setSubTotal(paginaCheckout.subtotal.getAttribute("textContent"));
    }

    //calcular e salvar valor de desconto à vista na tela de checkout
    public void calcularSalvarValorDesconto (){
        String descontoCalculado= checkoutInfo.getDescontoPorCalculo();
    }

    //calcular e salvar valor total da compra esperada
    public void calcularSalvarValorTotalCompra (){
        String totalCalculado = checkoutInfo.getPrecoTotalCompraPorCalculo();
    }

    // </editor-fold>

}
