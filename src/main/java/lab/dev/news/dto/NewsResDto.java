package lab.dev.news.dto;

import lab.dev.file.domain.UploadFile;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NewsResDto(
        Long id,
        LocalDateTime date,
        String activity,
        String content,
        List<String> imageUrls
) {
}
