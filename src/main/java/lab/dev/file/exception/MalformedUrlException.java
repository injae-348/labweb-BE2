package lab.dev.file.exception;

import lab.dev.common.exception.BusinessException;

public class MalformedUrlException extends BusinessException {

    public static BusinessException EXCEPTION = new MalformedUrlException();

    private MalformedUrlException() {
        super(FileErrorCode.MALFORMED_URL);
    }
}
