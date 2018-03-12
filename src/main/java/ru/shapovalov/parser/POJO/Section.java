package ru.shapovalov.parser.POJO;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "section")
public class Section {
    private int section_id;
    private int cntGoods;

    @XmlAttribute(name = "id")
    public int getSection_id() {
        return section_id;
    }

    @XmlElement(name = "cnt_goods")
    public int getCntGoods() {
        return cntGoods;
    }

    public int getPageCount() {
        return (int) Math.ceil(((double)cntGoods) / 500);
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public void setCntGoods(int cntGoods) {
        this.cntGoods = cntGoods;
    }
}
