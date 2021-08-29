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
import java.util.List;


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

    @GetMapping("")
    public String showIndexPages(@RequestParam(name = "page", required = false, defaultValue = "1") String strParam,
                                 Model model) {
        int pageNum = strParam.matches("\\d+") ? Integer.parseInt(strParam) : 1;
        model.addAttribute("pageNum", pageNum);
        List<User> users = (pageNum == 1) ? service.getFilterUsers(false) : service.getFilterUsers(true);
        model.addAttribute("users", users);
        model.addAttribute("isFilterActive", service.isFilterSet());
        return "index";
    }

    @GetMapping("{id}")
    public String showUserById(@PathVariable("id") Long id, Model model) {
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
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
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
    public String addNewUserPage(Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        User user = new User();
        model.addAttribute("user", user);
        return "create";
    }


    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
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
    public String filterPage(@ModelAttribute("user") User user, Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "filter";
    }

    @GetMapping("search")
    public String searchPage(@ModelAttribute("user") User user, Model model) {
        ArrayList<UserRole> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "search";
    }

    @PostMapping("filter")
    public String filterApply(@ModelAttribute("user") User user,
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
    public String searchApply(@ModelAttribute("user") User user,
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
    public String deleteUserById(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/admin";
    }
}
