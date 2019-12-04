package hay.java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ClarificationDto {
    @Min(value = 0, message = "Id should be greater than 0.")
    private Long id;
    @NotEmpty(message = "Clarification text is empty.")
    private String text;

    @Min(value = 0, message = "Question id i empty.")
    private Long questionId;

}
