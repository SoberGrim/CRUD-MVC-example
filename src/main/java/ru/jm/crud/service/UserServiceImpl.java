package ru.jm.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jm.crud.dao.UserDao;
import ru.jm.crud.model.User;
import ru.jm.crud.model.UserRole;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    final UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public String add(User user) {
        return dao.add(user);
    }

    @Override
    public String add(String username, String password, String firstName, String lastName, String age, String email, UserRole... roles) {
        return dao.add(username, password, firstName, lastName, age, email, roles);
    }

    @Override
    public User getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return dao.getByUsername(username);
    }

    @Override
    public List<User> getByName(String firstname) {
        return dao.getByName(firstname);
    }

    @Override
    public List<User> getByName(String firstname, String lastname) {
        return dao.getByName(firstname, lastname);
    }

    @Override
    public List<User> getByLastName(String lastname) {
        return dao.getByLastName(lastname);
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Override
    public List<User> getByAge(String age) {
        return dao.getByAge(age);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public List<User> getFilterUsers() {
        return dao.getFilterUsers();
    }

    @Override
    public boolean isFilterSet() {
        return dao.isFilterSet();
    }

    @Override
    public void setFilter(User user, boolean strict) {
        dao.setFilter(user, strict);
    }

    @Override
    public void removeFilter() {
        dao.removeFilter();
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
}
