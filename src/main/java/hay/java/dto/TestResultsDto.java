package hay.java.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TestResultsDto {
    private Long id;
    private Long userId;
    private Long chapterId;
    private Date startTime;
    private Date endTime;
    private Double successPercent;
}
