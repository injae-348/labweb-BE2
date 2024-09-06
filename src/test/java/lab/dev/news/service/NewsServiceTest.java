package lab.dev.news.service;

import lab.dev.file.domain.UploadFile;
import lab.dev.news.domain.News;
import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.repository.NewsRepository;
import lab.dev.file.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private FileService fileService;

    @InjectMocks
    private NewsService newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createNews() throws IOException {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        when(file1.getOriginalFilename()).thenReturn("test1.jpg");
        when(file2.getOriginalFilename()).thenReturn("test2.jpg");

        List<UploadFile> mockUploadFiles = List.of(
                new UploadFile("test1.jpg", "uuid1.jpg"),
                new UploadFile("test2.jpg", "uuid2.jpg")
        );

        when(fileService.storeFiles(List.of(file1, file2))).thenReturn(mockUploadFiles);

        NewsReqDto newsReqDto = NewsReqDto.builder()
                .date(LocalDateTime.now())
                .activity("activity")
                .content("content")
                .imageFiles(List.of(file1, file2))
                .build();

        News mockNews = News.builder()
                .date(newsReqDto.date())
                .activity(newsReqDto.activity())
                .content(newsReqDto.content())
                .imageFiles(mockUploadFiles)
                .build();

        when(newsRepository.save(any(News.class))).thenReturn(mockNews);

        Long newsId = newsService.createNews(newsReqDto);

        assertEquals(1L, newsId, "뉴스 생성 후 id가 반환되어야 합니다.");

        verify(fileService, times(1)).storeFiles(List.of(file1, file2));
        verify(newsRepository, times(1)).save(any(News.class));
    }
}