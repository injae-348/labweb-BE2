package lab.dev.file.exception;

import lab.dev.common.exception.BusinessException;

public class FileSizeExceededException extends BusinessException {

    public static BusinessException EXCEPTION = new FileSizeExceededException();

    private FileSizeExceededException() {
        super(FileErrorCode.FILE_SIZE_EXCEEDED);
    }
}
