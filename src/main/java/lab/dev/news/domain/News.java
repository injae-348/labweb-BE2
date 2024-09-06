package lab.dev.news.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lab.dev.common.BaseEntity;
import lab.dev.file.domain.UploadFile;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "NEWS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalDateTime date;

    @NonNull
    private String activity;

    @NonNull
    private String content;

    @ElementCollection
    @CollectionTable(name = "NEWS_IMAGES", joinColumns = @JoinColumn(name = "news_id"))
    private List<UploadFile> imageFiles;

    @Builder
    public News(
            @NonNull LocalDateTime date,
            @NonNull String activity,
            @NonNull String content,
            List<UploadFile> imageFiles) {
        this.date = date;
        this.activity = activity;
        this.content = content;
        this.imageFiles = imageFiles;
    }

    public void update(
            @NonNull LocalDateTime date,
            @NonNull String activity,
            @NonNull String content,
            List<UploadFile> imageFiles) {
        this.date = date;
        this.activity = activity;
        this.content = content;
        this.imageFiles = imageFiles;
    }

}
