package sh.ajo.linkeye.linkeye.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sh.ajo.linkeye.linkeye.model.AuthorityLevel;
import sh.ajo.linkeye.linkeye.model.User;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NotEmpty
    private String username;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private boolean admin;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.enabled = user.getEnabled();
        this.password = user.getPassword();

        this.admin = user.getAuthoritiesList().contains(AuthorityLevel.ADMIN.getAuthorityLevel());

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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}

