package ru.shapovalov.parser.POJO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "digiseller.response")
public class PriceList {


    private List<Price> priceList;

    @XmlElementWrapper(name="rows")
    @XmlElement(name="row")
    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }
}
