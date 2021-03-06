package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import sh.ajo.linkeye.linkeye.model.Click;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.model.User;

import java.util.List;

@NoRepositoryBean
public interface ClickService extends JpaService<Click, Long> {

    List<Click> findPaginated(int pageNo, int pageSize, Link link);

    Page<Click> findAllByLink(Pageable paging, Link link);

    long countClicksByLinkId(Long linkId);

    long countByLink(Link link);

    long countTotalClicksForUserSinceDate(User user, int daysAgo);


}
