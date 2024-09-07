package lab.dev;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.domain.News;
import lab.dev.news.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
public class InitialDataLoader {

    private final FileService fileService;
    private final NewsRepository newsRepository;

    public InitialDataLoader(FileService fileService, NewsRepository newsRepository) {
        this.fileService = fileService;
        this.newsRepository = newsRepository;
    }

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            // News 엔티티 생성
            News pressConference = new News(
                    LocalDateTime.of(2024, 3, 15, 10, 0),
                    "Press Conference",
                    "Announced new product line",
                    new ArrayList<>());
            News charityEvent = new News(
                    LocalDateTime.of(2024, 3, 16, 14, 30),
                    "Charity Event",
                    "Raised funds for local community",
                    new ArrayList<>());
            News productLaunch = new News(
                    LocalDateTime.of(2024, 3, 17, 9, 0),
                    "Product Launch",
                    "Introduced innovative technology",
                    new ArrayList<>());

            // 이미지 파일 추가
            addImageToNews(pressConference, "fox1.png");
            addImageToNews(pressConference,"fox2.png");
//            addImageToNews(charityEvent, "charity_event.jpg");
//            addImageToNews(productLaunch, "product_launch_1.jpg");
//            addImageToNews(productLaunch, "product_launch_2.jpg");
//            addImageToNews(productLaunch, "product_launch_3.jpg");

            // 저장
            newsRepository.saveAll(List.of(pressConference, charityEvent, productLaunch));
        };
    }

    private void addImageToNews(News news, String fileName) throws IOException {
        // 파일을 읽어오기
        Resource resource = new ClassPathResource("static/images/" + fileName);
        if (resource.exists()) {
            // 저장할 파일 경로 설정
            String storedFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path targetPath = Paths.get(fileService.getFullPath(storedFileName));

            // 파일 저장
            Files.copy(resource.getInputStream(), targetPath);

            // UploadFile 객체 생성 및 뉴스에 추가
            UploadFile uploadFile = new UploadFile(fileName, storedFileName);
            news.addImage(uploadFile);
        }
    }

}
