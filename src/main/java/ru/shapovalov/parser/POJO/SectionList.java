package ru.shapovalov.parser.POJO;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "digiseller.response")
public class SectionList {


    private List<Section> sectionList;
    @XmlElement(name = "section")
    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }
}
