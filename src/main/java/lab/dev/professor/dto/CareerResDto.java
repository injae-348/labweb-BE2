package lab.dev.professor.dto;

import lab.dev.professor.domain.Career;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CareerResDto(
        Long id,
        String experienceYear,
        String career,
        String careerDetailKR,
        String careerDetailEN
) {

    public static CareerResDto of(Career career) {
        return CareerResDto.builder()
                .id(career.getId())
                .experienceYear(career.getExperienceYear())
                .career(career.getCareer())
                .careerDetailKR(career.getCareerDetailKR())
                .careerDetailEN(career.getCareerDetailEN())
                .build();
    }

    public static List<CareerResDto> of(List<Career> careers) {
        return careers.stream()
                .map(CareerResDto::of)
                .collect(Collectors.toList());
    }
}
