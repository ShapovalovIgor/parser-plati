package ru.shapovalov.parser.DAO;

import javax.persistence.*;

@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "id_good")
    private int idGoods;
    @Column(name = "name_goods")
    private String nameGoods;
    @Column(name = "price_id")
    private Double[] prices;
    @Column(name = "cnt_sell")
    private int cntSell;
    @Column(name = "cnt_good_responses")
    private int cntGoodResponses;
    @Column(name = "cnt_bad_responses")
    private int cntBadResponses;
    @Column(name = "id_seller")
    private int idSeller;
    @Column(name = "type_product")
    private int type;

    public Product() {
    }

    public Product(int idGoods, String nameGoods, Double[] prices, int cntSell, int cntGoodResponses, int cntBadResponses, int idSeller, int type) {
        this.idGoods = idGoods;
        this.nameGoods = nameGoods;
        this.prices = prices;
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

    public void setPrices(Double[] prices) {
        this.prices = prices;
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



    public Double[] getPrices() {
        return prices;
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
    interface Intf<T> {
        public T get1(T arg1);
    }
}
