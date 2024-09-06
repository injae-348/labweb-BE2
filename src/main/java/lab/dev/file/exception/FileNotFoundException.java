package lab.dev.file.exception;

import lab.dev.common.exception.BusinessException;

public class FileNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new FileNotFoundException();

    private FileNotFoundException() {
        super(FileErrorCode.FILE_NOT_FOUND);
    }
}
