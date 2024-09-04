package lab.dev.file.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @NotNull(message = "원본 파일 이름을 입력해주세요.")
    private String originalFilename;  // 원본 파일 이름

    @NotNull
    private String storedFilename;    // 저장된 파일 이름

    public UploadFile(String originalFilename, String storedFilename) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
    }
}
