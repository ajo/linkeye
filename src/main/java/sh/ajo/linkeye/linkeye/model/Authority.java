package sh.ajo.linkeye.linkeye.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority() {
    }

    public void setAuthorityLevel(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
