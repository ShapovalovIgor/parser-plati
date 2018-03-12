package ru.shapovalov.parser.POJO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "digiseller.request")
public class RequestSection {
    private String guidAgent;
    private int idCatalog;
    private String language;
    private String encoding;

    public String getGuidAgent() {
        return guidAgent;
    }

    public int getIdCatalog() {
        return idCatalog;
    }

    public String getLanguage() {
        return language;
    }

    public String getEncoding() {
        return encoding;
    }

    @XmlElement(name = "guid_agent")
    public void setGuidAgent(String guidAgent) {
        this.guidAgent = guidAgent;
    }

    @XmlElement(name = "id_catalog")
    public void setIdCatalog(int idCatalog) {
        this.idCatalog = idCatalog;
    }

    @XmlElement(name = "lang")
    public void setLanguage(String lengvich) {
        this.language = lengvich;
    }

    @XmlElement(name = "encoding")
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
