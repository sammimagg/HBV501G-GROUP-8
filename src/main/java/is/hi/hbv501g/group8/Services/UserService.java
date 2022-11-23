package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.User;

import java.util.List;

public interface UserService {
    User save(User user);
    void delete(User user);
    List<User> findAll();
    User findByUsername(String username);
    User findBySSN(String ssn);
    User login(User user);

    String secureIt(String password);
}
