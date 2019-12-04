package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(String s) {
        super(s);
    }
}
