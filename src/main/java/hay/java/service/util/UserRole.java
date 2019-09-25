package hay.java.service.util;

public enum UserRole {
    USER, MODERATOR, ADMIN;

    @Override
    public String toString() {
        switch (this) {
            case ADMIN:
                return "ADMIN";
            case USER:
                return "USER";
            case MODERATOR:
                return "MODERATOR";
            default:
                return null;
        }
    }
}
