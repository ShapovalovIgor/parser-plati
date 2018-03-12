package ru.shapovalov.parser.POJO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "digiseller.request")
public class RequestPrice {
    private String guidAgent;
    private int idSection;
    private String encoding;
    private int page;
    private int rows;
    private String currency;
    private String order;

    public String getGuidAgent() {
        return guidAgent;
    }

    public int getIdSection() {
        return idSection;
    }

    public String getEncoding() {
        return encoding;
    }

    public int getPage() {
        return page;
    }

    public int getRows() {
        return rows;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOrder() {
        return order;
    }

    @XmlElement(name = "guid_agent")
    public void setGuidAgent(String guidAgent) {
        this.guidAgent = guidAgent;
    }

    @XmlElement(name = "id_section")
    public void setIdSection(int idSection) {
        this.idSection = idSection;
    }

    @XmlElement(name = "encoding")
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @XmlElement(name = "page")
    public void setPage(int page) {
        this.page = page;
    }

    @XmlElement(name = "rows")
    public void setRows(int rows) {
        this.rows = rows;
    }

    @XmlElement(name = "currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name = "order")
    public void setOrder(String order) {
        this.order = order;
    }
}
