package com.user_api.model.entities;

import com.user_api.DTOs.ResponseAddressDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "addresses")
@Table(name="addresses")
@Getter
@Setter
@NoArgsConstructor
@ToString
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


}


