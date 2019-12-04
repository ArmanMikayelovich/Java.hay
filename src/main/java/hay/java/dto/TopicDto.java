package hay.java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TopicDto {
    @Min(value = 0, message = "Id should be greater than 0")
    private Long topicId;
    @NotEmpty(message = "topic name should be not empty")
    private String topicName;
}
