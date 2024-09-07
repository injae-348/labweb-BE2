package lab.dev.news.service;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.domain.News;
import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.exception.NewsNotFoundException;
import lab.dev.news.repository.NewsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class NewsServiceTest {

    @Autowired
    private NewsService newsService;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private FileService fileService;

    @Test
    @DisplayName("NewsId로 News 찾기")
    void findNewsById() {
        Long newsId = 1L;
        News news = new News(
                LocalDateTime.now().withNano(0),
                "title",
                "content",
                List.of()
        );


        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));

        NewsResDto newsResDto = newsService.getNews(newsId);

        assertEquals(news.getId(), newsResDto.id());
        assertEquals(news.getDate(), newsResDto.date());
        assertEquals(news.getActivity(), newsResDto.activity());
        assertEquals(news.getContent(), newsResDto.content());
    }

    @Test
    @DisplayName("NewsId로 News 찾기 - 뉴스를 찾을 수 없음")
    void findNewsByIdNotFound() {
        Long newsId = 1L;
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        assertThrows(NewsNotFoundException.class, () -> newsService.findNewsByIdOrThrow(newsId));
    }

    @Test
    @DisplayName("뉴스 목록 조회")
    void getNewsList() {
        List<News> newsList = List.of(
                new News(
                        LocalDateTime.now().withNano(0),
                        "title1",
                        "content1",
                        List.of()
                ),
                new News(
                        LocalDateTime.now().withNano(0),
                        "title2",
                        "content2",
                        List.of()
                )
        );

        when(newsRepository.findAll()).thenReturn(newsList);

        List<NewsResDto> newsResDtoList = newsService.getNewsList();

        assertEquals(newsList.size(), newsResDtoList.size());
        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);
            NewsResDto newsResDto = newsResDtoList.get(i);

            assertEquals(news.getId(), newsResDto.id());
            assertEquals(news.getDate(), newsResDto.date());
            assertEquals(news.getActivity(), newsResDto.activity());
            assertEquals(news.getContent(), newsResDto.content());
        }

    }

    @Test
    @DisplayName("뉴스 생성")
    void createNews() {
        LocalDateTime testDate = LocalDateTime.now().withNano(0);
        List<MultipartFile> mockFiles = Arrays.asList(
                new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "test image 1".getBytes()),
                new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "test image 2".getBytes())
        );
        NewsReqDto newsReqDto = new NewsReqDto(testDate, "Test Activity", "Test Content", mockFiles);

        List<UploadFile> mockUploadFiles = Arrays.asList(
                new UploadFile("image1.jpg", "stored_image1.jpg"),
                new UploadFile("image2.jpg", "stored_image2.jpg")
        );

        when(fileService.storeFiles(any())).thenReturn(mockUploadFiles);
        // 이렇게 하는게 맞는건지 모르겠다.. 이정도 까지 해야하나;;
        when(newsRepository.save(any(News.class))).thenAnswer(invocation -> {
            News savedNews = invocation.getArgument(0);
            return new News(savedNews.getDate(), savedNews.getActivity(), savedNews.getContent(), savedNews.getImageFiles()) {
                @Override
                public Long getId() {
                    return 1L;
                }
            };
        });

        Long newsId = newsService.createNews(newsReqDto);
        System.out.println("newsId = " + newsId);
        assertThat(newsId).isNotNull();
        assertThat(newsId).isEqualTo(1L);
        verify(fileService, times(1)).storeFiles(any());
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    @DisplayName("뉴스 삭제")
    void deleteNews() {
        Long newsId = 1L;
        News news = new News(
                LocalDateTime.now().withNano(0),
                "title",
                "content",
                List.of(new UploadFile("image1.jpg", "stored_image1.jpg"))
        );

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));

        Long deletedNewsId = newsService.deleteNews(newsId);

        assertThat(deletedNewsId).isEqualTo(newsId);
        verify(fileService, times(1)).deleteFile(anyString());
        verify(newsRepository, times(1)).delete(any(News.class));
    }

    @Test
    @DisplayName("뉴스 수정")
    void updateNews() {
        Long newsId = 1L;
        LocalDateTime testDate = LocalDateTime.now().withNano(0);
        List<MultipartFile> mockFiles = Arrays.asList(
                new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "test image 1".getBytes()),
                new MockMultipartFile("image2", "image2.jpg", "image/jpeg", "test image 2".getBytes())
        );
        NewsReqDto newsReqDto = new NewsReqDto(testDate, "Updated Activity", "Updated Content", mockFiles);

        List<UploadFile> mockUploadFiles = Arrays.asList(
                new UploadFile("image1.jpg", "stored_image1.jpg"),
                new UploadFile("image2.jpg", "stored_image2.jpg")
        );

        News existingNews = new News(
                LocalDateTime.now().withNano(0),
                "title",
                "content",
                List.of(new UploadFile("old_image.jpg", "stored_old_image.jpg"))
        );

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(existingNews));
        doNothing().when(fileService).deleteFile(anyString());
        when(fileService.storeFiles(any())).thenReturn(mockUploadFiles);

        NewsResDto updatedNewsResDto = newsService.updateNews(newsId, newsReqDto);

        assertThat(updatedNewsResDto).isNotNull();
        assertThat(updatedNewsResDto.activity()).isEqualTo("Updated Activity");
        assertThat(updatedNewsResDto.content()).isEqualTo("Updated Content");
        verify(fileService, times(1)).deleteFile(anyString());
        verify(fileService, times(1)).storeFiles(any());
        verify(newsRepository, times(1)).findById(newsId);
    }
}