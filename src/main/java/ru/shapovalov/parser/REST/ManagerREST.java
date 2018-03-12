package ru.shapovalov.parser.REST;


import ru.shapovalov.parser.Constants;
import ru.shapovalov.parser.POJO.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

public class ManagerREST implements ManagerRESTImpl {

    @Override
    public Set getPrice() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RequestSection.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            RequestSection requestSection = new RequestSection();
            requestSection.setGuidAgent(Constants.GUID);
            requestSection.setIdCatalog(Constants.GAME_CATOLOG_ID);
            requestSection.setLanguage(Constants.LANGUAGE_RU);
            requestSection.setEncoding(Constants.UTF_8);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(requestSection, stringWriter);
            String s = getData(Constants.SECTIONS_URL, stringWriter.toString());
            StringReader stringReader = new StringReader(s);
            JAXBContext jaxbContext1 = JAXBContext.newInstance(SectionList.class);
            Unmarshaller unmarshaller = jaxbContext1.createUnmarshaller();
            SectionList sessionList = (SectionList) unmarshaller.unmarshal(stringReader);
            return getPrice(sessionList.getSectionList());
        } catch (JAXBException e) {
            System.out.println("Method getPrice fall exception.");
        }
        return null;
    }

    private static String getData(String url, String request) {
        Client client = ClientBuilder.newClient();
        return client.target(url)
                .request(MediaType.APPLICATION_XML)
                .post(Entity.xml(request), String.class);
    }

    private static Set getPrice(List<Section> list) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RequestPrice.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        Set price = new HashSet();
        for (int i = 0; i < 6; i++) {
            for (Section section : list) {
                int sectionId = section.getSection_id();
                int countPages = section.getPageCount();
                for (int page = 1; page <= countPages; page++) {
                    RequestPrice requestPrice = new RequestPrice();
                    requestPrice.setGuidAgent(Constants.GUID);
                    requestPrice.setCurrency(Constants.LANGUAGE_RU);
                    requestPrice.setEncoding(Constants.UTF_8);
                    requestPrice.setIdSection(sectionId);
                    requestPrice.setPage(page);
                    requestPrice.setRows(Constants.MAX_ROWS);
                    requestPrice.setOrder(Constants.ORDER);
                    StringWriter stringWriter = new StringWriter();
                    marshaller.marshal(requestPrice, stringWriter);
                    String data = getData(Constants.GOODS_URL, stringWriter.toString());
                    StringReader stringReader = new StringReader(data);
                    JAXBContext jaxbContext1 = JAXBContext.newInstance(PriceList.class);
                    Unmarshaller unmarshaller = jaxbContext1.createUnmarshaller();
                    PriceList priceList = (PriceList) unmarshaller.unmarshal(stringReader);
                    price.addAll(priceList.getPriceList());
                }
            }
        }
        return price;
    }
}
