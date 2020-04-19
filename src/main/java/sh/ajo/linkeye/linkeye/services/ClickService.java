package sh.ajo.linkeye.linkeye.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sh.ajo.linkeye.linkeye.model.Click;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.repositories.ClickRepository;

import java.util.List;

@Service
public class ClickService implements ClickServiceInterface {

    private final ClickRepository clickRepository;

    public ClickService(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    @Override
    public List<Click> findPaginated(int pageNo, int pageSize, Link link) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Click> pagedResult = clickRepository.findAllByLink(paging, link);

        return pagedResult.toList();
    }
}
