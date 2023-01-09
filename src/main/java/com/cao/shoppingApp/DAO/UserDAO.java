package com.cao.shoppingApp.DAO;

import com.cao.shoppingApp.domain.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAO extends DAO{

    public UserDAO() { }

    public void createNewUser(String username, String password, String email, boolean is_admin) {

    }

    public List<User> getUserByUsername(String username) {
        List<User> result = null;
        try {
            begin();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

            result = session.createQuery(criteriaQuery).getResultList();
            commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                rollback();
            }
        } finally {
            close();
        }

        return result;
    }

}
