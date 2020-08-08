package sh.ajo.linkeye.linkeye.controllers.external;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sh.ajo.linkeye.linkeye.model.Click;
import sh.ajo.linkeye.linkeye.model.Link;
import sh.ajo.linkeye.linkeye.services.ClickService;
import sh.ajo.linkeye.linkeye.services.LinkService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LinkController {

    private final LinkService linkService;
    private final ClickService clickService;

    public LinkController(LinkService linkService, ClickService clickService) {
        this.linkService = linkService;
        this.clickService = clickService;
    }

    @GetMapping("/link/{link}")
    public String root(@PathVariable String link, HttpServletRequest request) {

        Link target = linkService.findLinkBySourcePath(link);

        if (target != null && target.isActive()) {

            // Valid Link
            Click click = new Click();
            click.setLink(target);
            click.setIp(request.getRemoteHost());
            click.setUserAgent(request.getHeader("User-Agent"));

            clickService.save(click);

            return "redirect:" + target.getDestination();

        } else {

            // Invalid Link
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
