package lab.dev.admin.controller;

import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin/news")
public class NewsAdminController {

    private final NewsService newsService;

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

    @GetMapping("/edit/{id}") // 뉴스 수정 페이지
    public String editNews(
            @PathVariable Long id, Model model
    ) {
        NewsResDto news = newsService.getNews(id);
        model.addAttribute("news", news);
        return "news/edit";
    }

    @PostMapping("/update/{id}") // 뉴스 수정
    public String updateNews(
            @PathVariable Long id,
            @ModelAttribute NewsReqDto newsReqDto
    ) {
        newsService.updateNews(id, newsReqDto);
        return "redirect:/api/admin/news";
    }

    @GetMapping("/delete/{id}") // 뉴스 삭제
    public String deleteNews(
            @PathVariable Long id
    ) {
        newsService.deleteNews(id);
        return "redirect:/api/admin/news";
    }
}
