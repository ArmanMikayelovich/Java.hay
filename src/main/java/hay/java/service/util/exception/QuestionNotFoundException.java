package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String s) {
        super(s);
    }
}
