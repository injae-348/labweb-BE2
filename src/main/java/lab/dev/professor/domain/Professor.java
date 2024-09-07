package lab.dev.professor.domain;

import jakarta.persistence.*;
import lab.dev.common.BaseEntity;
import lab.dev.file.domain.UploadFile;
import lab.dev.professor.dto.ProfReqDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "PROFESSOR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String major; // 전자컴퓨터공학

    @NonNull
    private String university; // ex) 한국기술교육대학교

    @NonNull
    private String mobilePhone;

    @NonNull
    private String email;

    @NonNull
    private String officePhone;

    @NonNull
    private String officeLocationKR; // ex) 2호관 3층 301호

    @NonNull
    private String officeLocationEN; // ex) 2nd Building 3rd Floor Room 301

    @Embedded
    private UploadFile imageFiles;

    @Builder
    public Professor(
            @NonNull String name,
            @NonNull String major,
            @NonNull String university,
            @NonNull String mobilePhone,
            @NonNull String email,
            @NonNull String officePhone,
            @NonNull String officeLocationKR,
            @NonNull String officeLocationEN,
            @NonNull UploadFile imageFiles
    ) {
        this.name = name;
        this.major = major;
        this.university = university;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.officePhone = officePhone;
        this.officeLocationKR = officeLocationKR;
        this.officeLocationEN = officeLocationEN;
        this.imageFiles = imageFiles;
    }

    public void update(
            ProfReqDto profReqDto,
            @NonNull UploadFile imageFiles
    ) {
        this.name = profReqDto.name();
        this.major = profReqDto.major();
        this.university = profReqDto.university();
        this.mobilePhone = profReqDto.mobilePhone();
        this.email = profReqDto.email();
        this.officePhone = profReqDto.officePhone();
        this.officeLocationKR = profReqDto.officeLocationKR();
        this.officeLocationEN = profReqDto.officeLocationEN();
        this.imageFiles = imageFiles;
    }
}
