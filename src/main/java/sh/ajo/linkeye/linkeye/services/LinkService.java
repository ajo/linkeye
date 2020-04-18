package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.model.user.User;
import sh.ajo.linkeye.linkeye.model.web.Link;
import sh.ajo.linkeye.linkeye.repositories.LinkRepository;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;

import java.util.List;

@Service
public class LinkService implements LinkServiceInterface {

    private final LinkRepository linkRepository;

    private final UserRepository userRepository;

    public LinkService(LinkRepository linkRepository, UserRepository userRepository) {
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
}
