package lab.dev.admin.controller;


import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.dto.NewsUpdateResDto;
import lab.dev.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin/news")
public class NewsAdminController {

    private final NewsService newsService;
    private final FileService fileService;

    @GetMapping // 뉴스 리스트 조회
    public String listNews(
            Model model
    ) {
        List<NewsResDto> newsList = newsService.getNewsList();
        model.addAttribute("newsList", newsList);
        return "news/list";
    }

    @GetMapping("/create") // 뉴스 생성 페이지
    public String createNewsForm(
            Model model
    ) {
        LocalDateTime initialDate = LocalDateTime.of(0, 1, 1, 0, 0);
        model.addAttribute("newsReqDto", new NewsReqDto(initialDate,"활동을 입력해주세요","컨텐트를 입력해주세요", new ArrayList<>()));
        return "news/create";
    }

    @PostMapping("/create") // 뉴스 생성
    public String createNews(
            @ModelAttribute NewsReqDto newsReqDto
    ) {
        newsService.createNews(newsReqDto);
        return "redirect:/api/admin/news";
    }

    @GetMapping("/edit/{news-id}") // 뉴스 수정 페이지
    public String editNews(
            @PathVariable(name = "news-id") Long newsId,
            Model model
    ) {
        NewsUpdateResDto news = newsService.getUpdateNews(newsId);
        model.addAttribute("news", news);
        return "news/edit";
    }

    @PostMapping("/update/{news-id}") // 뉴스 수정
    public String updateNews(
            @PathVariable(name = "news-id") Long newsId,
            @ModelAttribute NewsReqDto newsReqDto
    ) {
        newsService.updateNews(newsId, newsReqDto);
        return "redirect:/api/admin/news";
    }

    @GetMapping("/delete/{news-id}") // 뉴스 삭제
    public String deleteNews(
            @PathVariable(name = "news-id") Long newsId
    ) {
        newsService.deleteNews(newsId);
        return "redirect:/api/admin/news";
    }

    @GetMapping("/{news-id}/files/{filename}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable(name = "news-id") Long newsId,
            @PathVariable String filename
    ) throws MalformedURLException {

        NewsUpdateResDto news = newsService.getUpdateNews(newsId);
        UploadFile file = news.imageFiles().stream()
                .filter(uploadFile -> uploadFile.getStoredFilename().equals(filename))
                .findFirst()
                .orElseThrow();

        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(filename));

        String encodedUploadFilename = UriUtils.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFilename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
