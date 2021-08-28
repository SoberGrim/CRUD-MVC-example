package ru.jm.crud.controller;

import ru.jm.crud.model.User;
import ru.jm.crud.model.UserRole;
import ru.jm.crud.service.RoleService;
import ru.jm.crud.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;


@Controller
@RequestMapping("/admin")
public class AdminController {
    final UserService service;
    final RoleService roleService;

    @Autowired
    public AdminController(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", service.getFilterUsers());
        model.addAttribute("isFilterActive", service.isFilterSet());
        return "index";
    }

    @GetMapping("{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", service.getById(id));
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "show";
    }

    @GetMapping("{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = service.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("userOrig", user);
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "edit";
    }


    @PatchMapping("{id}/edit")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam(value = "index", required = false) Integer[] index,
                         @PathVariable("id") Long id,
                         Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        User userOrig = service.getById(id);
        model.addAttribute("userOrig", userOrig);

        if (bindingResult.hasErrors()) {
            System.out.println(user);
            return "edit";
        }

        User editedUser = service.getByUsername(user.getUsername());
        if ((editedUser != null) && (!editedUser.getId().equals(user.getId()))) {
            bindingResult.addError(new FieldError("username", "username", "Username already taken"));
            user.setUsername("");
            return "edit";
        }

        editedUser = service.getByEmail(user.getEmail());
        if ((editedUser != null) && (!editedUser.getId().equals(user.getId()))) {
            bindingResult.addError(new FieldError("email", "email", "User with this email already exists"));
            user.setEmail("");
            return "edit";
        }

        if (index != null) {
            for (Integer i : index) {
                user.addRole(roleService.getRole(i));
            }
        }

        service.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String addNewUser(Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        User user = new User();
        model.addAttribute("user", user);
        return "create";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam(value = "index", required = false) Integer[] index,
                         Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);

        if (bindingResult.hasErrors()) {
            System.out.println(user);
            return "create";
        }

        if (service.getByUsername(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("username", "username", "Username already taken"));
            user.setUsername("");
            return "create";
        }

        if (service.getByEmail(user.getEmail()) != null) {
            bindingResult.addError(new FieldError("email", "email", "User with this email already exists"));
            user.setEmail("");
            return "create";
        }

        if (index != null) {
            for (Integer i : index) {
                user.addRole(roleService.getRole(i));
            }
        }

        service.update(user);

        return "redirect:/admin";
    }

    @GetMapping("filter")
    public String filter(@ModelAttribute("user") User user, Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "filter";
    }

    @GetMapping("search")
    public String search(@ModelAttribute("user") User user, Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "search";
    }

    @PostMapping("filter")
    public String filterPost(@ModelAttribute("user") User user,
                             @RequestParam(value = "index", required = false) Integer[] index) {
        System.out.println("Setting filter");
        if (index != null) {
            for (Integer i : index) {
                user.addRole(roleService.getRole(i));
            }
        }
        service.setFilter(user, true);
        return "redirect:/admin";
    }

    @PostMapping("search")
    public String searchPost(@ModelAttribute("user") User user,
                             @RequestParam(value = "index", required = false) Integer[] index) {
        System.out.println("Setting search filter");
        if (index != null) {
            for (Integer i : index) {
                user.addRole(roleService.getRole(i));
            }
        }
        service.setFilter(user, false);
        return "redirect:/admin";
    }

    @GetMapping("removeFilter")
    public String removeFilter() {
        service.removeFilter();
        System.out.println("Removed filter");
        return "redirect:/admin";
    }

    @GetMapping("/delete={id}")
    public String deleteByGet(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/admin";
    }
}
