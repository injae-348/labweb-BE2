package lab.dev.professor.exception;

import lab.dev.common.exception.BusinessException;

public class EducationNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new EducationNotFoundException();

    private EducationNotFoundException() {
        super(ProfErrorCode.EDUCATION_NOT_FOUND);
    }
}
