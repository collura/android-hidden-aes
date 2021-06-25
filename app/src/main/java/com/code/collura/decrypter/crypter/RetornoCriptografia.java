package com.code.collura.decrypter.crypter;

public class RetornoCriptografia {
    public RetornoCriptografia(String encodeToString) {
        this.texto = encodeToString;
    }

    public RetornoCriptografia(String ex, Boolean error) {
        this.ex = ex;
        this.error = error;
    }

    private String texto = "";
    private String ex = "";
    private Boolean error = false;

    public String getTexto() {
        return texto;
    }

    public String getEx() {
        return ex;
    }


    public Boolean getError() {
        return error;
    }
}
