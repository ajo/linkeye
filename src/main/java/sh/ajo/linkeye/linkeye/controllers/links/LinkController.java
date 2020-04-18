package sh.ajo.linkeye.linkeye.controllers.links;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sh.ajo.linkeye.linkeye.model.analytics.Click;
import sh.ajo.linkeye.linkeye.model.web.Link;
import sh.ajo.linkeye.linkeye.repositories.ClickRepository;
import sh.ajo.linkeye.linkeye.repositories.LinkRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LinkController {

    private final LinkRepository linkRepository;
    private final ClickRepository clickRepository;

    public LinkController(LinkRepository linkRepository, ClickRepository clickRepository) {
        this.linkRepository = linkRepository;
        this.clickRepository = clickRepository;
    }

    @GetMapping("/link/{link}")
    public String root(@PathVariable String link, HttpServletRequest request) {

        Link target = linkRepository.findLinkBySourcePath(link);

        if (target != null && target.isActive()) {

            // Valid Link
            Click click = new Click();
            click.setLink(target);
            click.setIp(request.getRemoteHost());
            click.setUserAgent(request.getHeader("User-Agent"));

            clickRepository.save(click);

            return "redirect:" + target.getDestination();

        } else {

            // Invalid Link
            return "error";
        }
    }

}
