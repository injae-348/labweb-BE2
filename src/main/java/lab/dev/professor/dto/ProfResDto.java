package lab.dev.professor.dto;

import jakarta.validation.constraints.Email;
import lab.dev.professor.domain.Career;
import lab.dev.professor.domain.Education;
import lab.dev.professor.domain.Professor;
import lab.dev.professor.domain.ResearchPage;
import lombok.Builder;

import java.util.List;

@Builder
public record ProfResDto(
        Long id,
        String name,
        String major,
        String university,
        String mobilePhone,
        String email,
        String officePhone,
        String officeLocationKR,
        String officeLocationEN,
        String imageUrl
) {

    public static ProfResDto of(
            Professor professor
    ) {
        return ProfResDto.builder()
                .id(professor.getId())
                .name(professor.getName())
                .major(professor.getMajor())
                .university(professor.getUniversity())
                .mobilePhone(professor.getMobilePhone())
                .email(professor.getEmail())
                .officePhone(professor.getOfficePhone())
                .officeLocationKR(professor.getOfficeLocationKR())
                .officeLocationEN(professor.getOfficeLocationEN())
                .imageUrl("/api/files/" + professor.getImageFiles().getStoredFilename())
                .build();
    }
}
