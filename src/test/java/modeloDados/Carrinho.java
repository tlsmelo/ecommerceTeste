package modeloDados;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Carrinho {

    private static Carrinho carrinhoInfo;

    public static Carrinho getInstance() {
        if (carrinhoInfo == null)
            carrinhoInfo = new Carrinho();
            return carrinhoInfo;
    }

    private String precoProduto;

    public String getPrecoProduto () { return precoProduto; }

    public void setPrecoProduto (String precoProduto) {
        String precoProdutoSemCifrao = precoProduto.replace("R$", "").trim();
        this.precoProduto = precoProdutoSemCifrao; }

    private String precoTotalProduto;

    public String getPrecoTotalProduto () { return precoTotalProduto; }

    public String getPrecoTotalProdutoCalculoAposAumentoOuDiminuicao () {
        BigDecimal valorUnitario = new BigDecimal(precoProduto.replaceAll("\\.", "").replace(",","."));
        BigDecimal quantidade = new BigDecimal(getQuantidade());
        BigDecimal valorTotalCalculado = valorUnitario.multiply(quantidade);
        DecimalFormat df = new DecimalFormat("###,##0.00");

        precoTotalProduto = df.format(valorTotalCalculado);

        setPrecoTotalProduto(precoTotalProduto);

        return precoTotalProduto;
    }

    public void setPrecoTotalProduto (String precoTotalProduto) {
        String precoTotalProdutoSemCifrao = precoTotalProduto.replace("R$", "").trim();
        this.precoTotalProduto = precoTotalProdutoSemCifrao; }

    private String quantidade;

    public String getQuantidade () { return quantidade; }

    public String getQuantidadePorCalculo () {
        BigDecimal valorTotal = new BigDecimal(precoTotalProduto.replaceAll("\\.", "").replace(",","."));
        BigDecimal valorUnitario = new BigDecimal(precoProduto.replaceAll("\\.", "").replace(",","."));
        BigDecimal quantidadeCalculada = valorTotal.divide(valorUnitario);
        quantidade = quantidadeCalculada.toPlainString();

        setQuantidade(quantidade);
        return quantidade;
    }

    public void setQuantidade (String quantidade) {
        this.quantidade = quantidade; }

    public void aumentarQuantidadePorClique (){
        int quantidadeNumero = Integer.parseInt(quantidade);
        quantidadeNumero = quantidadeNumero + 1;
        quantidade = Integer.toString(quantidadeNumero);
        setQuantidade(quantidade);
    }

    public void diminuirQuantidadePorClique (){
        int quantidadeNumero = Integer.parseInt(quantidade);
        quantidadeNumero = quantidadeNumero - 1;
        if(quantidadeNumero==0){
            quantidadeNumero=1;
            System.out.println("Quantidade mínima alcançada, não pode ser menor que 1. Para remover clique no botão de remoção de item.");
        }
        quantidade = Integer.toString(quantidadeNumero);
        setQuantidade(quantidade);
    }

    public void zerarQuantidade (){
        quantidade = "0";
        setQuantidade(quantidade);
    }

    private String precoTotalCompra;

    public String getPrecoTotalCompra() { return precoTotalCompra; }

    public void setPrecoTotalCompra (String precoTotalCompra) {
        String precoTotalCompraSemCifrao = precoTotalCompra.replace("R$", "").trim();
        this.precoTotalCompra = precoTotalCompraSemCifrao; }

}
