package com.cao.shoppingApp.DAO;

import com.cao.shoppingApp.config.HibernateConfigUtil;
import com.cao.shoppingApp.domain.entity.Product;
import com.cao.shoppingApp.exception.NotEnoughInventoryException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductDAO {

    public void createNewProduct(String name, String description, double price, int stock) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    public List<Product> getAllProduct() {
        List<Product> result = null;

        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.greaterThan(root.get("stock"), 0));

            result = session.createQuery(criteriaQuery).getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }

        return result;
    }

    public Product getProductById(Integer id) throws ZeroOrManyException {
        List<Product> result = null;
        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

            result = session.createQuery(criteriaQuery).getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }

        if (result.size() == 0 || result.size() > 1) {
            throw new ZeroOrManyException("The Product id return either 0 or more than 1 result.");
        }

        return result.get(0);
    }

    public void updatePrice(Integer product_id, double price) {
        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "UPDATE Product p SET p.price=:pPrice WHERE p.id=:pId";
            Query query = session.createQuery(qryString);
            query.setParameter("pPrice", price);
            query.setParameter("pId", product_id);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    public void updateStock(Integer product_id, Integer stock) {
        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "UPDATE Product p SET p.stock=:pStock WHERE p.id=:pId";
            Query query = session.createQuery(qryString);
            query.setParameter("pStock", stock);
            query.setParameter("pId", product_id);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    public int getStockByProduct(Integer product_id) throws NotEnoughInventoryException {
        Session session = null;
        int stock = -1;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "SELECT P.stock FROM Product P WHERE P.id=:pId";
            Query query = session.createQuery(qryString);
            query.setParameter("pId", product_id);
            stock = (int) query.list().get(0);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }

        if (stock != -1) {
            return stock;
        } else {
            throw new NotEnoughInventoryException("No enough stock");
        }
    }

}
