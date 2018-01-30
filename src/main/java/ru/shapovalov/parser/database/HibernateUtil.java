package ru.shapovalov.parser.database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import com.vaadin.server.VaadinService;

import ru.shapovalov.parser.Constants;
import ru.shapovalov.parser.dao.Price;
import ru.shapovalov.parser.dao.Product;
import ru.shapovalov.parser.dao.User;

import javax.management.ObjectName;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

public class HibernateUtil {
    public HibernateUtil() {
    }

    private static final String PERSISTENT_UNIT_NAME = "item-manager-pu";

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


    public List<User> getUser() {
        EntityManager em = getEm();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(User.class);
        cq.from(User.class);
        List userList = em.createQuery(cq).getResultList();
        em.close();
        return userList;
    }


    public Collection<Price> getPriceHistory(int id) {
        EntityManager em = getEm();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery(Price.class);
        cq.from(Price.class);
        List priceList = em.createQuery(cq).getResultList();
        Query query = null;
        if (!priceList.isEmpty()) {
            for (Object price : priceList) {
                if (id == ((Price) price).getPriceId()) {
                    query = em.createQuery("FROM price  WHERE id =" + id);
                    List<Price> prices = (List<Price>) query.getResultList();
                    em.close();
                    Collections.sort(prices, new Comparator<Price>() {
                        public int compare(Price o1, Price o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });
                    return prices;
                }
            }
        }
        return null;
    }

    public void addPrices(Collection<Price> priceCollection) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        for (Price price : priceCollection)
            em.persist(price);
        em.getTransaction().commit();
        em.close();
    }

    public void addPrice(Price price) {
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

    public boolean updatePrice(Price price) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        Query query = em
                .createQuery("UPDATE PRICE price SET price.price = :price "
                        + "WHERE price.id= :id and price.date = :date");
        query.setParameter("price", price.getPrice());
        query.setParameter("id", price.getPriceId());
        query.setParameter("date", price.getDate());
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean updateUser(User user) {
        EntityManager em = getEm();
        em.getTransaction().begin();
        em.merge(user);
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
        Collection oldProduct = getProduct();
        productList.removeAll(oldProduct);
        if (productList.isEmpty()) {
            addUsers(userIdList);
            EntityManager em = getEm();
            em.getTransaction().begin();
            for (Product product : productList)
                em.persist(product);
            em.getTransaction().commit();
            em.close();
        }
    }

    public void addUsers(Set<Integer> userIdList) {
        Collection oldUser = getUser();
        userIdList.removeAll(oldUser);
        if (userIdList.isEmpty()) {
            EntityManager em = getEm();
            em.getTransaction().begin();

            for (Integer userId : userIdList) {
                User user = new User(userId);

                em.persist(user);
            }
            if (userIdList.isEmpty())
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