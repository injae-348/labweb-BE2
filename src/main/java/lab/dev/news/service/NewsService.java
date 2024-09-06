package lab.dev.news.service;

import lab.dev.common.exception.BusinessException;
import lab.dev.common.exception.CommonErrorCode;
import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.domain.News;
import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.exception.NewsNotFoundException;
import lab.dev.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileService fileService;

    @Transactional(readOnly = true) // 단일 뉴스 찾기
    public News findNewsByIdOrThrow(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> NewsNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true) // 뉴스 목록 조회
    public List<NewsResDto> getNewsList() {
        return newsRepository.findAll()
                .stream()
                .map(this::toResDto)
                .toList(); // 불변 리스트
    }

    @Transactional(readOnly = true) // 단일 뉴스 조회
    public NewsResDto getNews(Long newsId) {
        News news = findNewsByIdOrThrow(newsId);
        return toResDto(news);
    }

    @Transactional // 뉴스 생성
    public Long createNews(NewsReqDto newsReqDto) {
        validateNewsReqDto(newsReqDto);
        List<UploadFile> uploadFiles = fileService.storeFiles(newsReqDto.imageFiles());
        News news = toEntity(newsReqDto, uploadFiles);
        return newsRepository.save(news).getId();
    }

    @Transactional // 뉴스 삭제
    public Long deleteNews(Long newsId) {
        News news = findNewsByIdOrThrow(newsId);
        List<String> storedFilenames = getStoredFilenames(news);
        storedFilenames.forEach(fileService::deleteFile);
        newsRepository.delete(news);
        return newsId;
    }

    @Transactional // 뉴스 수정
    public NewsResDto updateNews(Long newsId, NewsReqDto newsReqDto) {
        validateNewsReqDto(newsReqDto);
        News news = findNewsByIdOrThrow(newsId);

        List<String> existingFilenames = getStoredFilenames(news);
        existingFilenames.forEach(fileService::deleteFile);

        List<UploadFile> uploadFiles = fileService.storeFiles(newsReqDto.imageFiles());
        news.update(newsReqDto.date(), newsReqDto.activity(), newsReqDto.content(), uploadFiles);
        return toResDto(news);
    }

    private static List<String> getStoredFilenames(News news) {
        List<String> storedFilenames = news.getImageFiles().stream()
                .map(UploadFile::getStoredFilename)
                .toList();
        return storedFilenames;
    }

    // NewsReqDto -> News
    private static News toEntity(NewsReqDto newsReqDto, List<UploadFile> uploadFiles) {
         return News.builder()
                .date(newsReqDto.date())
                .activity(newsReqDto.activity())
                .content(newsReqDto.content())
                .imageFiles(uploadFiles != null ? uploadFiles : new ArrayList<>())
                .build();
    }

    // News -> NewsResDto
    private NewsResDto toResDto(News news) {
        return NewsResDto.builder()
                .id(news.getId())
                .date(news.getDate())
                .activity(news.getActivity())
                .content(news.getContent())
                .imageUrls(news.getImageFiles().stream()
                        .map(uploadFile -> "/api/files/" + uploadFile.getStoredFilename())
                        .toList()
                )
                .build();
    }

    // NewsReqDto 필드 검증
    private void validateNewsReqDto(NewsReqDto newsReqDto) {
        if (Objects.isNull(newsReqDto.date()) ||
                Objects.isNull(newsReqDto.activity()) ||
                Objects.isNull(newsReqDto.content())) {
            throw new BusinessException(CommonErrorCode.INVALID_PARAMETER);
        }
    }
}
