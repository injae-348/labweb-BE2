package lab.dev.news.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record NewsReqDto(
        @NotNull(message = "날짜를 입력해주세요.")
        LocalDateTime date,
        @NotNull(message = "활동을 입력해주세요.")
        String activity,
        @NotNull(message = "내용을 입력해주세요.")
        String content,
        List<MultipartFile> imageFiles
) {
}
