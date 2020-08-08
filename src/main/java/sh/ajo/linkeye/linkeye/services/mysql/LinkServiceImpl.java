package sh.ajo.linkeye.linkeye.services.mysql;

import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.dto.LinkDTO;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.repositories.LinkRepository;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;
import sh.ajo.linkeye.linkeye.services.LinkService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class LinkServiceImpl implements LinkService {

    private final Environment environment;
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public LinkServiceImpl(Environment environment, LinkRepository linkRepository, UserRepository userRepository) {
        this.environment = environment;
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Link> findPaginated(int pageNo, int pageSize) {

        User requester = userRepository.getOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Link> pagedResult = linkRepository.findAllByOwner(paging, requester);

        return pagedResult.toList();
    }

    @Override
    public Link findFirstByOwnerOrderByCreatedDesc(User user) {
        return linkRepository.findFirstByOwnerOrderByCreatedDesc(user);
    }

    @Override
    public Link findLinkBySourcePath(String sourcePath) {
        return linkRepository.findLinkBySourcePath(sourcePath);
    }

    @Override
    public Link getLinkById(Long linkId) {
        return linkRepository.getLinkById(linkId);
    }

    @Override
    public long countByOwner(User user) {
        return linkRepository.countByOwner(user);
    }

    @Override
    public Link getTopLinkByClicksForOwner(User owner) {
        return linkRepository.getTopLinkByClicksForOwner(owner);
    }

    @Override
    public Link createLink(LinkDTO linkDTO) {

        // Force all links to a "safe" location in demo mode
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(Predicate.isEqual("demo"))) {
            linkDTO.setDestination("https://github.com/ajo/linkeye");
        }

        return linkRepository.save(new Link(linkDTO));
    }

    @Override
    public Link updateLink(Link link, LinkDTO linkDTO) {

        link.setActive(linkDTO.isActive());
        link.setName(linkDTO.getName());
        link.setSourcePath(linkDTO.getPath());

        // Force all links to a "safe" location in demo mode
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(Predicate.isEqual("demo"))) {
            linkDTO.setDestination("https://github.com/ajo/linkeye");
        } else {
            link.setDestination(linkDTO.getDestination());
        }

        return linkRepository.save(link);
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> findAll(Sort sort) {
        return linkRepository.findAll(sort);
    }

    @Override
    public Page<Link> findAll(Pageable pageable) {
        return linkRepository.findAll(pageable);
    }

    @Override
    public List<Link> findAllById(Iterable<Long> iterable) {
        return linkRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return linkRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        linkRepository.deleteById(aLong);
    }

    @Override
    public void delete(Link click) {
        linkRepository.delete(click);
    }

    @Override
    public void deleteAll(Iterable<? extends Link> iterable) {
        linkRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        linkRepository.deleteAll();
    }

    @Override
    public <S extends Link> S save(S s) {
        return linkRepository.save(s);
    }

    @Override
    public <S extends Link> List<S> saveAll(Iterable<S> iterable) {
        return linkRepository.saveAll(iterable);
    }

    @Override
    public Optional<Link> findById(Long aLong) {
        return linkRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return linkRepository.existsById(aLong);
    }

    @Override
    public void flush() {
        linkRepository.flush();
    }

    @Override
    public <S extends Link> S saveAndFlush(S s) {
        return linkRepository.saveAndFlush(s);
    }

    @Override
    public void deleteInBatch(Iterable<Link> iterable) {
        linkRepository.deleteInBatch(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        linkRepository.deleteAllInBatch();
    }

    @Override
    public Link getOne(Long aLong) {
        return linkRepository.getOne(aLong);
    }

    @Override
    public <S extends Link> Optional<S> findOne(Example<S> example) {
        return linkRepository.findOne(example);
    }

    @Override
    public <S extends Link> List<S> findAll(Example<S> example) {
        return linkRepository.findAll(example);
    }

    @Override
    public <S extends Link> List<S> findAll(Example<S> example, Sort sort) {
        return linkRepository.findAll(example, sort);
    }

    @Override
    public <S extends Link> Page<S> findAll(Example<S> example, Pageable pageable) {
        return linkRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Link> long count(Example<S> example) {
        return linkRepository.count();
    }

    @Override
    public <S extends Link> boolean exists(Example<S> example) {
        return linkRepository.exists(example);
    }

}
