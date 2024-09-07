package lab.dev.professor.exception;

import lab.dev.common.exception.BusinessException;

public class CareerNotFoundException extends BusinessException {

    public static final CareerNotFoundException EXCEPTION = new CareerNotFoundException();

    private CareerNotFoundException() {
        super(ProfErrorCode.CAREER_NOT_FOUND);
    }
}
