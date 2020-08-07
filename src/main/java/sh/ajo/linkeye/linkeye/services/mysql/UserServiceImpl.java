package sh.ajo.linkeye.linkeye.services.mysql;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;
import sh.ajo.linkeye.linkeye.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<User> pagedResult = userRepository.findAll(paging);

        return pagedResult.toList();
    }

    @Override
    public User getOneByUsername(String username) {
        return userRepository.getOneByUsername(username);
    }

    @Override
    public User getOneById(long userId) {
        return userRepository.getOneById(userId);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> findAllById(Iterable<Long> iterable) {
        return userRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
        userRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public <S extends User> S save(S s) {
        return userRepository.save(s);
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> iterable) {
        return userRepository.saveAll(iterable);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return userRepository.existsById(aLong);
    }

    @Override
    public void flush() {
        userRepository.flush();
    }

    @Override
    public <S extends User> S saveAndFlush(S s) {
        return userRepository.saveAndFlush(s);
    }

    @Override
    public void deleteInBatch(Iterable<User> iterable) {
        userRepository.deleteInBatch(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }

    @Override
    public User getOne(Long aLong) {
        return userRepository.getOne(aLong);
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return userRepository.findOne(example);
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return userRepository.findAll(example);
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return userRepository.findAll(example, sort);
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return userRepository.findAll(example, pageable);
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return userRepository.count();
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return userRepository.exists(example);
    }
}
