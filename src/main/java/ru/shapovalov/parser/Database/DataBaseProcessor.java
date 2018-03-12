package ru.shapovalov.parser.Database;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import ru.shapovalov.parser.ConcurrentComponent;
import ru.shapovalov.parser.DAO.PriceDAO;
import ru.shapovalov.parser.DAO.Product;
import ru.shapovalov.parser.REST.ManagerREST;
import ru.shapovalov.parser.REST.ManagerRESTImpl;
import ru.shapovalov.parser.UI.MainUI;
import ru.shapovalov.parser.POJO.Price;
import ru.shapovalov.parser.UI.Table.PriceTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataBaseProcessor extends ConcurrentComponent {
    private HibernateUtil hibernateUtil;

    public DataBaseProcessor() {
        this.hibernateUtil = new HibernateUtil();
    }

    public static boolean statusDB = false;

    public void processor(VerticalLayout layout) {
        hibernateUtil = new HibernateUtil();
        Collection product = hibernateUtil.getProduct();
        if (null == product
                || product.isEmpty()) {
            startProcessor(layout);
            while (!DataBaseProcessor.statusDB) {
            }
            DataBaseProcessor.statusDB = false;
            product = hibernateUtil.getProduct();
            PriceTable priceTable = new PriceTable(product);
            layout.addComponent(priceTable);
        } else {
            PriceTable priceTable = new PriceTable(product);
            layout.addComponent(priceTable);
            startProcessor(layout);
        }

    }

    private void startProcessor(VerticalLayout layout) {
        executeUpdate(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ManagerRESTImpl managerREST = new ManagerREST();
                    Set pricesSet = managerREST.getPrice();
                    List productList = new ArrayList<Product>(pricesSet.size());
                    Set userId = new HashSet<Integer>();
                    for (Object priceObj : pricesSet) {
                        int idGoods = ((Price) priceObj).getIdGoods();
                        String name = ((Price) priceObj).getName();
                        Double price = ((Price) priceObj).getPrice();
                        Double[] prices = updatePrice(idGoods, price);
                        int cntSell = ((Price) priceObj).getCntSell();
                        int cntGoodResponses = ((Price) priceObj).getCntGoodResponses();
                        int cntBadResponses = ((Price) priceObj).getCntBadResponses();
                        int sellerId = ((Price) priceObj).getIdSellerInt();
                        userId.add(sellerId);
                        Product product = new Product(idGoods, name, prices, cntSell, cntGoodResponses, cntBadResponses, sellerId, 0);
                        productList.add(product);
                    }
                    hibernateUtil.addProducts(productList, userId);
                    statusDB = true;
                    Collection product = hibernateUtil.getProduct();
                    PriceTable priceTable = new PriceTable(product);
                    layout.addComponent(priceTable);
                }
            }
        });
    }


    private Double[] updatePrice(int id, Double price) {
        List<PriceDAO> prices = (List<PriceDAO>) MainUI.hibernateUtil.getPriceHistory(id);
        if (prices == null
                || prices.isEmpty()) {
            PriceDAO priceObj = new PriceDAO(id, price, getDate());
            prices = new ArrayList<>();
            prices.add(priceObj);
            System.out.println("***add price***size:" + prices.size() + " id:" + id);
            MainUI.hibernateUtil.addPrice(priceObj);
        } else {
            Date date = getDate();
            PriceDAO priceTmp = null;
            for (PriceDAO priceObj : prices) {
                if (priceObj.getDate().equals(date)) {
                    priceObj.setPrice(price);
                    priceTmp = priceObj;
                    MainUI.hibernateUtil.updatePrice(priceObj);
                    System.out.println("***update price***");
                }
            }
            if (priceTmp != null) {
                prices.add(priceTmp);
                return getValues(prices);
            }
            if (prices.size() == 30) {
                PriceDAO priceObj = new PriceDAO(id, price, getDate());
                prices = addElement(prices, priceObj);
                MainUI.hibernateUtil.removePrices(id, prices.get(0).getDate());
                System.out.println("***remove price30***");

                MainUI.hibernateUtil.addPrice(priceObj);

            } else {
                PriceDAO priceObj = new PriceDAO(id, price, getDate());
                prices.add(priceObj);
                MainUI.hibernateUtil.removePrices(id, prices.get(0).getDate());
                System.out.println("***remove price***");
                MainUI.hibernateUtil.addPrice(priceObj);
            }

        }
        return getValues(prices);
    }

    private List<PriceDAO> addElement(List list, PriceDAO price) {
        if (list != null) {
            int length = list.size();
            List temp = new ArrayList(length);
            for (int elemNum = 0; elemNum < length; elemNum++) {
                temp.add(elemNum, list.get(elemNum + 1));
            }
            temp.add(length, price);
            return temp;
        } else {
            return null;
        }
    }

    private Double[] getValues(List list) {
        if (list != null) {
            int length = list.size();
            Double[] temp = new Double[length];
            for (int elemNum = 0; elemNum < length; elemNum++) {
                Price price = (Price) list.get(elemNum);
                temp[elemNum] = price.getPrice();
            }
            return temp;
        } else {
            return null;
        }
    }

    private Date getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
        Date date = new Date();
        String format = dateFormat.format(date);
        Date dt = null;
        try {
            dt = dateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
}
