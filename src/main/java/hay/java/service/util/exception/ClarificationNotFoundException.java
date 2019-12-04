package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClarificationNotFoundException extends RuntimeException {
    public ClarificationNotFoundException(String s) {
        super(s);
    }
}
