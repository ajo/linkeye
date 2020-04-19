package sh.ajo.linkeye.linkeye.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.ajo.linkeye.linkeye.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getOneByUsername(String username);

}
