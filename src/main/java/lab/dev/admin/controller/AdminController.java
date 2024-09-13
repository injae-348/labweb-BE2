package lab.dev.admin.controller;

import lab.dev.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping // 메인 페이지
    public String admin() {
        return "admin/index";
    }

}
