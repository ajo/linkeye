package sh.ajo.linkeye.linkeye.controllers.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.repositories.ClickRepository;
import sh.ajo.linkeye.linkeye.repositories.LinkRepository;
import sh.ajo.linkeye.linkeye.repositories.UserRepository;

@Controller
public class DashboardController {

    private final LinkRepository linkRepository;
    private final ClickRepository clickRepository;
    private final UserRepository userRepository;

    public DashboardController(LinkRepository linkRepository, ClickRepository clickRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.clickRepository = clickRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {

        User requester = userRepository.getOneByUsername(authentication.getName());

        model.addAttribute("newestLink", linkRepository.findFirstByOwnerOrderByCreatedDesc(requester));
        model.addAttribute("topLink", linkRepository.getTopLinkByClicksForOwner(requester));
        model.addAttribute("totalDailyClicks", clickRepository.countTotalClicksForUserSinceDate(requester, 1));
        model.addAttribute("totalMonthlyClicks", clickRepository.countTotalClicksForUserSinceDate(requester, 30));

        return "dashboard";
    }

}
