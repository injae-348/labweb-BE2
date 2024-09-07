package lab.dev.professor.dto;

import jakarta.validation.constraints.NotNull;
import lab.dev.professor.domain.ResearchPage;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ResearReqDto(
        @NotNull(message = "페이지 설명을 작성해주세요.")
        String title,
        @NotNull(message = "URL을 작성해주세요.")
        String url
) {

    public ResearchPage toEntity() {
        return ResearchPage.builder()
                .title(title)
                .url(url)
                .build();
    }

    public static List<ResearchPage> toEntities(List<ResearReqDto> researReqDtos) {
        return researReqDtos.stream()
                .map(ResearReqDto::toEntity)
                .collect(Collectors.toList());
    }
}
