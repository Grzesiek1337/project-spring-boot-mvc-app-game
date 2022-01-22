package pl.gm.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gm.project.model.Hero;
import pl.gm.project.service.CurrentUserDetails;
import pl.gm.project.service.HeroService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/game")
@AllArgsConstructor
public class GamePanelController {

    @Autowired
    private HeroService heroService;


    @ModelAttribute("user")
    public CurrentUserDetails getUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails;
    }

    @ModelAttribute("userHero")
    public CurrentUserDetails getUserHero(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails;
    }

    @ModelAttribute("localDateTime")
    public LocalDateTime getUser() {
        return LocalDateTime.now();
    }

    @GetMapping()
    public String gamePanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        if(!currentUserDetails.getUserHero().isPresent()) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        return "gamepanel";
    }
    @GetMapping("/temple")
    public String getTemplePanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        if(!currentUserDetails.getUserHero().isPresent()) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        return "templepanel";
    }

    @GetMapping("/arena")
    public String getArenaPanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        if(!currentUserDetails.getUserHero().isPresent()) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        return "arenapanel";
    }

    @GetMapping("/shop")
    public String getShopPanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        if(!currentUserDetails.getUserHero().isPresent()) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        return "shoppanel";

    }



}
