package lab.dev.professor.dto;

import lab.dev.file.domain.UploadFile;
import lab.dev.professor.domain.Professor;
import lombok.Builder;

@Builder
public record ProfUpdateResDto(
        Long id,
        String name,
        String major,
        String university,
        String mobilePhone,
        String email,
        String officePhone,
        String officeLocationKR,
        String officeLocationEN,
        UploadFile imageFiles
) {

    public static ProfUpdateResDto of(
            Professor professor
    ) {
        return ProfUpdateResDto.builder()
                .id(professor.getId())
                .name(professor.getName())
                .major(professor.getMajor())
                .university(professor.getUniversity())
                .mobilePhone(professor.getMobilePhone())
                .email(professor.getEmail())
                .officePhone(professor.getOfficePhone())
                .officeLocationKR(professor.getOfficeLocationKR())
                .officeLocationEN(professor.getOfficeLocationEN())
                .imageFiles(professor.getImageFiles())
                .build();
    }
}
