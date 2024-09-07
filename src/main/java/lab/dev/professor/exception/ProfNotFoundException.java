package lab.dev.professor.exception;

import lab.dev.common.exception.BusinessException;

public class ProfNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new ProfNotFoundException();

    private ProfNotFoundException() {
        super(ProfErrorCode.PROFESSOR_NOT_FOUND);
    }
}
