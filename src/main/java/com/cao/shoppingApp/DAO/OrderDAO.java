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

    public Order createNewOrder(User user) {
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

        return order;
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

    public List<String> getRecent3ItemByUser(User user) {
        Session session = null;
        List<String> result = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "SELECT p.name " +
                    "FROM Order o, OrderProduct op, Product p " +
                    "WHERE o.user=:oUserId AND o.id = op.order_id AND op.product_id = p.id " +
                    "ORDER BY o.placing_time";
            Query query = session.createQuery(qryString);
            query.setParameter("oUserId", user);
            query.setMaxResults(3);
            result = query.list();

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

    public List<String> getFrequent3ItemByUser(User user) {
        Session session = null;
        List<String> result = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "SELECT p.name " +
                    "FROM Order o, OrderProduct op, Product p " +
                    "WHERE o.user=:oUserId AND o.id = op.order_id AND op.product_id = p.id " +
                    "GROUP BY p.name " +
                    "ORDER BY sum(op.quantity) DESC";
            Query query = session.createQuery(qryString);
            query.setParameter("oUserId", user);
            query.setMaxResults(3);
            result = query.list();

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

    public List<String> getTop3Product() {
        Session session = null;
        List<String> result = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

            String qryString = "SELECT p.name " +
                    "FROM Order o, OrderProduct op, Product p " +
                    "WHERE o.id = op.order_id AND op.product_id = p.id " +
                    "GROUP BY p.name " +
                    "ORDER BY sum(op.quantity) DESC";
            Query query = session.createQuery(qryString);
            query.setMaxResults(3);
            result = query.list();

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

    public List<String> getTop3User() {
        Session session = null;
        List<String> result = null;
        try {
            session = HibernateConfigUtil.openSession();
            session.beginTransaction();

//            String qryString = "SELECT u.username " +
//                    "FROM Order o, OrderProduct op, user u " +
//                    "WHERE o.id = op.order_id AND u.id = o.user" +
//                    "GROUP BY u.username " +
//                    "ORDER BY sum(op.quantity * op.purchased_price) DESC";
            String qryString = "SELECT u.username FROM user u ORDER BY u.username";
            Query query = session.createQuery(qryString);
            query.setMaxResults(3);
            result = query.list();

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
