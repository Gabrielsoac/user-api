package com.user_api.model.entities;

import com.user_api.DTOs.ResponseAddressDTO;
import jakarta.persistence.*;

@Entity(name = "addresses")
@Table(name="addresses")
public class Address {

    @Id
    private String cep;

    private String bairro;
    private String cidade;
    private String estado;
    private String logradouro;


    public Address(ResponseAddressDTO address) {
        this.bairro = address.bairro();
        this.cidade = address.cidade();
        this.estado = address.estado();
        this.logradouro = address.logradouro();
        this.cep = address.cep();
    }

    public Address(){

    }

    public String getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    
}


