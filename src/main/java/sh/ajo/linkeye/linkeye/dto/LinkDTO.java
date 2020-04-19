package sh.ajo.linkeye.linkeye.dto;

import org.hibernate.validator.constraints.URL;
import sh.ajo.linkeye.linkeye.model.Link;

import javax.validation.constraints.NotEmpty;

public class LinkDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String path;

    @URL
    private String destination;

    private boolean active;

    public LinkDTO() {
    }

    public LinkDTO(Link link) {
        this.setName(link.getName());
        this.setPath(link.getSourcePath());
        this.setDestination(link.getDestination());
        this.setActive(link.isActive());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
