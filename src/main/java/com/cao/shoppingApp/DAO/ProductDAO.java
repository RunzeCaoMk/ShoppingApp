package com.cao.shoppingApp.DAO;

import com.cao.shoppingApp.config.HibernateConfigUtil;
import com.cao.shoppingApp.domain.Order;
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
public class OrderDAO {

    public void createNewOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setPlacing_time(new Timestamp(System.currentTimeMillis()));
        order.setStatus("Processing");

        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();
            session.save(order);
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

    public List<Order> getAllOrder() {
        List<Order> result = null;

        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);

            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root);

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

    public List<Order> getOrderByUser(User user) {
        List<Order> result = null;

        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);

            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("user"), user));

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

    public Order getOrderById(Integer id) throws ZeroOrManyException {
        List<Order> result = null;
        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);

            Root<Order> root = criteriaQuery.from(Order.class);
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
            throw new ZeroOrManyException("The Order id return either 0 or more than 1 result.");
        }

        return result.get(0);
    }

    public void cancelOrder(Integer order_id) {
        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "UPDATE Order o SET o.status='Canceled' WHERE o.id=:oId";
            Query query = session.createQuery(qryString);
            query.setParameter("oId", order_id);
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

    public void completeOrder(Integer order_id) {
        Session session = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "UPDATE Order o SET o.status='Completed' WHERE o.id=:oId";
            Query query = session.createQuery(qryString);
            query.setParameter("oId", order_id);
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
}
