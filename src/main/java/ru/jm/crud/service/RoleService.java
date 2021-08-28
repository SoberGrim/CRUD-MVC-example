package ru.jm.crud.service;

import ru.jm.crud.model.UserRole;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;


public interface RoleService {

    String add(UserRole userRole);
    UserRole getRole(Integer id);
    ArrayList<UserRole> getRoles();

}