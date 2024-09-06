package lab.dev.news.exception;

import lab.dev.common.exception.BusinessException;

public class NewsNotFoundException extends BusinessException {

    public static BusinessException EXCEPTION = new NewsNotFoundException();

    private NewsNotFoundException() {
        super(NewsErrorCode.NEWS_NOT_FOUND);
    }
}
