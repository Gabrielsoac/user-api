package com.user_api.model.entities;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name="users")
@Table(name="users")
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String email;

    @ManyToOne
    private Address address;

    public User(String name, String email, Address address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}
