package lab.dev.professor.exception;

import lab.dev.common.exception.BusinessException;

public class ResearchPageNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new ResearchPageNotFoundException();

    private ResearchPageNotFoundException() {
        super(ProfErrorCode.RESEARCH_PAGE_NOT_FOUND);
    }
}
