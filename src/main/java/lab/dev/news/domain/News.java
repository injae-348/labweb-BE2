package lab.dev.news.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lab.dev.common.BaseEntity;
import lab.dev.file.domain.UploadFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private List<UploadFile> imageFiles;

    public News(LocalDateTime date, String activity, String content, List<UploadFile> imageFiles) {
        this.date = date;
        this.activity = activity;
        this.content = content;
        this.imageFiles = imageFiles;
    }
}
