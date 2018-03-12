package ru.shapovalov.parser.DAO;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "price")
public class PriceDAO {
    @Id
    @Column(name = "id", unique = false, nullable = false)
    private int priceId;
    @Column(name = "price")
    private double price;
    @Column(name = "date")
    private Date date;

    public PriceDAO(int priceId) {
        this.priceId = priceId;
        this.date = null;

    }

    public PriceDAO() {

    }

    public PriceDAO(int priceId, double price, Date date) {
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
