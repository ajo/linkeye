package sh.ajo.linkeye.linkeye.services.mysql;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.model.Click;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.repositories.ClickRepository;
import sh.ajo.linkeye.linkeye.services.ClickService;

import java.util.List;
import java.util.Optional;

@Service
public class ClickServiceImpl implements ClickService {

    private final ClickRepository clickRepository;

    public ClickServiceImpl(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    @Override
    public List<Click> findPaginated(int pageNo, int pageSize, Link link) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Click> pagedResult = clickRepository.findAllByLink(paging, link);

        return pagedResult.toList();
    }

    @Override
    public List<Click> findAll() {
        return clickRepository.findAll();
    }

    @Override
    public List<Click> findAll(Sort sort) {
        return clickRepository.findAll(sort);
    }

    @Override
    public Page<Click> findAll(Pageable pageable) {
        return clickRepository.findAll(pageable);
    }

    @Override
    public List<Click> findAllById(Iterable<Long> iterable) {
        return clickRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return clickRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        clickRepository.deleteById(aLong);
    }

    @Override
    public void delete(Click click) {
        clickRepository.delete(click);
    }

    @Override
    public void deleteAll(Iterable<? extends Click> iterable) {
        clickRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        clickRepository.deleteAll();
    }

    @Override
    public <S extends Click> S save(S s) {
        return clickRepository.save(s);
    }

    @Override
    public <S extends Click> List<S> saveAll(Iterable<S> iterable) {
        return clickRepository.saveAll(iterable);
    }

    @Override
    public Optional<Click> findById(Long aLong) {
        return clickRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return clickRepository.existsById(aLong);
    }

    @Override
    public void flush() {
        clickRepository.flush();
    }

    @Override
    public <S extends Click> S saveAndFlush(S s) {
        return clickRepository.saveAndFlush(s);
    }

    @Override
    public void deleteInBatch(Iterable<Click> iterable) {
        clickRepository.deleteInBatch(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        clickRepository.deleteAllInBatch();
    }

    @Override
    public Click getOne(Long aLong) {
        return clickRepository.getOne(aLong);
    }

    @Override
    public <S extends Click> Optional<S> findOne(Example<S> example) {
        return clickRepository.findOne(example);
    }

    @Override
    public <S extends Click> List<S> findAll(Example<S> example) {
        return clickRepository.findAll(example);
    }

    @Override
    public <S extends Click> List<S> findAll(Example<S> example, Sort sort) {
        return clickRepository.findAll(example, sort);
    }

    @Override
    public <S extends Click> Page<S> findAll(Example<S> example, Pageable pageable) {
        return clickRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Click> long count(Example<S> example) {
        return clickRepository.count();
    }

    @Override
    public <S extends Click> boolean exists(Example<S> example) {
        return clickRepository.exists(example);
    }

    @Override
    public Page<Click> findAllByLink(Pageable paging, Link link) {
        return clickRepository.findAllByLink(paging, link);
    }

    @Override
    public long countClicksByLinkId(Long linkId) {
        return clickRepository.countClicksByLinkId(linkId);
    }

    @Override
    public long countByLink(Link link) {
        return clickRepository.countByLink(link);
    }

    @Override
    public long countTotalClicksForUserSinceDate(User user, int daysAgo) {
        return clickRepository.countTotalClicksForUserSinceDate(user, daysAgo);
    }
}
