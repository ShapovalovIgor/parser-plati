package ru.shapovalov.parser.dao;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private int idSeller;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idSeller")
    private Set<Product> name_goods = new HashSet<>();

    public User(int idSeller) {
        this.idSeller = idSeller;
        this.name_goods = null;

    }

    public User() {

    }

    public User(int idSeller, Set<Product> name_goods) {
        this.idSeller = idSeller;
        this.name_goods = name_goods;

    }


    public void setIdSeller(int idSeller) {
        this.idSeller = idSeller;
    }

    public void setGoods(Set<Product> name_goods) {
        this.name_goods = name_goods;
    }

    public int getIdSeller() {
        return idSeller;
    }

    public Collection<Product> getGoods() {
        return name_goods;
    }

    }
