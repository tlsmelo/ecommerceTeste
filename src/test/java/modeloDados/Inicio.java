package modeloDados;

public class Inicio {

    private static Inicio inicioInfo;

    public static Inicio getInstance() {
        if (inicioInfo == null)
            inicioInfo = new Inicio();
            return inicioInfo;
    }

    private String iconeCarrinhoQuantidade;

    public String getIconeCarrinhoQuantidade () { return iconeCarrinhoQuantidade; }

    public void setIconeCarrinhoQuantidade (String iconeCarrinhoQuantidade) {
        int iconeCarrinhoQuantidadeNumero = Integer.parseInt(iconeCarrinhoQuantidade);
        iconeCarrinhoQuantidade = Integer.toString(iconeCarrinhoQuantidadeNumero);
        this.iconeCarrinhoQuantidade = iconeCarrinhoQuantidade; }

    private String total;

    public String getTotal () { return total; }

    public void setTotal (String total) {
        String totalSemCifrao = total.replace("R$", "").trim();
        this.total = totalSemCifrao; }

}
