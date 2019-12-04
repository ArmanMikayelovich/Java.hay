package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
