package lab.dev.professor.domain;

import jakarta.persistence.*;
import lab.dev.common.BaseEntity;
import lab.dev.professor.dto.EduReqDto;
import lombok.*;

@Entity
@Getter
@Table(name = "EDUCATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String experienceYear;

    @NonNull
    private String degreeSummary;

    @NonNull
    private String degreeDetailKR;

    @NonNull
    private String degreeDetailEN;

    @Builder
    public Education(
            @NonNull String experienceYear,
            @NonNull String degreeSummary,
            @NonNull String degreeDetailKR,
            @NonNull String degreeDetailEN
    ) {
        this.experienceYear = experienceYear;
        this.degreeSummary = degreeSummary;
        this.degreeDetailKR = degreeDetailKR;
        this.degreeDetailEN = degreeDetailEN;
    }

    public void update(
            EduReqDto eduReqDto
    ) {
        this.experienceYear = eduReqDto.experienceYear();
        this.degreeSummary = eduReqDto.degreeSummary();
        this.degreeDetailKR = eduReqDto.degreeDetailKR();
        this.degreeDetailEN = eduReqDto.degreeDetailEN();
    }

}
