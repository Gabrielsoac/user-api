package com.user_api.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String name;
    private String apresentation;

    @ManyToOne //(cascade = CascadeType.ALL)
    private Address address;

    public User(String username, String name, String apresentation, Address address) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.apresentation = apresentation;
    }
}
