package lab.dev.admin.controller;

import lab.dev.news.dto.NewsReqDto;
import lab.dev.news.dto.NewsResDto;
import lab.dev.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin/news")
public class NewsAdminController {

    private final NewsService newsService;

    @GetMapping
    public String listNews(
            Model model
    ) {
        List<NewsResDto> newsList = newsService.getNewsList();
        model.addAttribute("newsList", newsList);
        return "news/list";
    }

    @GetMapping("/edit/{id}")
    public String editNews(
            @PathVariable Long id, Model model
    ) {
        NewsResDto news = newsService.getNews(id);
        model.addAttribute("news", news);
        return "news/edit";
    }

    @PostMapping("/update/{id}")
    public String updateNews(
            @PathVariable Long id,
            @ModelAttribute NewsReqDto newsReqDto
    ) {
        newsService.updateNews(id, newsReqDto);
        return "redirect:/api/admin/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(
            @PathVariable Long id
    ) {
        newsService.deleteNews(id);
        return "redirect:/api/admin/news";
    }
}
