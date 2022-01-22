package pl.gm.project.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public void saveNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        userRepository.save(user);
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
