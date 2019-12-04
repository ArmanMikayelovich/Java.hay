package hay.java.service.util.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TestResultsNotFoundException extends RuntimeException {
    public TestResultsNotFoundException(String s) {
        super(s);
    }
}
