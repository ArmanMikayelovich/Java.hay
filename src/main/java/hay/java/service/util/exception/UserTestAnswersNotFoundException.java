package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserTestAnswersNotFoundException extends RuntimeException
{
    public UserTestAnswersNotFoundException(String s) {
        super(s);
    }
}
