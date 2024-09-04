package lab.dev.file.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @NonNull
    private String originalFilename;  // 원본 파일 이름

    @NonNull
    private String storedFilename;    // 저장된 파일 이름

    public UploadFile(
            @NonNull String originalFilename,
            @NonNull String storedFilename) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
    }
}
