package com.user_api.model.entities;


import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String name;
    private String apresentation;

    @ManyToOne
    private Address address;

    public User(String username, String name, String apresentation, Address address) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.apresentation = apresentation;
    }

    public User(String id, String username, String name, String apresentation, Address address){
        this.id = id;
        this.name = name;
        this.username = username;
        this.apresentation = apresentation;
        this.address = address;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApresentation() {
        return apresentation;
    }

    public void setApresentation(String apresentation) {
        this.apresentation = apresentation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
}
