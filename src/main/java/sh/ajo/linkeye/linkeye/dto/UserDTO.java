package sh.ajo.linkeye.linkeye.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sh.ajo.linkeye.linkeye.model.Authority;
import sh.ajo.linkeye.linkeye.model.AuthorityLevel;
import sh.ajo.linkeye.linkeye.model.User;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;

public class UserDTO {

    @NotEmpty
    private String username;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private boolean admin;

    private Collection<Authority> authorities = new ArrayList<>();

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.enabled = user.getEnabled();
        this.password = user.getPassword();
        this.authorities = (Collection<Authority>) user.getAuthorities();
        this.admin = authorities.stream().anyMatch(authority -> authority.getAuthority().equals(AuthorityLevel.ADMIN.getAuthorityLevel()));
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }
}

