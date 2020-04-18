package sh.ajo.linkeye.linkeye.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;

public class EditUserForm {

    @NotEmpty
    private String username;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private boolean admin;

    public EditUserForm(User user, Authority authority) {
        this.username = user.getUsername();
        this.enabled = user.getEnabled();
        this.password = user.getPassword();

        String auth = authority.getAuthorityLevel();

        if (auth.equalsIgnoreCase("USER_ADMIN")){
            this.admin = true;
        } else {
            this.admin = false;
        }

    }

    public EditUserForm() {
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

