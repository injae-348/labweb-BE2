package lab.dev.news.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lab.dev.news.domain.News;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NewsReqDto(

        @NotNull(message = "날짜를 입력해주세요.")
        LocalDateTime date,

        @NotNull(message = "활동을 입력해주세요.")
        @Size(max = 30, message = "활동은 30자 이내로 입력해주세요.")
        String activity,

        @NotNull(message = "내용을 입력해주세요.")
        @Size(max = 1000, message = "내용은 1000자 이내로 입력해주세요.")
        String content,

        List<MultipartFile> imageFiles
) {

}
