package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserQuestionAnswersNotFoundException extends RuntimeException {
    public UserQuestionAnswersNotFoundException(String s) {
        super(s);
    }
}
