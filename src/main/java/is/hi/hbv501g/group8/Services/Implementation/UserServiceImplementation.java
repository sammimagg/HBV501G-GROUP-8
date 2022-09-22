package is.hi.hbv501g.group8.Services.Implementation;

import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findBySSN(String ssn){
        return userRepository.findBySSN(ssn);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
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
            if(foundUser.getPassword().equals(user.getPassword())){
                return foundUser;
            }
        }
        return null;
    }
}
