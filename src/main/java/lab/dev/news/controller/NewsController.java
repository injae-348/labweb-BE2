package lab.dev.news.controller;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.domain.News;
import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    private final FileService fileService;

    @GetMapping("/{news-id}")
    public ResponseEntity<NewsResDto> getNews(
            @PathVariable("news-id") Long newsId
    ) {
        NewsResDto newsResDto = newsService.getNews(newsId);
        return ResponseEntity.ok(newsResDto);
    }

    @PostMapping
    public ResponseEntity<Long> createNews(
            NewsReqDto newsReqDto
    ) throws IOException {
        Long newsId = newsService.createNews(newsReqDto);
        return ResponseEntity.ok(newsId);
    }

}
