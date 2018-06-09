package ru.shapovalov.parser.Database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import com.vaadin.server.VaadinService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.shapovalov.parser.Constants;
import ru.shapovalov.parser.DAO.PriceDAO;
import ru.shapovalov.parser.DAO.Product;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

public class HibernateUtil {
    public HibernateUtil() {
    }

    private static final String PERSISTENT_UNIT_NAME = "item-manager-pu";
    private static final Log LOG = LogFactory.getLog(DataBaseProcessor.class);
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEm() {
        return emf.createEntityManager();
    }


    @Transactional
    public void executeSQLCommand(String sql) throws Exception {
        EntityManager em = getEm();
        em.getTransaction().begin();
        em.createNativeQuery(sql).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public void executeSQLCommandInFile(String pathFile) throws Exception {
        InputStream in = new FileInputStream(findQuery(pathFile));
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");

        while (s.hasNext()) {
            String line = s.next();
            if (line.startsWith("/*!") && line.endsWith("*/")) {
                int i = line.indexOf(' ');
                line = line.substring(i + 1, line.length() - " */".length());
            }

            if (line.trim().length() > 0) {
                executeSQLCommand(line);
            }
        }
    }

    public String findQuery(String filePatch) {
        String queryFolder = "\\WEB-INF\\classes\\";
        String applicationFolder = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        return applicationFolder + queryFolder + filePatch;
    }

    public List<Product> getProduct() {
        EntityManager em = getEm();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(Product.class);
        cq.from(Product.class);
        List productList = em.createQuery(cq).getResultList();
        em.close();
        return productList;
    }


    public Collection<PriceDAO> getPriceHistory(int id) {
        EntityManager em = getEm();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(PriceDAO.class);
        cq.from(PriceDAO.class);
        List priceList = em.createQuery(cq).getResultList();
        Query query = null;
        if (!priceList.isEmpty()) {
            for (Object price : priceList) {
                if (id == ((PriceDAO) price).getPriceId()) {
                    query = em.createQuery("FROM price  WHERE id =" + id);
                    List<PriceDAO> prices = (List<PriceDAO>) query.getResultList();
                    em.close();
                    Collections.sort(prices, new Comparator<PriceDAO>() {
                        public int compare(PriceDAO o1, PriceDAO o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });
                    return prices;
                }
            }
        }
        return null;
    }

    public void addPrices(Collection<PriceDAO> priceCollection) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        for (PriceDAO price : priceCollection)
            em.persist(price);
        em.getTransaction().commit();
        em.close();
    }

    public void addPrice(PriceDAO price) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        em.persist(price);
        em.getTransaction().commit();
        em.close();
    }

    public void removePrices(int id, Date date) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        Query query = em
                .createQuery("DELETE FROM price WHERE id = :id and date = :date");
        query.setParameter("id", id);
        query.setParameter("date", date);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public boolean updatePrice(PriceDAO price) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        Query query = em
                .createQuery("UPDATE price price SET price.price = :price "
                        + "WHERE price.id= :id and price.date = :date");
        query.setParameter("price", price.getPrice());
        query.setParameter("id", price.getPriceId());
        query.setParameter("date", price.getDate());
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public void addProducts(Collection<Product> productList) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        for (Product product : productList)
            em.persist(product);
        em.getTransaction().commit();
        em.close();
    }

    public void addProducts(Collection<Product> productList, Set<Integer> userIdList) {
        if(LOG.isDebugEnabled())
            LOG.debug("add Product list size:" + productList.size() + "user list size:" + userIdList.size());
        Collection oldProduct = getProduct();
        productList.removeAll(oldProduct);
        if(LOG.isDebugEnabled())
            LOG.debug("List old size:" + oldProduct.size() + "List new size:" + productList.size());
        if (!productList.isEmpty()) {
            EntityManager em = getEm();
            em.getTransaction().begin();
            for (Product product : productList)
                em.persist(product);
            em.getTransaction().commit();
            em.close();
        }
    }


    public boolean createDB() {
        try {
            executeSQLCommand("drop table student;");
            executeSQLCommand("drop table student_group;");


            executeSQLCommandInFile(Constants.CREATE_TABLE_SCRIPT);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}