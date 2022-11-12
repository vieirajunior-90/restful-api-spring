package br.dev.paulovieira.restfulapispring.model;

import jakarta.persistence.*;

import java.io.*;

@Entity
@Table(name = "person", indexes = {
        @Index(name = "idx_person_id", columnList = "id")
})
public class Person {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30, name = "first_name")
    private String firstName;
    @Column(nullable = false, length = 30, name = "last_name")
    private String lastName;
    @Column(nullable = false, length = 100, name = "address")
    private String address;
    @Column(nullable = false, length = 6, name = "gender")
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
