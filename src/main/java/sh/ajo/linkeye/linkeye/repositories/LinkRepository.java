package sh.ajo.linkeye.linkeye.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sh.ajo.linkeye.linkeye.model.user.User;
import sh.ajo.linkeye.linkeye.model.web.Link;

@Repository
public interface LinkRepository extends PagingAndSortingRepository<Link, Long> {

    // Newest
    Link findFirstByOwnerOrderByCreatedDesc(User user);

    Link findLinkBySourcePath(String sourcePath);
    Link getLinkById(Long linkId);

    Page<Link> findAllByOwner(Pageable paging, User user);

    long countByOwner(User user);

    @Query(value =
            "SELECT * " +
            "FROM link " +
            "INNER JOIN click " +
            "ON link.id = click.link_id " +
            "WHERE link.owner_id = :#{#owner.id} " +
            "GROUP BY link_id " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1", nativeQuery = true)
    Link getTopLinkByClicksForOwner(@Param("owner") User owner);

}
