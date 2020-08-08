package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.repository.NoRepositoryBean;
import sh.ajo.linkeye.linkeye.model.Authority;

import java.util.Optional;

@NoRepositoryBean
public interface AuthorityService extends JpaService<Authority, Long> {

    Authority getByAuthority(String authority);

    Optional<Authority> findByAuthority(String authority);

}
