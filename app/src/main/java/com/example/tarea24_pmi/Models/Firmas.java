package com.example.tarea24_pmi.Models;

public class Firmas {
    private byte[] signature;
    private String id, nombre;

    public Firmas(String id, byte[] signature, String nombre) {
        this.id = id;
        this.signature = signature;
        this.nombre = nombre;

    }

    public Firmas() {
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
