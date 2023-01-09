package com.cao.shoppingApp.DAO;

import com.cao.shoppingApp.config.HibernateConfigUtil;
import com.cao.shoppingApp.domain.Order;
import com.cao.shoppingApp.domain.OrderProduct;
import com.cao.shoppingApp.domain.Product;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderProductDAO {

    public void createNewOrderProduct(Order order, Product product, int quantity) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder_id(order);
        orderProduct.setProduct_id(product);
        orderProduct.setQuantity(quantity);
        orderProduct.setPurchased_price(product.getPrice());

        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();
            session.save(orderProduct);
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

    public List<OrderProduct> getAllOrderProductByOrder(Order order) {
        List<OrderProduct> result = null;

        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<OrderProduct> criteriaQuery = criteriaBuilder.createQuery(OrderProduct.class);

            Root<OrderProduct> root = criteriaQuery.from(OrderProduct.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("order_id"), order));

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

}
