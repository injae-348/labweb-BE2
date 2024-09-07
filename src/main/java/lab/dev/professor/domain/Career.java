package lab.dev.professor.domain;

import jakarta.persistence.*;
import lab.dev.common.BaseEntity;
import lab.dev.professor.dto.CareerReqDto;
import lombok.*;

@Entity
@Getter
@Table(name = "CAREER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String experienceYear;

    @NonNull
    private String career;

    @NonNull
    private String careerDetailKR;

    @NonNull
    private String careerDetailEN;

    @Builder
    public Career(
            @NonNull String experienceYear,
            @NonNull String career,
            @NonNull String careerDetailKR,
            @NonNull String careerDetailEN
    ) {
        this.experienceYear = experienceYear;
        this.career = career;
        this.careerDetailKR = careerDetailKR;
        this.careerDetailEN = careerDetailEN;
    }

    public void update(
            CareerReqDto careerReqDto
    ) {
        this.experienceYear = careerReqDto.experienceYear();
        this.career = careerReqDto.career();
        this.careerDetailKR = careerReqDto.careerDetailKR();
        this.careerDetailEN = careerReqDto.careerDetailEN();
    }
}
