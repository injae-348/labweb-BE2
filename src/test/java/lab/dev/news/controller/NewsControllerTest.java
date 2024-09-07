package lab.dev.news.controller;

import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.repository.NewsRepository;
import lab.dev.news.service.NewsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerTest {

    @MockBean
    private NewsService newsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("뉴스 생성")
    void createNews() throws Exception {

        LocalDateTime date = LocalDateTime.now();

        // given
        NewsReqDto newsReqDto = NewsReqDto.builder()
                .date(date)
                .activity("활동")
                .content("내용")
                .imageFiles(List.of(
                        new MockMultipartFile("imageFiles", "image1.jpg", "image/jpeg", "image1".getBytes()),
                        new MockMultipartFile("imageFiles", "image2.jpg", "image/jpeg", "image2".getBytes())
                ))
                .build();
        given(newsService.createNews(any(NewsReqDto.class))).willReturn(1L);

        // when & then
        mockMvc.perform(multipart("/api/news")
                        .file("imageFiles", "image1.jpg".getBytes())
                        .file("imageFiles", "image2.jpg".getBytes())
                        .param("date", newsReqDto.date().toString())
                        .param("activity", newsReqDto.activity())
                        .param("content", newsReqDto.content())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(1L));

        verify(newsService, times(1)).createNews(any(NewsReqDto.class));
    }

    @Test
    @DisplayName("단일 뉴스 조회 - 이미지 포함")
    void getNews() throws Exception {

        LocalDateTime date = LocalDateTime.now().withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // given
        Long newsId = 1L;
        NewsResDto newsResDto = NewsResDto.builder()
                .id(newsId)
                .date(date)
                .activity("활동")
                .content("내용")
                .imageUrls(List.of("url1", "url2"))
                .build();
        given(newsService.getNews(newsId)).willReturn(newsResDto);

        // when & then
        mockMvc.perform(get("/api/news/{news-id}", newsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newsId))
                .andExpect(jsonPath("$.date").value(date.format(formatter)))
                .andExpect(jsonPath("$.activity").value("활동"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.imageUrls").isArray())
                .andExpect(jsonPath("$.imageUrls").isNotEmpty())
                .andExpect(jsonPath("$.imageUrls[0]").value("url1"))
                .andExpect(jsonPath("$.imageUrls[1]").value("url2"));

        verify(newsService, times(1)).getNews(newsId);
    }

    @Test
    @DisplayName("단일 뉴스 조회 - 이미지 미포함")
    void getNewsNoImg() throws Exception {

        LocalDateTime date = LocalDateTime.now().withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // given
        Long newsId = 1L;
        NewsResDto newsResDto = NewsResDto.builder()
                .id(newsId)
                .date(date)
                .activity("활동")
                .content("내용")
                .build();
        given(newsService.getNews(newsId)).willReturn(newsResDto);

        // when & then
        mockMvc.perform(get("/api/news/{news-id}", newsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newsId))
                .andExpect(jsonPath("$.date").value(date.format(formatter)))
                .andExpect(jsonPath("$.activity").value("활동"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.imageUrls").doesNotExist());

        verify(newsService, times(1)).getNews(newsId);
    }

    @Test
    @DisplayName("뉴스 목록 조회")
    void getNewsList() throws Exception {

        LocalDateTime date = LocalDateTime.now().withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // given
        List<NewsResDto> newsResDtoList = List.of(
                NewsResDto.builder()
                        .id(1L)
                        .date(date)
                        .activity("활동1")
                        .content("내용1")
                        .imageUrls(List.of("url1", "url2"))
                        .build(),
                NewsResDto.builder()
                        .id(2L)
                        .date(date)
                        .activity("활동2")
                        .content("내용2")
                        .build()
        );
        given(newsService.getNewsList()).willReturn(newsResDtoList);

        // when & then
        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].date").value(date.format(formatter)))
                .andExpect(jsonPath("$[0].activity").value("활동1"))
                .andExpect(jsonPath("$[0].content").value("내용1"))
                .andExpect(jsonPath("$[0].imageUrls").isArray())
                .andExpect(jsonPath("$[0].imageUrls").isNotEmpty())
                .andExpect(jsonPath("$[0].imageUrls[0]").value("url1"))
                .andExpect(jsonPath("$[0].imageUrls[1]").value("url2"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].date").value(date.format(formatter)))
                .andExpect(jsonPath("$[1].activity").value("활동2"))
                .andExpect(jsonPath("$[1].content").value("내용2"))
                .andExpect(jsonPath("$[1].imageUrls").doesNotExist());

        verify(newsService, times(1)).getNewsList();
    }

    @Test
    @DisplayName("뉴스 삭제")
    void deleteNews() throws Exception {

        // given
        Long newsId = 1L;
        given(newsService.deleteNews(newsId)).willReturn(newsId);

        // when & then
        mockMvc.perform(delete("/api/news/{news-id}", newsId))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value(newsId));

        verify(newsService, times(1)).deleteNews(newsId);
    }

    @Test
    @DisplayName("뉴스 수정")
    void updateNews() throws Exception {

        LocalDateTime date = LocalDateTime.now().withNano(0);

        // given
        Long newsId = 1L;
        NewsReqDto newsReqDto = NewsReqDto.builder()
                .date(date)
                .activity("활동")
                .content("내용")
                .imageFiles(List.of(
                        new MockMultipartFile("imageFiles", "image1.jpg", "image/jpeg", "image1".getBytes()),
                        new MockMultipartFile("imageFiles", "image2.jpg", "image/jpeg", "image2".getBytes())
                ))
                .build();
        NewsResDto newsResDto = NewsResDto.builder()
                .id(newsId)
                .date(date)
                .activity("활동")
                .content("내용")
                .imageUrls(List.of("url1", "url2"))
                .build();
        given(newsService.updateNews(newsId, newsReqDto)).willReturn(newsResDto);

        // when & then
        mockMvc.perform(multipart(HttpMethod.PUT, "/api/news/{news-id}", newsId)
                        .file("imageFiles", "image1.jpg".getBytes())
                        .file("imageFiles", "image2.jpg".getBytes())
                        .param("date", newsReqDto.date().toString())
                        .param("activity", newsReqDto.activity())
                        .param("content", newsReqDto.content())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(newsService, times(1)).updateNews(eq(newsId), any(NewsReqDto.class));
    }
}