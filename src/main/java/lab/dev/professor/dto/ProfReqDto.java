package lab.dev.professor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lab.dev.file.domain.UploadFile;
import lab.dev.professor.domain.Career;
import lab.dev.professor.domain.Education;
import lab.dev.professor.domain.Professor;
import lab.dev.professor.domain.ResearchPage;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Builder
public record ProfReqDto(
        @NotNull(message = "이름을 입력해주세요.")
        String name,
        @NotNull(message = "전공을 입력해주세요.")
        String major,
        @NotNull(message = "대학을 입력해주세요.")
        String university,
        @NotNull(message = "휴대폰 번호를 입력해주세요.")
        String mobilePhone,
        @Email(message = "이메일 형식을 지켜주세요.")
        String email,
        @NotNull(message = "사무실 전화번호를 입력해주세요.")
        String officePhone,
        @NotNull(message = "위치를 한국어로 입력해주세요.")
        String officeLocationKR,
        @NotNull(message = "위치를 영어로 입력해주세요.")
        String officeLocationEN,
        @NotNull(message = "이미지 파일을 첨부해주세요.")
        MultipartFile imageFiles
) {

        public Professor toEntity(UploadFile uploadFile) {
                return Professor.builder()
                        .name(name)
                        .major(major)
                        .university(university)
                        .mobilePhone(mobilePhone)
                        .email(email)
                        .officePhone(officePhone)
                        .officeLocationKR(officeLocationKR)
                        .officeLocationEN(officeLocationEN)
                        .imageFiles(uploadFile)
                        .build();
        }

}
