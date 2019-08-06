package am.aca.dto;

import am.aca.entity.ChapterItemEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChapterItemDto {

    private int id;
    @NonNull
    private String headline;
    @NonNull
    private int chapterId;

    public ChapterItemDto(ChapterItemEntity item) {
        setId(item.getItemId());
        setHeadline(item.getHeadline());
        setChapterId(item.getChapterEntity().getChapterId());
    }
}
