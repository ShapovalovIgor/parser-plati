package ru.shapovalov.parser.dao;

import javax.persistence.Entity;
import java.util.Collection;

@Entity(name = "user")
public class User {

    private int idSeller;
    private Collection<Product> name_goods;


    public User(int idSeller, Collection<Product> name_goods) {
        this.idSeller = idSeller;
        this.name_goods = name_goods;

    }

    public void setIdSeller(int idSeller) {
        this.idSeller = idSeller;
    }

    public void setName_goods(Collection<Product> name_goods) {
        this.name_goods = name_goods;
    }

    public int getIdSeller() {
        return idSeller;
    }

    public Collection<Product> getName_goods() {
        return name_goods;
    }

    }
