package sh.ajo.linkeye.linkeye.services;

import sh.ajo.linkeye.linkeye.model.Click;
import sh.ajo.linkeye.linkeye.model.Link;

import java.util.List;

public interface ClickServiceInterface {

    List<Click> findPaginated(int pageNo, int pageSize, Link link);


}
