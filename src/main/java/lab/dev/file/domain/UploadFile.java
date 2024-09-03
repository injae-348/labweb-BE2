package lab.dev.file.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "UPLOAD_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "원본 파일 이름을 입력해주세요.")
    private String originalFilename;  // 원본 파일 이름

    @NotNull
    private String storedFilename;    // 저장된 파일 이름

    public UploadFile(String originalFilename, String storedFilename) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
    }
}
