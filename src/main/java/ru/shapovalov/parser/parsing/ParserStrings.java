package ru.shapovalov.parser.parsing;

import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.shapovalov.parser.Constants;
import ru.shapovalov.parser.UI.MainUI;
import ru.shapovalov.parser.dao.Product;
import ru.shapovalov.parser.database.HibernateUtil;
import ru.shapovalov.parser.getdate.GetData;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.*;


public class ParserStrings {
    public ParserStrings() {
    }

    public static boolean startCollection = true;
    public Collection<Product> productCollection = new ArrayList<>();
    public HashSet<Integer> userCollection = new HashSet<>();

    public void parserGoods() throws Exception {
        GetData getData = new GetData();

        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            Map<Integer, Integer> sectionIdAndCountGoodsMap = getSectionIdAndCountGoods(db, getData, is);
            System.out.println(sectionIdAndCountGoodsMap.size());

            for (Map.Entry<Integer, Integer> sectionIdAndCountGoods : sectionIdAndCountGoodsMap.entrySet()) {
                int sectionId = sectionIdAndCountGoods.getKey();
                int countPages = sectionIdAndCountGoods.getValue();
                for (int page = 1; page <= countPages; page++) {
                    is.setCharacterStream(new StringReader(getData.getNewData(
                            new URL(Constants.URL),
                            Constants.GOODS_1 + sectionId + Constants.GOODS_2 + page + Constants.GOODS_3)));

                    Document doc = db.parse(is);

                    NodeList nodes = doc.getElementsByTagName("row");

                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element element = (Element) nodes.item(i);

                        NodeList id_goods = element.getElementsByTagName("id_goods");
                        Element line = (Element) id_goods.item(0);
                        int idGoodsInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList name_goods = element.getElementsByTagName("name_goods");
                        line = (Element) name_goods.item(0);
                        String[] nameGoodsStrArray = getCharacterDataFromElement(line)
                                .replace("&amp;", "")
                                .replace("– ", "")
                                .replace("–", " ")
                                .replace("quot;", "")
                                .split("[(,+,/,|,-]");
                        String nameGoodsStr = nameGoodsStrArray[0];

                        NodeList price = element.getElementsByTagName("price");
                        line = (Element) price.item(0);
                        double priceDoub = Double.valueOf(getCharacterDataFromElement(line)
                                .replace(',', '.'));

                        NodeList cntSell = element.getElementsByTagName("cnt_sell");
                        line = (Element) cntSell.item(0);
                        int cntSellInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList cntGoodResponses = element.getElementsByTagName("cnt_goodresponses");
                        line = (Element) cntGoodResponses.item(0);
                        int cntGoodresponsesInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList cntBadResponses = element.getElementsByTagName("cnt_badresponses");
                        line = (Element) cntBadResponses.item(0);
                        int cntBadresponsesInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList idSeller = element.getElementsByTagName("id_seller");
                        line = (Element) idSeller.item(0);
                        int idSellerInt = Integer.parseInt(getCharacterDataFromElement(line));

                        if (startCollection) {
                            productCollection.add(new Product(idGoodsInt, nameGoodsStr, priceDoub,
                                    cntSellInt, cntGoodresponsesInt, cntBadresponsesInt, idSellerInt, 0));
                            userCollection.add(idSellerInt);
                        }
                    }
                }
                break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainUI.hibernateUtil.addProducts(productCollection, userCollection);
    }


    public Map<Integer, Integer> getSectionIdAndCountGoods(DocumentBuilder db, GetData getData, InputSource is) throws IOException, SAXException {
        is.setCharacterStream(new StringReader(getData.getNewData(new URL(Constants.SECTIONS_URL), Constants.CATOLOG_ID_AND_COUNT_PAGE)));

        Document doc = db.parse(is);
        System.out.println(doc.toString());
        Map<Integer, Integer> sectionIdAndCountGoods = new HashMap<>();
        NodeList nodes = doc.getElementsByTagName("section");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element sectionIdElement = (Element) nodes.item(i);
            int sectionId = Integer.parseInt(sectionIdElement.getAttribute("id"));

            NodeList cntGoodsNodeList = sectionIdElement.getElementsByTagName("cnt_goods");
            Element cntGoodsElement = (Element) cntGoodsNodeList.item(0);
            double cntGoods = Integer.parseInt(getCharacterDataFromElement(cntGoodsElement));

            int pagesCount = (int) Math.floor((cntGoods / 500)) + 1;
            System.out.println("Section=" + sectionId + "Page count=" + pagesCount);
            sectionIdAndCountGoods.put(sectionId, pagesCount);
        }
        return sectionIdAndCountGoods;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }
}
