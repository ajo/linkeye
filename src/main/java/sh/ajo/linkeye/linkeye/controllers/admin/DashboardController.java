package sh.ajo.linkeye.linkeye.controllers.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sh.ajo.linkeye.linkeye.model.User;
import sh.ajo.linkeye.linkeye.services.ClickService;
import sh.ajo.linkeye.linkeye.services.LinkService;
import sh.ajo.linkeye.linkeye.services.UserService;

@Controller
public class DashboardController {

    private final LinkService linkService;
    private final ClickService clickService;
    private final UserService userService;

    public DashboardController(LinkService linkRepository, ClickService clickRepository, UserService userRepository) {
        this.linkService = linkRepository;
        this.clickService = clickRepository;
        this.userService = userRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {

        User requester = userService.getOneByUsername(authentication.getName());

        model.addAttribute("newestLink", linkService.findFirstByOwnerOrderByCreatedDesc(requester));
        model.addAttribute("topLink", linkService.getTopLinkByClicksForOwner(requester));
        model.addAttribute("totalDailyClicks", clickService.countTotalClicksForUserSinceDate(requester, 1));
        model.addAttribute("totalMonthlyClicks", clickService.countTotalClicksForUserSinceDate(requester, 30));

        return "user/dashboard";
    }

}
