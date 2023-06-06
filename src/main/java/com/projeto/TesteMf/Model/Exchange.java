package com.projeto.TesteMf.Model;

import jakarta.persistence.*;

@Entity
public class Exchange {

    @Id
    private int id;

    @JoinColumn(name = "id_conta_source")
    @ManyToOne
    private Accont accontSource;

    @JoinColumn(name = "id_conta_dest")
    @ManyToOne
    private Accont accontDest;

    private double value;

    public Exchange() {
    }

    public Exchange(int id, Accont accontSource, Accont accontDest, double value) {
        this.id = id;
        this.accontSource = accontSource;
        this.accontDest = accontDest;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Accont getAccontSource() {
        return accontSource;
    }

    public void setAccontSource(Accont accontSource) {
        this.accontSource = accontSource;
    }

    public Accont getAccontDest() {
        return accontDest;
    }

    public void setAccontDest(Accont accontDest) {
        this.accontDest = accontDest;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
