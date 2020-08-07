package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.repository.NoRepositoryBean;
import sh.ajo.linkeye.linkeye.model.User;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserService extends JpaService<User, Long> {

    List<User> findPaginated(int pageNo, int pageSize);

    User getOneByUsername(String username);


    User getOneById(long userId);

    Optional<User> findOneByUsername(String username);
}
