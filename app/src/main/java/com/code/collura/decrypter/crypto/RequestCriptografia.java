package com.code.collura.decrypter.crypto;



public class RequestCriptografia {

    private String key;
    private String texto;

    public RequestCriptografia(String key, String texto) {
        this.key = key;
        this.texto = texto;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
