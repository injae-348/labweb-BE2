package lab.dev.file.exception;

import lab.dev.common.exception.BusinessException;

public class FileDeleteFailedException extends BusinessException {

    public static BusinessException EXCEPTION = new FileDeleteFailedException();

    private FileDeleteFailedException() {
        super(FileErrorCode.FILE_DELETE_FAILED);
    }
}
