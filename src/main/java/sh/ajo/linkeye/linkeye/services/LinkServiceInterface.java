package sh.ajo.linkeye.linkeye.services;

import sh.ajo.linkeye.linkeye.model.Link;

import java.util.List;

public interface LinkServiceInterface {

    List<Link> findPaginated(int pageNo, int pageSize);


}
