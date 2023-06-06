package com.projeto.TesteMf.Model;

import jakarta.persistence.*;

@Entity
public class Accont {
    @Id
    private java.lang.Integer id;
    private java.lang.Double value;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "id_client")
    private Client client;

    public  Accont() {}
    public Accont(Integer id, Double value, Client client) {
        this.id = id;
        this.value = value;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
