package sh.ajo.linkeye.linkeye.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String authorityLevel;

    public Authority(String authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

    public Authority() {
    }

    public String getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(String authority) {
        this.authorityLevel = authority;
    }

    @Override
    public String getAuthority() {
        return authorityLevel;
    }
}
