package lab.dev.file.controller;

import lab.dev.file.exception.FileDownloadFailedException;
import lab.dev.file.exception.FileNotFoundException;
import lab.dev.file.service.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(FileController.class)
@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    @DisplayName("파일 다운로드 테스트")
    void downloadFile() throws Exception {

        String filename = "test.txt";
        byte[] content = "test".getBytes();
        Resource resource = new ByteArrayResource(content);

        given(fileService.loadFileAsResource(filename))
                .willReturn(resource);

        mockMvc.perform(get("/api/files/{filename}", filename))
                .andExpect(status().isOk())
                .andExpect(content().bytes(content));
    }

    @Test
    @DisplayName("파일 다운로드 실패 - 파일이 존재하지 않음")
    void downloadFileNotFound() throws Exception {

        String filename = "test.txt";

        given(fileService.loadFileAsResource(filename))
                .willThrow(FileNotFoundException.EXCEPTION);

        mockMvc.perform(get("/api/files/{filename}", filename))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("파일 다운로드 실패 - 서비스 오류")
    void downloadFileServiceException() throws Exception {

        String filename = "test.txt";

        given(fileService.loadFileAsResource(filename))
                .willThrow(FileDownloadFailedException.EXCEPTION);

        mockMvc.perform(get("/api/files/{filename}", filename))
                .andExpect(status().is4xxClientError());
    }
}