package lab.dev.news.controller;

import jakarta.validation.Valid;
import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.domain.News;
import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping // 목록 조회
    public ResponseEntity<List<NewsResDto>> getNewsList() {
        List<NewsResDto> newsResDtoList = newsService.getNewsList();
        return ResponseEntity.ok(newsResDtoList);
    }

    @GetMapping("/{news-id}") // 단일 조회
    public ResponseEntity<NewsResDto> getNews(
            @PathVariable("news-id") Long newsId
    ) {
        NewsResDto newsResDto = newsService.getNews(newsId);
        return ResponseEntity.ok(newsResDto);
    }
}
