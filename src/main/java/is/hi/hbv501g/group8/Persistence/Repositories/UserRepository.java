package is.hi.hbv501g.group8.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.hbv501g.group8.Persistence.Entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User save(User user);
    void delete(User user);
    List<User> findAll();
    User findByUsername(String username);
    User findBySSN(String ssn);

    /*
    @Override
    Optional<User> findById(String ssn);
     */
}
