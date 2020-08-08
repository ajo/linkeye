package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.repository.NoRepositoryBean;
import sh.ajo.linkeye.linkeye.dto.AccountUpdateDTO;
import sh.ajo.linkeye.linkeye.dto.UserDTO;
import sh.ajo.linkeye.linkeye.exception.DuplicateUsernameException;
import sh.ajo.linkeye.linkeye.exception.InvalidPasswordException;
import sh.ajo.linkeye.linkeye.exception.MismatchedPasswordException;
import sh.ajo.linkeye.linkeye.model.User;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserService extends JpaService<User, Long> {

    List<User> findPaginated(int pageNo, int pageSize);

    User getOneByUsername(String username);

    User getOneById(long userId);

    Optional<User> findOneByUsername(String username);

    User updateUser(User user, UserDTO userDTO) throws DuplicateUsernameException;

    User createUser(UserDTO userDTO) throws DuplicateUsernameException, InvalidPasswordException;

    void changePassword(User user, AccountUpdateDTO accountUpdateDTO) throws InvalidPasswordException, MismatchedPasswordException;
}
