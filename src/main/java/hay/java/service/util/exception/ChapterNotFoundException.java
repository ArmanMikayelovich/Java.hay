package hay.java.service.util.exception;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class ChapterNotFoundException extends RuntimeException {
    public ChapterNotFoundException(String s) {
        super(s);
    }
}
