package sh.ajo.linkeye.linkeye.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sh.ajo.linkeye.linkeye.dto.LinkDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @NotNull
    private String name;

    @NotNull
    private String destination;

    @NotNull
    @Column(unique = true)
    private String sourcePath;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;

    private boolean active = true;

    public Link(LinkDTO linkDTO) {
        this.owner = linkDTO.getOwner();
        this.name = linkDTO.getName();
        this.destination = linkDTO.getDestination();
        this.sourcePath = linkDTO.getPath();
        this.active = linkDTO.isActive();
    }

    public Link() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
