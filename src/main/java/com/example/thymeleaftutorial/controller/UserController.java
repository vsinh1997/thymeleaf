package com.example.thymeleaftutorial.controller;

import com.example.thymeleaftutorial.dto.UserRequest;
import com.example.thymeleaftutorial.entity.User;
import com.example.thymeleaftutorial.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public String viewHome(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/page/{pageNum}")
    public String findPaginated(
            @PathVariable(name = "pageNum") int pageNum,
            Model model
    ) {
        int pageSize = 5;

        Page<User> page = userService.findPaginated(pageNum, pageSize);
        List<User> users = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/search")
    public String searchUserByEmail(
            @ModelAttribute(name = "searchEmail") String searchEmail,
            Model model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return viewHome(model);
        }

        List<User> users = userService.findByUserEmail(searchEmail);

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems", 1);
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/recover/{id}")
    public String recoverUser(@PathVariable("id") Long id, Model model) {
        userService.recoverUserById(id);
        return "redirect:/user";
//        return viewHome(model);
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteUserById(id);
//        return viewHome(model);
        return "redirect:/user";
    }

    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        return "add-user";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("newUser") UserRequest newUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-user";
        }
        userService.addUSer(newUser);
//        return viewHome(model);
        return "redirect:/user";
    }

    @GetMapping("/updateUser/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userOptional = userService.findUserById(id);
        User updateUser = userOptional.get();
        model.addAttribute("updateUser", updateUser);
        model.addAttribute("email", updateUser.getEmail());
        return "update-user";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(
            @PathVariable(name = "id") Long id,
            @ModelAttribute("updateUser") @Valid UserRequest updateUser,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "update-user";
        }
        userService.updateUser(updateUser, id);
//        return viewHome(model);
        return "redirect:/user";
    }


}
