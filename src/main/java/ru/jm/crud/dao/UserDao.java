package ru.jm.crud.dao;

import ru.jm.crud.model.User;
import ru.jm.crud.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public interface UserDao {

    String add(User user);
    String add(UserRole userRole);
    UserRole getRole(Integer id);
    ArrayList<UserRole> getRoles();
    String add(String username, String password, String firstName, String lastName, String age, String email, UserRole... roles);
    User getById(Long id);
    User getByUsername(String username);
    List<User> getByName(String firstname);
    List<User> getByName(String firstname, String lastname);
    List<User> getByLastName(String lastname);
    User getByEmail(String email);
    List<User> getByAge(String age);

    List<User> getAllUsers();
    List<User> getFilterUsers();
    void setFilter(User user, boolean strict);
    boolean isFilterSet();


    void removeFilter();
    void update(User user);
    void delete(Long id);

}
