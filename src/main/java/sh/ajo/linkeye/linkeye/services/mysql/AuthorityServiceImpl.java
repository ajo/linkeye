package sh.ajo.linkeye.linkeye.services.mysql;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.repositories.AuthorityRepository;
import sh.ajo.linkeye.linkeye.services.AuthorityService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }


    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public List<Authority> findAll(Sort sort) {
        return authorityRepository.findAll(sort);
    }

    @Override
    public Page<Authority> findAll(Pageable pageable) {
        return authorityRepository.findAll(pageable);
    }

    @Override
    public List<Authority> findAllById(Iterable<Long> iterable) {
        return authorityRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return authorityRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        authorityRepository.deleteById(aLong);
    }

    @Override
    public void delete(Authority user) {
        authorityRepository.delete(user);
    }

    @Override
    public void deleteAll(Iterable<? extends Authority> iterable) {
        authorityRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        authorityRepository.deleteAll();
    }

    @Override
    public <S extends Authority> S save(S s) {
        return authorityRepository.save(s);
    }

    @Override
    public <S extends Authority> List<S> saveAll(Iterable<S> iterable) {
        return authorityRepository.saveAll(iterable);
    }

    @Override
    public Optional<Authority> findById(Long aLong) {
        return authorityRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return authorityRepository.existsById(aLong);
    }

    @Override
    public void flush() {
        authorityRepository.flush();
    }

    @Override
    public <S extends Authority> S saveAndFlush(S s) {
        return authorityRepository.saveAndFlush(s);
    }

    @Override
    public void deleteInBatch(Iterable<Authority> iterable) {
        authorityRepository.deleteInBatch(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        authorityRepository.deleteAllInBatch();
    }

    @Override
    public Authority getOne(Long aLong) {
        return authorityRepository.getOne(aLong);
    }

    @Override
    public <S extends Authority> Optional<S> findOne(Example<S> example) {
        return authorityRepository.findOne(example);
    }

    @Override
    public <S extends Authority> List<S> findAll(Example<S> example) {
        return authorityRepository.findAll(example);
    }

    @Override
    public <S extends Authority> List<S> findAll(Example<S> example, Sort sort) {
        return authorityRepository.findAll(example, sort);
    }

    @Override
    public <S extends Authority> Page<S> findAll(Example<S> example, Pageable pageable) {
        return authorityRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Authority> long count(Example<S> example) {
        return authorityRepository.count();
    }

    @Override
    public <S extends Authority> boolean exists(Example<S> example) {
        return authorityRepository.exists(example);
    }

    @Override
    public Authority getByAuthority(String authority) {
        return authorityRepository.getByAuthority(authority);
    }
}
