package ru.shapovalov.parser.POJO;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "row")
public class Price {

    private int idGoods;
    private String name;
    private Double price;
    private int cntSell;
    private int cntGoodResponses;
    private int cntBadResponses;
    private int idSellerInt;
    public int getIdGoods() {
        return idGoods;
    }
    @XmlElement(name = "name_goods")
    public String getName() {
        return name;
    }
    @XmlElement(name = "price")
    public Double getPrice() {
        return price;
    }
    @XmlElement(name = "cnt_sell")
    public int getCntSell() {
        return cntSell;
    }
    @XmlElement(name = "cnt_goodresponses")
    public int getCntGoodResponses() {
        return cntGoodResponses;
    }
    @XmlElement(name = "cnt_badresponses")
    public int getCntBadResponses() {
        return cntBadResponses;
    }
    @XmlElement(name = "id_seller")
    public int getIdSellerInt() {
        return idSellerInt;
    }
    @XmlElement(name = "id_goods")
    public void setIdGoods(int idGoods) {
        this.idGoods = idGoods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCntSell(int cntSell) {
        this.cntSell = cntSell;
    }

    public void setCntGoodResponses(int cntGoodResponses) {
        this.cntGoodResponses = cntGoodResponses;
    }

    public void setCntBadResponses(int cntBadResponses) {
        this.cntBadResponses = cntBadResponses;
    }

    public void setIdSellerInt(int idSellerInt) {
        this.idSellerInt = idSellerInt;
    }
}
