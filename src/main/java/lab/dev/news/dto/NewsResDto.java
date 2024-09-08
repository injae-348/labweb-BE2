package lab.dev.news.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lab.dev.file.domain.UploadFile;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NewsResDto(
        Long id,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime date,
        String activity,
        String content,
        List<String> imageUrls
) {
}
