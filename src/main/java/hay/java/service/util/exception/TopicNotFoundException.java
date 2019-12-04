package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String s) {
        super(s);
    }
}
