package lab.dev.news.service;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.domain.News;
import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileService fileService;


    // Todo: 커스텀 예외 처리 적용
    @Transactional(readOnly = true)
    public News findNewsByIdOrThrow(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 뉴스가 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public NewsResDto getNews(Long newsId) {
        News news = findNewsByIdOrThrow(newsId);
        return toResDto(news);
    }

    // Todo: throws 부분 RuntimeException으로 변경 가능한지
    @Transactional
    public Long createNews(NewsReqDto newsReqDto) throws IOException {

        List<UploadFile> uploadFiles = fileService.storeFiles(newsReqDto.imageFiles());

        News news = toEntity(newsReqDto, uploadFiles);

        return newsRepository.save(news).getId();
    }

    private static News toEntity(NewsReqDto newsReqDto, List<UploadFile> uploadFiles) {
         return News.builder()
                .date(newsReqDto.date())
                .activity(newsReqDto.activity())
                .content(newsReqDto.content())
                .imageFiles(uploadFiles)
                .build();
    }


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

}
