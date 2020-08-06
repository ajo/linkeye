package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.repository.NoRepositoryBean;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.model.User;

import java.util.List;

@NoRepositoryBean
public interface AuthorityService extends JpaService<Authority, Long> {
}
