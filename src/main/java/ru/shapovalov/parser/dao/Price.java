package ru.shapovalov.parser.dao;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity(name = "price")
public class Price {
    @Id
    @Column(name = "id", unique = false, nullable = false)
    private int priceId;
    @Column(name = "price")
    private double price;
    @Column(name = "date")
    private Date date;

    public Price(int priceId) {
        this.priceId = priceId;
        this.date = null;

    }

    public Price() {

    }

    public Price(int priceId, double price, Date date) {
        this.priceId = priceId;
        this.price = price;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriceId() {
        return priceId;
    }

    public Date getDate() {
        return date;
    }

    }
