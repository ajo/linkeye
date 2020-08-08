package sh.ajo.linkeye.linkeye.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.ajo.linkeye.linkeye.model.Authority;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority getByAuthority(String authority);

    Optional<Authority> findByAuthority(String authority);

}
