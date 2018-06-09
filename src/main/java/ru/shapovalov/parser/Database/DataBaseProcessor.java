package ru.shapovalov.parser.Database;

import com.vaadin.ui.VerticalLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.shapovalov.parser.DAO.PriceDAO;
import ru.shapovalov.parser.DAO.Product;
import ru.shapovalov.parser.REST.ManagerREST;
import ru.shapovalov.parser.REST.ManagerRESTImpl;
import ru.shapovalov.parser.POJO.Price;
import ru.shapovalov.parser.UI.Table.PriceTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataBaseProcessor extends HibernateUtil {
    private static final Log LOG = LogFactory.getLog(DataBaseProcessor.class);
    public static boolean statusDB = false;

    public void processor(VerticalLayout layout) {
        if (LOG.isDebugEnabled())
            LOG.debug("Start processor DB");
        Collection product = getProduct();
        if (LOG.isDebugEnabled())
            LOG.debug("Product size:" + product.size());
        if (null == product
                || product.isEmpty()) {
            startProcessor();
            while (!DataBaseProcessor.statusDB) {
            }
            DataBaseProcessor.statusDB = false;
            product = getProduct();
            PriceTable priceTable = new PriceTable(product);
            layout.addComponent(priceTable);//требуется переместить в метод startProcessor()
        } else {
            if (LOG.isDebugEnabled())
                LOG.debug("Product not null:" + product.size());
            PriceTable priceTable = new PriceTable(product);
            layout.addComponent(priceTable);
            startProcessor();
        }

    }

    private void startProcessor() {
        if (LOG.isDebugEnabled())
            LOG.debug("Start processor");
        while (true) {
            ManagerRESTImpl managerREST = new ManagerREST();
            Set pricesSet = managerREST.getPrice();
            List productList = new ArrayList<Product>(pricesSet.size());
            Set userId = new HashSet<Integer>();
            if (LOG.isDebugEnabled())
                LOG.debug("Price size:" + pricesSet.size());
            for (Object priceObj : pricesSet) {
                int idGoods = ((Price) priceObj).getIdGoods();
                String name = ((Price) priceObj).getName();
                Double price = ((Price) priceObj).getPrice();
                if (idGoods != 0 && price != null) {
                    Double[] prices = updatePrice(idGoods, price);
                    int cntSell = ((Price) priceObj).getCntSell();
                    int cntGoodResponses = ((Price) priceObj).getCntGoodResponses();
                    int cntBadResponses = ((Price) priceObj).getCntBadResponses();
                    int sellerId = ((Price) priceObj).getIdSellerInt();
                    userId.add(sellerId);
                    Product product = new Product(idGoods, name, prices, cntSell, cntGoodResponses, cntBadResponses, sellerId, 0);
                    productList.add(product);
                    if (LOG.isDebugEnabled())
                        LOG.debug("Start Processor add product and id:" + product.getIdGoods());
                }
            }

            addProducts(productList, userId);
            if (LOG.isDebugEnabled())
                LOG.debug("Add prod");
            statusDB = true;

            if (LOG.isDebugEnabled())
                LOG.debug("Start processor exit");
            break;
        }
    }


    private Double[] updatePrice(int id, Double price) {
        if (LOG.isDebugEnabled())
            LOG.debug("Update price id:" + id + " Price:" + price);
        List<PriceDAO> prices = (List<PriceDAO>) getPriceHistory(id);
        if (prices == null
                || prices.isEmpty()) {
            PriceDAO priceObj = new PriceDAO(id, price, getDate());
            prices = new ArrayList<>();
            prices.add(priceObj);
            if (LOG.isDebugEnabled())
                LOG.debug("***add price***size:" + prices.size() + " id:" + id);
            addPrice(priceObj);
        } else {
            Date date = getDate();
            PriceDAO priceTmp = null;
            for (PriceDAO priceObj : prices) {
                if (priceObj.getDate().equals(date)) {
                    priceObj.setPrice(price);
                    priceTmp = priceObj;
                    updatePrice(priceObj);
                    if (LOG.isDebugEnabled())
                        LOG.debug("***update price***");
                }
            }
            if (priceTmp != null) {
                prices.add(priceTmp);
                return getValues(prices);
            }
            if (prices.size() == 30) {
                PriceDAO priceObj = new PriceDAO(id, price, getDate());
                prices = addElement(prices, priceObj);
                removePrices(id, prices.get(0).getDate());
                if (LOG.isDebugEnabled())
                    LOG.debug("***remove price30***");

                addPrice(priceObj);

            } else {
                PriceDAO priceObj = new PriceDAO(id, price, getDate());
                prices.add(priceObj);
                removePrices(id, prices.get(0).getDate());
                if (LOG.isDebugEnabled())
                    LOG.debug("***remove price*** priceObj id:" + priceObj.getPriceId());
                addPrice(priceObj);
            }

        }
        if (LOG.isDebugEnabled())
            LOG.debug("return: size:" + prices.size() + " values:" + prices.toArray());
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

    private Double[] getValues(List<PriceDAO> list) {
        if (LOG.isDebugEnabled())
            LOG.debug("Start get value");
        if (list != null) {
            int length = list.size();
            Double[] temp = new Double[length];
            for (int elemNum = 0; elemNum < length; elemNum++) {
                PriceDAO price = list.get(elemNum);
                temp[elemNum] = price.getPrice();
            }
            return temp;
        } else {
            if (LOG.isDebugEnabled())
                LOG.debug("Get value return null");
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
