package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.repository.NoRepositoryBean;
import sh.ajo.linkeye.linkeye.dto.LinkDTO;
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

    long countByOwner(User user);

    Link getTopLinkByClicksForOwner(User owner);

    Link createLink(LinkDTO linkDTO);

    Link updateLink(Link link, LinkDTO linkDTO);
}
