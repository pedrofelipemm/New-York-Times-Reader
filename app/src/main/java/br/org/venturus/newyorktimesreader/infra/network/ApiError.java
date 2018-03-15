package br.org.venturus.newyorktimesreader.infra.network;

public class ApiError {

    public static final String NO_ERROR = "-1";

    private String codigo = NO_ERROR;
    private String detalhe = NO_ERROR;
    private Tipo tipo = Tipo.NO_ERROR;

    public ApiError() {}

    ApiError(String detalhe, Tipo tipo) {
        this(null, detalhe, tipo);
    }

    ApiError(String codigo, String detalhe, Tipo tipo) {
        this.codigo = codigo;
        this.detalhe = detalhe;
        this.tipo = tipo;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static String getNoError() {
        return NO_ERROR;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ " +
                "codigo=" + codigo + " " +
                "detalhe=" + detalhe + " " +
                "tipo=" + tipo + " " +
                " ]";
    }

    public enum Tipo {
        WARN, ERROR, NO_ERROR
    }
}
