package lab.dev.file.exception;

import lab.dev.common.exception.BusinessException;

public class FileDownloadFailedException extends BusinessException {

    public static BusinessException EXCEPTION = new FileDownloadFailedException();

    private FileDownloadFailedException() {
        super(FileErrorCode.FILE_DOWNLOAD_FAILED);
    }
}
