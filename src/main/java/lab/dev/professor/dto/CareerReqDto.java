package lab.dev.professor.dto;

import jakarta.validation.constraints.NotNull;
import lab.dev.professor.domain.Career;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CareerReqDto(
        @NotNull(message = "경력 년수를 입력해주세요. ex) 2010년 ~ 현재")
        String experienceYear,
        @NotNull(message = "직위를 입력해주세요. ex) 교수, 조교수, 박사후연구원")
        String career,
        @NotNull(message = "경력 상세 내용을 한국어로 입력해주세요. ex) OO대학교 교수")
        String careerDetailKR,
        @NotNull(message = "경력 상세 내용을 영어로 입력해주세요. ex) Professor at OO University")
        String careerDetailEN
) {

    public Career toEntity() {
        return Career.builder()
                .experienceYear(experienceYear)
                .career(career)
                .careerDetailKR(careerDetailKR)
                .careerDetailEN(careerDetailEN)
                .build();
    }

    public static List<Career> toEntities(List<CareerReqDto> careerReqDtos) {
        return careerReqDtos.stream()
                .map(CareerReqDto::toEntity)
                .collect(Collectors.toList());
    }
}
