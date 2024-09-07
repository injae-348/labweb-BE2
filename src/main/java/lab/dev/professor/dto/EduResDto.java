package lab.dev.professor.dto;

import lab.dev.professor.domain.Education;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record EduResDto(
        String experienceYear,
        String degreeSummary,
        String degreeDetailKR,
        String degreeDetailEN
) {

        public static EduResDto of(Education education) {
            return EduResDto.builder()
                    .experienceYear(education.getExperienceYear())
                    .degreeSummary(education.getDegreeSummary())
                    .degreeDetailKR(education.getDegreeDetailKR())
                    .degreeDetailEN(education.getDegreeDetailEN())
                    .build();
        }

        public static List<EduResDto> of(List<Education> educations) {
            return educations.stream()
                    .map(EduResDto::of)
                    .collect(Collectors.toList());
        }
}
