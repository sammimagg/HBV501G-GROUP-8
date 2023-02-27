package is.hi.hbv501g.group8.Services.Implementation;

import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User findBySSN(String ssn){
        return userRepository.findBySSN(ssn);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode((user.getPassword()))); // @Deprecated v/ extends User
        return userRepository.save(user);
    }

    public String secureIt(String password){
        return passwordEncoder.encode(password);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /*
    *   Myndi vilja bæta við hashi
     */
    @Override
    public User login(User user) {
        User foundUser = findByUsername(user.getUsername());
        if(foundUser != null) {
            /*
            if(foundUser.getPassword().equals(user.getPassword())){
                return foundUser;
            }
            https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html#%3Cinit%3E()
             */
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())){
                return foundUser;
            }
        }
        return null;
    }
}
