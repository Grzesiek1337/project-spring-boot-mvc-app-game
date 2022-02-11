package pl.gm.project.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.gm.project.model.User;
import pl.gm.project.repository.UserRepository;


@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Iterable<User> listAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public String checkAndSaveUser(User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "usercontent/user/user_create";
        }
        if (userRepository.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("msg", "User already exist.");
            return "usercontent/user/user_create";
        }
        model.addAttribute("successCreatingAccountMsg", "U have successfully created your account! Log and play!");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "index";
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public User get(long id) {
        return userRepository.findById(id).get();
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }


}
