package com.projeto.TesteMf.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Client {
    @Id
    private java.lang.Integer id;
    private java.lang.String name;
    private java.lang.String cpf;
    private java.lang.String address;
    private java.lang.String phone;

    public Client() {
    }

    public Client(Integer id, String name, String cpf, String address, String phone) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
