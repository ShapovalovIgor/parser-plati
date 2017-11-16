package ru.shapovalov.parser.dao;

import javax.persistence.Entity;

@Entity(name = "product")
public class Product {

    private int idGoods;
    private String nameGoods;
    private double price;
    private int cntSell;
    private int cntGoodResponses;
    private int cntBadResponses;
    private int idSeller;
    private int type;

    public Product(int idGoods, String nameGoods, double price, int cntSell, int cntGoodResponses, int cntBadResponses, int idSeller, int type) {
        this.idGoods = idGoods;
        this.nameGoods = nameGoods;
        this.price = price;
        this.cntSell = cntSell;
        this.cntGoodResponses = cntGoodResponses;
        this.cntBadResponses = cntBadResponses;
        this.idSeller = idSeller;
        this.type = type;
    }

    public void setIdGoods(int id_goods) {
        this.idGoods = id_goods;
    }

    public void setNameGoods(String nameGoods) {
        this.nameGoods = nameGoods;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCntSell(int cnt_sell) {
        this.cntSell = cnt_sell;
    }

    public void setCntGoodResponses(int cnt_goodresponses) {
        this.cntGoodResponses = cnt_goodresponses;
    }

    public void setCntBadResponses(int cnt_badresponses) {
        this.cntBadResponses = cnt_badresponses;
    }

    public void setIdSeller(int cnt_badresponses) {
        this.idSeller = idSeller;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getIdGoods() {
        return idGoods;
    }

    public String getNameGoods() {
        return nameGoods;
    }

    public double getPrice() {
        return price;
    }

    public int getCntSell() {
        return cntSell;
    }

    public int getCntGoodResponses() {
        return cntGoodResponses;
    }

    public int getCntBadResponses() {
        return cntBadResponses;
    }

    public int getIdSeller() {
        return idSeller;
    }

    public int getType() {
        return type;
    }

    @FunctionalInterface
    interface Intf<T>
    {
        public T get1(T arg1);
    }

}
