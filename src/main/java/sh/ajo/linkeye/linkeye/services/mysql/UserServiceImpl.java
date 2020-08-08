package sh.ajo.linkeye.linkeye.services.mysql;

import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.dto.UserDTO;
import sh.ajo.linkeye.linkeye.exception.DuplicateUsernameException;
import sh.ajo.linkeye.linkeye.exception.InvalidPasswordException;
import sh.ajo.linkeye.linkeye.model.AuthorityLevel;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;
import sh.ajo.linkeye.linkeye.services.AuthorityService;
import sh.ajo.linkeye.linkeye.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final Environment environment;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthorityService authorityService, Environment environment, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
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
    public User updateUser(User user, UserDTO userDTO) throws DuplicateUsernameException {

        // Do not change the linkeye demo admin account if running in demo mode
        if (!(user.getUsername().equalsIgnoreCase("linkeye") && Arrays.stream(environment.getActiveProfiles()).anyMatch(Predicate.isEqual("demo")))){

            // Do not update to taken usernames
            if ((!userDTO.getUsername().equals(user.getUsername()) && userRepository.findOneByUsername(userDTO.getUsername()).isPresent())){
                throw new DuplicateUsernameException();
            } else {
                user.setUsername(userDTO.getUsername());
            }

            // Do not change the password if blank
            if (!userDTO.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            user.setEnabled(userDTO.isEnabled());

            if (userDTO.isAdmin()){
                userDTO.getAuthorities().add(authorityService.getByAuthority(AuthorityLevel.ADMIN.getAuthorityLevel()));
            } else {
                userDTO.getAuthorities().add(authorityService.getByAuthority(AuthorityLevel.USER.getAuthorityLevel()));
            }

            user.setAuthorities(new ArrayList<>(userDTO.getAuthorities()));

            return userRepository.saveAndFlush(user);
        }

        // Dont touch the database at all if it's the demo admin is the target
        return user;
    }

    @Override
    public User createUser(UserDTO userDTO) throws DuplicateUsernameException, InvalidPasswordException {

        // Do not create users with taken usernames
        if (userRepository.findOneByUsername(userDTO.getUsername()).isPresent()){
            throw new DuplicateUsernameException();
        }

        if (userDTO.isAdmin()){
            userDTO.getAuthorities().add(authorityService.getByAuthority(AuthorityLevel.ADMIN.getAuthorityLevel()));
        } else {
            userDTO.getAuthorities().add(authorityService.getByAuthority(AuthorityLevel.USER.getAuthorityLevel()));
        }

        // Dont allow blank passwords, encrypt valid ones.
        if (!userDTO.getPassword().isBlank()) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            throw new InvalidPasswordException();
        }

        return userRepository.saveAndFlush(new User(userDTO));
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
        delete(userRepository.getOneById(aLong));
    }

    @Override
    public void delete(User user) {

        // Do not change the linkeye demo admin account if running in demo mode
        if (!(user.getUsername().equalsIgnoreCase("linkeye") && Arrays.stream(environment.getActiveProfiles()).anyMatch(Predicate.isEqual("demo")))){
            userRepository.delete(user);
        }
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
