package pl.gm.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.gm.project.model.Hero;
import pl.gm.project.model.Item;
import pl.gm.project.service.CurrentUserDetails;
import pl.gm.project.service.HeroService;
import pl.gm.project.service.ItemService;
import pl.gm.project.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/game")
@AllArgsConstructor
public class GamePanelController {

    @Autowired
    private HeroService heroService;
    private ItemService itemService;
    private UserService userService;


    @ModelAttribute("user")
    public CurrentUserDetails getUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails;
    }

    @ModelAttribute("userHero")
    public CurrentUserDetails getUserHero(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails;
    }

    @ModelAttribute("heroItems")
    public List<Item> getHeroItemList(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails.getUserHero().get().getItems();
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

    @ModelAttribute("shopItems")
    public List<Item> getShopItemList() {
        return itemService.listAll();
    }

    @GetMapping("/shop")
    public String getShopPanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        if(!currentUserDetails.getUserHero().isPresent()) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        return "shoppanel";
    }

    @GetMapping("/shop/item_buy/{id}")
    public String buyItemFromShop(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model, @PathVariable long id) {
        Hero currentHero = currentUserDetails.getUserHero().get();
        List<Item> currentHeroItems = currentHero.getItems();
        int currentGold = currentUserDetails.getUserHero().get().getGold();
        Item item = itemService.get(id);
        if(item.getPrice() > currentGold) {
            model.addAttribute("goldAmountNotEnought","You dont have enought gold to buy this item.");
            return "shoppanel";
        } else {
            currentHeroItems.add(item);
            currentHero.setItems(currentHeroItems);
            currentHero.setGold(currentGold - item.getPrice());
            currentHero.setAttack(currentHero.getAttack() + item.getIncreaseAttack());
            currentHero.setHealth(currentHero.getHealth() + item.getIncreaseHealth());
            heroService.update(currentHero);
            model.addAttribute("successBuy","You bought an item.");
            return "shoppanel";
        }
    }
}
