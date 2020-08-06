package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.model.User;

import java.util.List;

@NoRepositoryBean
public interface LinkService extends JpaService<Link, Long> {

    List<Link> findPaginated(int pageNo, int pageSize);

    // Newest
    Link findFirstByOwnerOrderByCreatedDesc(User user);

    Link findLinkBySourcePath(String sourcePath);

    Link getLinkById(Long linkId);

    Page<Link> findAllByOwner(Pageable paging, User user);

    long countByOwner(User user);

    Link getTopLinkByClicksForOwner(User owner);
}
