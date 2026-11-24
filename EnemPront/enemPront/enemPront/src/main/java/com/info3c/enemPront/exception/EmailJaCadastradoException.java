package com.info3c.enemPront.exception;

public class EmailJaCadastradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailJaCadastradoException() {
        super("Este email já possui uma conta");
    }
}
