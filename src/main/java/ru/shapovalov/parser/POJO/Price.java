package ru.shapovalov.parser.POJO;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "row")
public class Price {
    private int idGoods;
    private String name;
    private Double price;
    private int cntSell;
    private int cntGoodResponses;
    private int cntBadResponses;
    private int idSellerInt;

    @XmlAttribute(name = "id_goods")
    public int getIdGoods() {
        return idGoods;
    }

    @XmlAttribute(name = "name_goods")
    public String getName() {
        return name;
    }

    @XmlAttribute(name = "price")
    public Double getPrice() {
        return price;
    }

    @XmlAttribute(name = "cnt_sell")
    public int getCntSell() {
        return cntSell;
    }

    @XmlAttribute(name = "cnt_goodresponses")
    public int getCntGoodResponses() {
        return cntGoodResponses;
    }

    @XmlAttribute(name = "cnt_badresponses")
    public int getCntBadResponses() {
        return cntBadResponses;
    }

    @XmlAttribute(name = "id_seller")
    public int getIdSellerInt() {
        return idSellerInt;
    }

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
