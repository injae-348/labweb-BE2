package lab.dev;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.service.FileService;
import lab.dev.news.domain.News;
import lab.dev.news.repository.NewsRepository;
import lab.dev.professor.domain.Career;
import lab.dev.professor.domain.Education;
import lab.dev.professor.domain.Professor;
import lab.dev.professor.domain.ResearchPage;
import lab.dev.professor.repository.CareerRepository;
import lab.dev.professor.repository.EducationRepository;
import lab.dev.professor.repository.ProfessorRepository;
import lab.dev.professor.repository.ResearchPageRepository;
import lab.dev.professor.service.ProfessorService;
import lombok.RequiredArgsConstructor;
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

// Todo: 초기화 클래스 다듬기 -> 실제 사용할 데이터 사용 -> .gitignore로 관리
// Todo: 종료시 이미지 파일 삭제
@Slf4j
@Configuration
@RequiredArgsConstructor
public class InitialDataLoader {

    private final FileService fileService;
    private final NewsRepository newsRepository;
    private final ProfessorRepository professorRepository;
    private final EducationRepository educationRepository;
    private final CareerRepository careerRepository;
    private final ResearchPageRepository researchPageRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {

            Runtime.getRuntime().addShutdownHook(new Thread(this::deleteStoredImages));

            // News 엔티티 생성
            News test1 = new News(
                    LocalDateTime.of(2024, 3, 15, 10, 0),
                    "activity1",
                    "content1",
                    new ArrayList<>());

            // 이미지 파일 추가
            addImageTo(test1, "fox1.png");
            addImageTo(test1,"fox2.png");

            // 저장
            newsRepository.save(test1);

            /*
            Professor professor = Professor.builder()
                    .build();

            addImageTo(professor, "fox1.png");

            Career career1 = Career.builder()
                    .build();
            Career career2 = Career.builder()
                    .build();
            Career career3 = Career.builder()
                    .build();

            Education education1 = Education.builder()
                    .build();

            Education education2 = Education.builder()
                    .build();

            Education education3 = Education.builder()
                    .build();

            ResearchPage researchPage1 = ResearchPage.builder()
                    .build();

            ResearchPage researchPage2 = ResearchPage.builder()
                    .build();

            ResearchPage researchPage3 = ResearchPage.builder()
                    .build();

            professorRepository.save(professor);
            careerRepository.saveAll(List.of(career1, career2, career3));
            educationRepository.saveAll(List.of(education1, education2, education3));
            researchPageRepository.saveAll(List.of(researchPage1, researchPage2, researchPage3));
            */
        };
    }

    private void addImageTo(Object entity, String fileName) throws IOException {
        // 파일을 읽어오기
        Resource resource = new ClassPathResource("static/images/" + fileName);
        if (resource.exists()) {
            String storedFileName = UUID.randomUUID() + "_" + fileName;
            // 저장할 파일 경로 설정
            Path targetPath = Paths.get(fileService.getFullPath(storedFileName));

            // 파일 저장
            Files.copy(resource.getInputStream(), targetPath);

            // UploadFile 객체 생성 및 뉴스에 추가
            UploadFile uploadFile = new UploadFile(fileName, storedFileName);

            if (entity instanceof News) {
                ((News) entity).addImage(uploadFile);
            } else if (entity instanceof Professor) {
                ((Professor) entity).addImage(uploadFile);
            }
        }
    }

    private void deleteStoredImages() {
        try {
            Path imagesPath = Paths.get(fileService.getFullPath(""));
            Files.walk(imagesPath)
                    .filter(Files::isRegularFile) // 파일만(디렉토리 제외)
                    .map(Path::toFile) // Path -> File
                    .forEach(File::delete);
            log.info("Stored images deleted");
        } catch (IOException e) {
            log.error("Failed to delete stored images", e);
        }
    }

}
