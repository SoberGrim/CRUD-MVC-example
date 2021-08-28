package ru.jm.crud.dao;

import ru.jm.crud.model.UserRole;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;


@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public String add(UserRole userRole) {
        entityManager.persist(userRole);
        return "ok";
    }
    @Override
    public UserRole getRole(Integer id) {
        TypedQuery<UserRole> result = entityManager.createQuery("FROM UserRole WHERE id=:id", UserRole.class).setParameter("id", id);
        return result.getResultList().isEmpty() ? null : result.getSingleResult();
    }

    @Override
    public UserRole getRole(String userRole) {
        TypedQuery<UserRole> result = entityManager.createQuery("FROM UserRole WHERE role=:userRole", UserRole.class).setParameter("userRole", userRole);
        return result.getResultList().isEmpty() ? null : result.getSingleResult();
    }


    @Override
    public ArrayList<UserRole> getRoles() {
        ArrayList<UserRole> list = new ArrayList<>(entityManager.createQuery("FROM UserRole ORDER BY id", UserRole.class).getResultList());
        System.out.println("Role list received from sql: " + list);
        return list;
    }
}
