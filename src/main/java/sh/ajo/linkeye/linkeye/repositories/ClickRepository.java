package sh.ajo.linkeye.linkeye.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sh.ajo.linkeye.linkeye.model.Click;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.model.User;

@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {

    Page<Click> findAllByLink(Pageable paging, Link link);

    long countClicksByLinkId(Long linkId);
    long countByLink(Link link);

    @Query(value =
            "SELECT COUNT(*) " +
            "FROM click " +
            "INNER JOIN link " +
            "ON click.link_id = link.id " +
            "WHERE link.owner_id = :#{#owner.id} AND click.date >= NOW() - INTERVAL :#{#daysAgo} DAY", nativeQuery = true)
    long countTotalClicksForUserSinceDate(@Param("owner") User user, @Param("daysAgo") int daysAgo);
}
