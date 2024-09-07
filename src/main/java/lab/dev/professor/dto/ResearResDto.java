package lab.dev.professor.dto;

import lab.dev.professor.domain.ResearchPage;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ResearResDto(
        String title,
        String url
) {

    public static ResearResDto of(ResearchPage researchPage) {
        return ResearResDto.builder()
                .title(researchPage.getTitle())
                .url(researchPage.getUrl())
                .build();
    }

    public static List<ResearResDto> of(List<ResearchPage> researchPages) {
        return researchPages.stream()
                .map(ResearResDto::of)
                .collect(Collectors.toList());
    }
}
