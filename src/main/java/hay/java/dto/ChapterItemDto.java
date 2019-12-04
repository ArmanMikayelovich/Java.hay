package hay.java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChapterItemDto {
    @Min(value = 0, message = "Id is empty.")
    private Long id;

    @NotEmpty(message = "Headline is empty.")
    private String headline;

    @Min(value = 0, message = "Chapter id is empty.")
    private Long chapterId;
}
