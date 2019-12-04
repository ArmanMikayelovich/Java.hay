package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChapterItemNotFoundException extends RuntimeException {
    public ChapterItemNotFoundException(String s) {
        super(s);
    }
}
