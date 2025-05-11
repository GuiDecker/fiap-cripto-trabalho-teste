package br.com.tiopatinhasexchange.exceptions;

public class ValorInvalidoException extends RuntimeException {

    public ValorInvalidoException() {
    }

    public ValorInvalidoException(String message) {
        super(message);
    }
    
}
