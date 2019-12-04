package hay.java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChapterDto {
    @Min(value = 0, message = "id should be greater than 0")
    private Long chapterId;
    @NotEmpty(message = "Chapter name is empty")
    private String chapterName;

    @Min(value = 0, message = "Topic id shoudl be greater than 0")
    private Long topicId;


}
