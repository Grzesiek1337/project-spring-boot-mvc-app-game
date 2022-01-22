package pl.gm.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    private HeroService heroService;
    private UserRepository userRepository;

    @GetMapping("/user_create")
    public String showNewUserCreatePageForUser(Model model) {
        model.addAttribute("user", new User());
        return "usercontent/user/user_create";
    }

    @PostMapping("/user_create")

    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "usercontent/user/user_create";
        }
        if (userRepository.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("msg", "User already exists.");
            return "usercontent/user/user_create";
        }
        model.addAttribute("successCreatingAccountMsg", "U have successfully created your account! Log and play!");
        userService.saveNewUser(user);
        return "index";


    }


    @PostMapping("/hero_create")
    public String saveHeroForUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Hero hero) {
        Hero heroForUser = new Hero();
        heroForUser.setName(hero.getName());
        heroForUser.setAttack(5);
        heroForUser.setHealth(100);
        heroForUser.setLevel(0);
        heroForUser.setExperience(0);
        heroForUser.setGold(0);
        heroForUser.setUser(currentUserDetails.getUser());
        currentUserDetails.setHero(heroForUser);
        userService.update(currentUserDetails.getUser());
        return "redirect:/game";
    }


}