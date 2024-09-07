package lab.dev.file.service;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.exception.FileDeleteFailedException;
import lab.dev.file.exception.FileNotFoundException;
import lab.dev.file.exception.FileTypeNotAllowedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Value("${FILE_DIR}")
    private String fileDir;

    @Test
    @DisplayName("파일을 리소스로 로드")
    void loadFileAsResource() throws IOException {
        String filename = "test.txt";
        Path filePath = Paths.get(fileService.getFullPath(filename));
        Files.createFile(filePath); // 더미 파일 생성

        Resource resource = fileService.loadFileAsResource(filename);
        assertTrue(resource.exists());
        assertEquals(filePath.toUri(), resource.getURI());

        Files.delete(filePath); // 더미 파일 삭제
    }

    @Test
    @DisplayName("파일을 리소스로 로드 - 파일이 존재하지 않음")
    void loadFileAsResourceNotFound() {
        String filename = "test.txt";

        Exception exception = assertThrows(FileNotFoundException.EXCEPTION.getClass(), () -> fileService.loadFileAsResource(filename));
        assertEquals(FileNotFoundException.EXCEPTION.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("파일을 리소스로 로드 - 파일 경로에 '..' 포함")
    void loadFileAsResourceWithDotDot() {
        String filename = "../test.txt";

        Exception exception = assertThrows(FileTypeNotAllowedException.EXCEPTION.getClass(), () -> fileService.loadFileAsResource(filename));
        assertEquals(FileTypeNotAllowedException.EXCEPTION.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("단일 파일 저장")
    void storeFileSuccess() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
        UploadFile uploadFile = fileService.storeFile(multipartFile);

        assertNotNull(uploadFile);
        assertEquals("test.txt",uploadFile.getOriginalFilename());

        File storedFile = new File(fileService.getFullPath(uploadFile.getStoredFilename()));
        assertTrue(storedFile.exists());

        storedFile.delete();
    }

    @Test
    @DisplayName("다중 파일 저장")
    void storeFiles() {
        MockMultipartFile multipartFile1 = new MockMultipartFile("file1", "test1.txt", "text/plain", "test content 1".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("file2", "test2.txt", "text/plain", "test content 2".getBytes());

        List<UploadFile> uploadFiles = fileService.storeFiles(List.of(multipartFile1, multipartFile2));

        assertEquals(2, uploadFiles.size());

        for (UploadFile uploadFile : uploadFiles) {
            File storedFile = new File(fileService.getFullPath(uploadFile.getStoredFilename()));
            assertTrue(storedFile.exists());
            storedFile.delete();
        }
    }

    @Test
    @DisplayName("파일 저장 실패 - 파일이 비어있음")
    void storeFileFailed() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "".getBytes());

        Exception exception = assertThrows(FileNotFoundException.EXCEPTION.getClass(), () -> fileService.storeFile(multipartFile));
        assertEquals(FileNotFoundException.EXCEPTION.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("파일 삭제 실패 - 파일이 null")
    void deleteFileFailed() {
        String filename = null;

        assertThrows(FileNotFoundException.EXCEPTION.getClass(), () -> fileService.deleteFile(filename));
    }

    @Test
    @DisplayName("파일 삭제 성공")
    void deleteFileSuccess() {
        String filename = "test.txt";
        Path filePath = Paths.get(fileService.getFullPath(filename));
        try {
            Files.createFile(filePath); // 더미 파일 생성
        } catch (FileDeleteFailedException | IOException e) {
            throw new RuntimeException(e);
        }
        fileService.deleteFile(filename);
        assertFalse(Files.exists(filePath));
    }
}