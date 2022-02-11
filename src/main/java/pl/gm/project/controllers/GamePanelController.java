package pl.gm.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.gm.project.model.Hero;
import pl.gm.project.model.Item;
import pl.gm.project.model.Mob;
import pl.gm.project.model.User;
import pl.gm.project.service.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GamePanelController {

    private final HeroService heroService;
    private final ItemService itemService;
    private final UserService userService;
    private final MobService mobService;
    private final FightService fightService;

    @ModelAttribute("user")
    public CurrentUserDetails getUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails;
    }

    @ModelAttribute("userHero")
    public Hero getUserHero(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return userService.get(currentUserDetails.getUser().getId()).getHero();
    }

    @ModelAttribute("inventory")
    public List<Item> getHeroItems(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        if (userService.get(currentUserDetails.getUser().getId()).getHero() == null) {
            return new ArrayList<>();
        } else {
            return userService.get(currentUserDetails.getUser().getId()).getHero().getItems();
        }
    }

    @ModelAttribute("shopItems")
    public List<Item> getShopItems() {
        return itemService.listAll();
    }

    @ModelAttribute("localDateTime")
    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }

    @GetMapping()
    public String gamePanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        User user = userService.get(currentUserDetails.getUser().getId());
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        if (!heroService.isUserHaveHero(hero)) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        return "gamepanel";
    }

    @GetMapping("/temple")
    public String getTemplePanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        if (!heroService.isUserHaveHero(hero)) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        return "templepanel";
    }

    @GetMapping("/temple/raise_random_stat")
    public String raiseRandomStat(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        heroService.ifPossibleToRaiseStatictic(hero);
        heroService.levelUpifPossible(hero);
        if (!heroService.ifPossibleToRaiseStatictic(hero)) {
            model.addAttribute("goldAmountNotEnought", "Not enough gold.");
        }
        return "templepanel";
    }

    @GetMapping("/heal")
    public String healHeroByPotion(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        if (hero.getHealth() == hero.getMaximumHealth() || hero.getHpPotions() <= 0) {
            model.addAttribute("fullHealthOrNotEnoughPotions", "You have full health or 0 health potions.");
        } else {
            heroService.healByPotion(hero);
            return "gamepanel";
        }
        return "gamepanel";
    }

    @GetMapping("/arena")
    public String getArenaPanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        if (!heroService.isUserHaveHero(hero)) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        model.addAttribute("mobs", mobService.listAll());
        return "arenapanel";
    }

    @GetMapping("/arena/fight/{id}")
    public String getArenaPanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model, @PathVariable long id) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        Mob mob = mobService.get(id);
        model.addAttribute("mob", mob);
        return "fightpanel";
    }

    @GetMapping("/arena/attack/{mobName}/{minAttack}/{maxAttack}/{health}/{level}")
    public String attackMob(@PathVariable String mobName, @PathVariable int minAttack, @PathVariable int maxAttack, @PathVariable int health, @PathVariable int level, @AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        Mob mob = new Mob(null, mobName, minAttack, maxAttack, health, level);
        return fightService.oneTurnHeroVsMob(hero,mob,model);

    }

    @GetMapping("/shop")
    public String getShopPanel(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        if (!heroService.isUserHaveHero(hero)) {
            model.addAttribute("hero", new Hero());
            return "usercontent/hero/hero_create";
        }
        model.addAttribute("shopItems", itemService.listAll());
        return "shoppanel";
    }

    @GetMapping("/shop/item_buy/{id}")
    public String buyItemFromShop(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model, @PathVariable long id) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        List<Item> items = hero.getItems();
        Item itemToBuy = itemService.get(id);
        for (Item item : hero.getItems()) {
            if (item.getName().equals(itemToBuy.getName())) {
                model.addAttribute("haveItemAlready", "You already have this item.");
                return "shoppanel";
            }
        }
        if (hero.getGold() >= itemToBuy.getPrice()) {
            items.add(itemToBuy);
            heroService.buyItemAndUpdate(hero, itemToBuy);
            model.addAttribute("successBuy", "You have bought item successfully.");
            return "gamepanel";
        } else {
            model.addAttribute("goldAmountNotEnought", "Not enough gold.");
            return "shoppanel";
        }
    }

    @GetMapping("/shop/item_sell/{name}")
    public String sellItemFromInventory(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, @PathVariable String name, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        itemService.removeItemFromInventory(hero.getItems(), name);
        heroService.sellItemAndUpdate(hero, itemService.getByName(name));
        model.addAttribute("successSell", "You have sold an item.");
        return "gamepanel";
    }

    @GetMapping("/shop/hp_potion_buy")
    public String buyHpPotion(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
        Hero hero = userService.get(currentUserDetails.getUser().getId()).getHero();
        return heroService.buyHpPotion(hero,model);
    }
}
