package ru.jm.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.jm.crud.model.User;
import ru.jm.crud.model.UserRole;
import ru.jm.crud.service.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/")
public class MainController {
    final UserService service;

    @Autowired
    public MainController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping("welcome")
    public String welcome(Principal principal, Model model) {
        User user = new User();
        user.setFirstname(principal.getName());
        model.addAttribute("user", user);
        return "welcome";
    }

    @GetMapping("user")
    public String user(Principal principal, Model model) {
        User user = service.getByUsername(principal.getName());
        Set<UserRole> roles = service.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user";
    }
}
