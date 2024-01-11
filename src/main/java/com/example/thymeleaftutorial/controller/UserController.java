package com.example.thymeleaftutorial.controller;

import com.example.thymeleaftutorial.dto.UserRequest;
import com.example.thymeleaftutorial.entity.User;
import com.example.thymeleaftutorial.helper.UserHelper;
import com.example.thymeleaftutorial.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final int PAGE_SIZE = 5;

    @GetMapping("")
    public String viewHome(Model model, HttpSession session) {
        return findPaginated(1, null, model, session);
    }

    @GetMapping("/page/{pageNum}")
    public String findPaginated(
            @PathVariable(name = "pageNum") int pageNum,
            @RequestParam(name = "searchEmail", required = false) String searchEmail,
            Model model,
            HttpSession session
    ) {
        Page<User> page = userService.findPaginated(pageNum, PAGE_SIZE, searchEmail);
        List<User> users = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);

        session.setAttribute("searchEmail", searchEmail);

        return "index";
    }

    @GetMapping("/recover/{id}")
    public String recoverUser(
            @PathVariable("id") Long id,
            @RequestParam(name = "page") int currentPage,
            HttpSession session
    ) {
        String searchEmail = UserHelper.getSessionAttributeAsString(session, "searchEmail");
        userService.recoverUserById(id);
        return UserHelper.redirectToUserPageWithSearchByEmail(currentPage, searchEmail);
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, @RequestParam(name = "page") int currentPage, HttpSession session) {
        String searchEmail = UserHelper.getSessionAttributeAsString(session, "searchEmail");
        userService.deleteUserById(id);
        return UserHelper.redirectToUserPageWithSearchByEmail(currentPage, searchEmail);
    }

    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        return "add-user";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("newUser") UserRequest newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-user";
        }
        userService.addUSer(newUser);
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
    public String updateUser(@PathVariable(name = "id") Long id, @ModelAttribute("updateUser") @Valid UserRequest updateUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-user";
        }
        userService.updateUser(updateUser, id);
        return "redirect:/user";
    }


}
