package lab.dev.file.controller;

import lab.dev.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    // Todo: 보안상 문제가 있을 수 있음
    @GetMapping("/{filename}")
    public Resource downloadFile(
            @PathVariable String filename
    ) {
        return fileService.loadFileAsResource(filename);
    }

}
