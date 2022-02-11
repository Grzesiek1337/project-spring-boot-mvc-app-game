package pl.gm.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pl.gm.project.model.Hero;
import pl.gm.project.model.User;
import pl.gm.project.repository.UserRepository;
import pl.gm.project.service.CurrentUserDetails;
import pl.gm.project.service.HeroService;
import pl.gm.project.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HeroService heroService;
    private final UserRepository userRepository;

    @GetMapping("/user_create")
    public String showNewUserCreatePageForUser(Model model) {
        model.addAttribute("user", new User());
        return "usercontent/user/user_create";
    }

    @PostMapping("/user_create")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        return userService.checkAndSaveUser(user,bindingResult,model);
    }

    @PostMapping("/hero_create")
    public String saveHeroForUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails,Hero hero) {
        heroService.createHeroForUser(currentUserDetails,hero);
        return "redirect:/game";
    }
}