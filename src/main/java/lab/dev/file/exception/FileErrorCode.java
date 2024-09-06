package lab.dev.file.exception;

import lab.dev.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode {

    FILE_NOT_FOUND("F001", HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED("F002", HttpStatus.FORBIDDEN ,"파일 업로드에 실패했습니다."),
    FILE_DELETE_FAILED("F003", HttpStatus.FORBIDDEN,"파일 삭제에 실패했습니다."),
    FILE_SIZE_EXCEEDED("F004", HttpStatus.FORBIDDEN,"파일 크기가 초과되었습니다."),
    FILE_TYPE_NOT_ALLOWED("F005", HttpStatus.FORBIDDEN,"허용되지 않는 파일 형식입니다.")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
