package lab.dev.professor.domain;

import jakarta.persistence.*;
import lab.dev.common.BaseEntity;
import lab.dev.professor.dto.ResearReqDto;
import lombok.*;

@Entity
@Getter
@Table(name = "RESEARCH_PAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchPage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String url;

    @Builder
    public ResearchPage(
            @NonNull String title,
            @NonNull String url
    ) {
        this.title = title;
        this.url = url;
    }

    public void update(
            ResearReqDto resReqDto
    ) {
        this.title = resReqDto.title();
        this.url = resReqDto.url();
    }

}
