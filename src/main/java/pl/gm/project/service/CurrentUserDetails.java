package pl.gm.project.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.gm.project.model.Hero;
import pl.gm.project.model.User;


public class CurrentUserDetails implements UserDetails {

    private User user;

    public CurrentUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Optional<Hero> getUserHero() {
        return Optional.ofNullable(user.getHero());
    }

    public String getUserHeroName() {
        return user.getHero().getName();
    }

    public Integer getUserHeroAttack() {
        return user.getHero().getAttack();
    }

    public Integer getUserHeroHealth() {
        return user.getHero().getHealth();
    }

    public Integer getUserHeroLevel() {
        return user.getHero().getLevel();
    }

    public Integer getUserHeroExperience() {
        return user.getHero().getExperience();
    }

    public Integer getUserHeroGold() {
        return user.getHero().getGold();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setHero(Hero hero) {
        user.setHero(hero);
    }
}
