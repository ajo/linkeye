package sh.ajo.linkeye.linkeye.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.model.User;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    //boolean existsByAuthorityLevelAndUser(String authorityLevel, User user);
    //Authority getByUser(User user);

}
