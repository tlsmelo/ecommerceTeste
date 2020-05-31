package modeloDados;

import java.math.BigDecimal;

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

}
