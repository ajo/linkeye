package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.model.user.User;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<User> pagedResult = userRepository.findAll(paging);

        return pagedResult.toList();
    }
}
