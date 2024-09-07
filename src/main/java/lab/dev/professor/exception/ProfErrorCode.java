package lab.dev.professor.exception;

import lab.dev.common.exception.BusinessException;
import lab.dev.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProfErrorCode implements ErrorCode {

    PROFESSOR_NOT_FOUND("P001", HttpStatus.NOT_FOUND, "교수를 찾을 수 없습니다."),
    CAREER_NOT_FOUND("P002", HttpStatus.NOT_FOUND, "Career를 찾을 수 없습니다."),
    EDUCATION_NOT_FOUND("P003", HttpStatus.NOT_FOUND, "Education을 찾을 수 없습니다."),
    RESEARCH_PAGE_NOT_FOUND("P004", HttpStatus.NOT_FOUND, "ResearchPage를 찾을 수 없습니다.")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

}
