package sh.ajo.linkeye.linkeye.model;

public enum AuthorityLevel {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");


    private final String authorityLevel;

    AuthorityLevel(String authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

    public String getAuthorityLevel() {
        return authorityLevel;
    }

}
