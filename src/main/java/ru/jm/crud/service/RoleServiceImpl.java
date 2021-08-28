package ru.jm.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jm.crud.dao.UserDao;
import ru.jm.crud.model.UserRole;

import java.util.ArrayList;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    final UserDao dao;

    @Autowired
    public RoleServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public String add(UserRole userRole) {
        return dao.add(userRole);
    }

    @Override
    public UserRole getRole(Integer id) {
        return dao.getRole(id);
    }

    @Override
    public ArrayList<UserRole> getRoles() {
        return dao.getRoles();
    }

}
