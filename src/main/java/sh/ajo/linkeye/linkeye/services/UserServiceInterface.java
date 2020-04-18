package sh.ajo.linkeye.linkeye.services;

import sh.ajo.linkeye.linkeye.model.user.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> findPaginated(int pageNo, int pageSize);


}
