package pl.gm.project.controllers.admincontrollers;

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
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPanelController {


    private final HeroService heroService;
    private final UserService userService;
    private final ItemService itemService;
    private final MobService mobService;

    @ModelAttribute("user")
    public CurrentUserDetails getUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return currentUserDetails;
    }

    @ModelAttribute("localDateTime")
    public LocalDateTime getUser() {
        return LocalDateTime.now();
    }

    @GetMapping()
    public String adminPanel(Model model) {
        return "admincontent/admin_panel";
    }

    @GetMapping("/hero_list")
    public String viewListOfHeroes(Model model) {
        List<Hero> heroes = heroService.listAll();
        model.addAttribute("heroList", heroes);
        return "admincontent/hero/hero_list";
    }
    @GetMapping("/hero_new")
    public String showNewHeroPage(Model model) {
        model.addAttribute("hero", new Hero());
        return "admincontent/hero/hero_new";
    }

    @PostMapping("/hero_new")
    public String saveHero(@ModelAttribute("hero") Hero hero) {
        heroService.save(hero);
        return "redirect:/admin";
    }

    @GetMapping("/hero_edit/{id}")
    public String showEditHeroPageForAdmin(@PathVariable(name = "id") int id, Model model) {
        Hero hero = heroService.get(id);
        model.addAttribute("hero", hero);
        return "admincontent/hero/hero_edit";
    }

    @PostMapping("/hero_edit")
    public String updateHeroForAdmin(Hero hero) {
        heroService.update(hero);
        return "redirect:/admin";
    }
    @GetMapping("/hero_remove/{id}")
    public String getRemoveFormForHero(@PathVariable int id, Model model) {
        model.addAttribute("heroId", id);
        return "admincontent/hero/hero_delete";
    }

    @GetMapping("/hero_delete/{id}")
    public String removeHero(@PathVariable long id, Model model) {
        heroService.delete(id);
        model.addAttribute("heroId", null);
        return "redirect:/admin/hero_list";
    }

    @GetMapping("/user_list")
    public String viewListOfUsers(Model model) {
        Iterable<User> users = userService.listAll();
        model.addAttribute("userList", users);
        return "admincontent/user/user_list";
    }

    @GetMapping("/user_new")
    public String showNewUserPage(Model model) {
        model.addAttribute("userNew", new User());
        return "admincontent/user/user_new";
    }

    @PostMapping("/user_new")
    public String saveUser(@ModelAttribute("userNew") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/user_remove/{id}")
    public String getRemoveFormForUSer(@PathVariable int id, Model model) {
        model.addAttribute("userId", id);
        return "admincontent/user/user_delete";
    }
    @GetMapping("/user_delete/{id}")
    public String removeUser(@PathVariable long id, Model model) {
        userService.delete(id);
        model.addAttribute("userId", null);
        return "redirect:/admin/user_list";
    }

    @GetMapping("/item_list")
    public String viewListOfItems(Model model) {
        Iterable<Item> items = itemService.listAll();
        model.addAttribute("itemList", items);
        return "admincontent/item/item_list";
    }

    @GetMapping("/item_new")
    public String showNewItemPage(Model model) {
        model.addAttribute("item", new Item());
        return "admincontent/item/item_new";
    }

    @PostMapping("/item_new")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.save(item);
        return "redirect:/admin";
    }

    @GetMapping("/mob_list")
    public String viewListOfMobs(Model model) {
        List<Mob> mobs = mobService.listAll();
        model.addAttribute("mobList", mobs);
        return "admincontent/mob/mob_list";
    }

    @GetMapping("/mob_new")
    public String showNewMobPage(Model model) {
        model.addAttribute("mob", new Mob());
        return "admincontent/mob/mob_new";
    }

    @PostMapping("/mob_new")
    public String saveMob(@ModelAttribute("mob") Mob mob) {
        mobService.save(mob);
        return "redirect:/admin";
    }
}
