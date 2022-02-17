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
    public User getUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails) {
        return userService.get(currentUserDetails.getUser().getId());
    }

    @ModelAttribute("localDateTime")
    public LocalDateTime getUser() {
        return LocalDateTime.now();
    }

    @GetMapping()
    public String adminPanel(Model model) {
        return "admincontent/admin_panel";
    }

    @GetMapping("/hero/list")
    public String viewListOfHeroes(Model model) {
        List<Hero> heroes = heroService.listAll();
        model.addAttribute("heroList", heroes);
        return "admincontent/hero/hero_list";
    }

    @GetMapping("/hero/edit/{id}")
    public String showEditHeroFormForAdmin(@PathVariable(name = "id") int id, Model model) {
        Hero hero = heroService.get(id);
        model.addAttribute("hero", hero);
        return "admincontent/hero/hero_edit";
    }

    @PostMapping("/hero/edit")
    public String updateHeroByAdmin(Hero hero) {
        heroService.update(hero);
        return "redirect:/admin";
    }
    @GetMapping("/hero/remove/{id}")
    public String getRemoveFormForHero(@PathVariable int id, Model model) {
        model.addAttribute("heroId", id);
        return "admincontent/hero/hero_delete";
    }

    @GetMapping("/hero/delete/{id}")
    public String removeHeroByAdmin(@PathVariable long id, Model model) {
        heroService.delete(id);
        model.addAttribute("heroId", null);
        return "redirect:/admin/hero/list";
    }

    @GetMapping("/user/list")
    public String viewListOfUsersForAdmin(Model model) {
        Iterable<User> users = userService.listAll();
        model.addAttribute("userList", users);
        return "admincontent/user/user_list";
    }

    @GetMapping("/user/new")
    public String showNewUserFormForAdmin(Model model) {
        model.addAttribute("userNew", new User());
        return "admincontent/user/user_new";
    }

    @PostMapping("/user/new")
    public String saveUserByAdmin(@ModelAttribute("userNew") User user) {
        userService.saveUserByAdmin(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/remove/{id}")
    public String getUserRemoveFormForAdmin(@PathVariable int id, Model model) {
        model.addAttribute("userId", id);
        return "admincontent/user/user_delete";
    }
    @GetMapping("/user/delete/{id}")
    public String removeUserByAdmin(@PathVariable long id, Model model) {
        userService.delete(id);
        model.addAttribute("userId", null);
        return "redirect:/admin/user/list";
    }

    @GetMapping("/item/list")
    public String viewListOfItemsForAdmin(Model model) {
        Iterable<Item> items = itemService.listAll();
        model.addAttribute("itemList", items);
        return "admincontent/item/item_list";
    }

    @GetMapping("/item/new")
    public String showNewItemFormForAdmin(Model model) {
        model.addAttribute("item", new Item());
        return "admincontent/item/item_new";
    }

    @PostMapping("/item/new")
    public String saveItemByAdminToDatabase(@ModelAttribute("item") Item item) {
        itemService.save(item);
        return "redirect:/admin";
    }

    @GetMapping("/mob/list")
    public String viewListOfMobsForAdmin(Model model) {
        List<Mob> mobs = mobService.listAll();
        model.addAttribute("mobList", mobs);
        return "admincontent/mob/mob_list";
    }

    @GetMapping("/mob/new")
    public String showNewMobFormForAdmin(Model model) {
        model.addAttribute("mob", new Mob());
        return "admincontent/mob/mob_new";
    }

    @PostMapping("/mob/new")
    public String saveMobByAdminToDatabase(@ModelAttribute("mob") Mob mob) {
        mobService.save(mob);
        return "redirect:/admin";
    }
}
