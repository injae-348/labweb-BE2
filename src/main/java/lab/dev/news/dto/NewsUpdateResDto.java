package lab.dev.news.dto;

import lab.dev.file.domain.UploadFile;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NewsUpdateResDto(
        Long id,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime date,
        String activity,
        String content,
        List<UploadFile> imageFiles
) {
}
