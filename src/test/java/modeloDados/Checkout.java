package modeloDados;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Checkout {

    private static Checkout checkoutInfo;

    public static Checkout getInstance() {
        if (checkoutInfo == null)
            checkoutInfo = new Checkout();
            return checkoutInfo;
    }

    private String precoProduto;

    public String getPrecoProduto () { return precoProduto; }

    public void setPrecoProduto (String precoProduto) {
        String precoProdutoSemCifrao = precoProduto.replace("R$", "").trim();
        this.precoProduto = precoProdutoSemCifrao; }

    private String precoFrete;

    public String getPrecoFrete () { return precoFrete; }

    public void setPrecoFrete (String precoFrete) {
        String precoFreteSemCifrao = precoFrete.replace("R$", "").trim();
        this.precoFrete = precoFreteSemCifrao; }

    private String subTotal;

    public String getSubTotal () { return subTotal; }

    public void setSubTotal (String subTotal) {
        String subTotalSemCifrao = subTotal.replace("R$", "").trim();
        this.subTotal = subTotalSemCifrao; }

    private String desconto = null;

    public String getDesconto () { return desconto; }

    public String getDescontoPorCalculo () {
        BigDecimal valorProduto = new BigDecimal(subTotal.replaceAll("\\.", "").replace(",","."));
        //7% de desconto se for à vista
        BigDecimal descontoCalculado = valorProduto.multiply(new BigDecimal("0.07"));

        DecimalFormat df = new DecimalFormat("###,##0.00");

        desconto = df.format(descontoCalculado);

        setDesconto(desconto);

        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto; }

    private String precoTotalCompra;

    public String getPrecoTotalCompra() { return precoTotalCompra; }

    public void setPrecoTotalCompra (String precoTotalCompra) {
        String precoTotalCompraSemCifrao = precoTotalCompra.replace("R$", "").trim();
        this.precoTotalCompra = precoTotalCompraSemCifrao; }

    public String getPrecoTotalCompraPorCalculo() {
        BigDecimal valorSubTotal = new BigDecimal(subTotal.replaceAll("\\.", "").replace(",","."));
        BigDecimal valorFrete = new BigDecimal(precoFrete.replaceAll("\\.", "").replace(",","."));
        BigDecimal valorTotalCalculado;
        if (!(getDesconto() == null)) {
            BigDecimal valorDesconto = new BigDecimal(desconto.replaceAll("\\.", "").replace(",","."));
            valorTotalCalculado = valorSubTotal.add(valorFrete).subtract(valorDesconto);
        }
        else{
            valorTotalCalculado = valorSubTotal.add(valorFrete);
        }

        DecimalFormat df = new DecimalFormat("###,##0.00");

        precoTotalCompra = df.format(valorTotalCalculado);

        setPrecoTotalCompra(precoTotalCompra);

        return precoTotalCompra;
    }

}
