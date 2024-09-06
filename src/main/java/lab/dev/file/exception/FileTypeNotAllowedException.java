package lab.dev.file.exception;

import lab.dev.common.exception.BusinessException;

public class FileTypeNotAllowedException extends BusinessException {

        public static BusinessException EXCEPTION = new FileTypeNotAllowedException();

        private FileTypeNotAllowedException() {
            super(FileErrorCode.FILE_TYPE_NOT_ALLOWED);
        }
}
