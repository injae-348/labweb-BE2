package lab.dev.file.exception;

import lab.dev.common.exception.BusinessException;

public class FileUploadFailedException extends BusinessException {

    public static BusinessException EXCEPTION = new FileUploadFailedException();

    private FileUploadFailedException() {
        super(FileErrorCode.FILE_UPLOAD_FAILED);
    }
}
