package lab.dev.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

import java.util.List;

@Getter
public class ErrorResponse {

    private final String code;
    private final int status;
    private final String message;

    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationError> invalidParams;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.status = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }
}
