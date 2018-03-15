package br.org.venturus.newyorktimesreader.infra.factory;

import br.org.venturus.newyorktimesreader.infra.network.ApiError;
import br.org.venturus.newyorktimesreader.infra.network.ApiError.Tipo;
import br.org.venturus.newyorktimesreader.infra.network.NetworkConstants.Error;

public class ApiErrorFactory {

    public static ApiError createNoConnectionError() {
        ApiError error = new ApiError();
        error.setCodigo(Error.NO_CONNECTION_AVAILABLE_CODE);
        error.setDetalhe(Error.NO_CONNECTION_AVAILABLE_DESC);
        error.setTipo(Tipo.ERROR);

        return error;
    }

    public static ApiError createInternalServerErrorError() {
        ApiError error = new ApiError();
        error.setCodigo(Error.INTERNAL_SERVER_ERROR_CODE);
        error.setDetalhe(Error.INTERNAL_SERVER_ERROR_DESC);
        error.setTipo(Tipo.ERROR);

        return error;
    }

    public static ApiError createParseError() {
        ApiError error = new ApiError();
        error.setCodigo(Error.PARSE_ERROR_CODE);
        error.setDetalhe(Error.PARSE_ERROR_DESC);
        error.setTipo(Tipo.ERROR);

        return error;
    }

    public static ApiError createValidationError() {
        ApiError error = new ApiError();
        error.setCodigo(Error.VALIDATION_CODE);
        error.setDetalhe(Error.VALIDATION_DESC);
        error.setTipo(Tipo.WARN);

        return error;
    }

    public static ApiError createTimeoutError() {
        ApiError error = new ApiError();
        error.setCodigo(Error.TIMEOUT_CODE);
        error.setDetalhe(Error.TIMEOUT_DESC);
        error.setTipo(Tipo.ERROR);

        return error;
    }

}
