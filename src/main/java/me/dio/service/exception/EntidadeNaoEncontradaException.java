package me.dio.service.exception;

public class EntidadeNaoEncontradaException extends RegraDeNegocioException {

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException() {
        super("Recurso não encontrado.");
    }

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
