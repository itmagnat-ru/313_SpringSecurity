package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;


@Controller
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String helloRage() {
        return "hello";
    }

    @GetMapping("/home")
    public String homePage(Model model, Principal principal){
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "homepage";
    }
    @GetMapping("/admin/user")
    public String printWelcome(ModelMap model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/admin/registration")
    public String regPage(@ModelAttribute("user") User user) {
        return "/registration";
    }

    @PostMapping("/admin/registration")
    public String registrationUser(@ModelAttribute("user") @Validated User user) {
        userService.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping(value = "/admin/{id}/update")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("role", userService.getListRole());
        model.addAttribute("user", userService.findById(id));
        return "update";
    }

    @PatchMapping(value = "/admin/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/user";
    }

    @DeleteMapping(value = "/admin/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/admin/user";
    }
}
