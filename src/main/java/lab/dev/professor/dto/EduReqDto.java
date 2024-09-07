package lab.dev.professor.dto;

import jakarta.validation.constraints.NotNull;
import lab.dev.professor.domain.Education;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record EduReqDto(
        @NotNull(message = "경력 년수를 입력해주세요. ex) 2010년 ~ 2012년")
        String experienceYear,
        @NotNull(message = "학위 요약을 입력해주세요. ex) Ph.D. in OO University")
        String degreeSummary,
        @NotNull(message = "학위 상세 내용을 한국어로 입력해주세요. ex) OO대학교 박사")
        String degreeDetailKR,
        @NotNull(message = "학위 상세 내용을 영어로 입력해주세요. ex) Ph.D. in OO University")
        String degreeDetailEN
) {

    public Education toEntity() {
        return Education.builder()
                .experienceYear(experienceYear)
                .degreeSummary(degreeSummary)
                .degreeDetailKR(degreeDetailKR)
                .degreeDetailEN(degreeDetailEN)
                .build();
    }

    public static List<Education> toEntities(List<EduReqDto> eduReqDtos) {
        return eduReqDtos.stream()
                .map(EduReqDto::toEntity)
                .collect(Collectors.toList());
    }
}
