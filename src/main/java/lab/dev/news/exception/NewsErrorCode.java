package lab.dev.news.exception;

import lab.dev.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NewsErrorCode implements ErrorCode {

    NEWS_NOT_FOUND("N001", HttpStatus.NOT_FOUND, "뉴스를 찾을 수 없습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

}
