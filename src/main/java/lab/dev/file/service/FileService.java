package lab.dev.file.service;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.exception.FileDeleteFailedException;
import lab.dev.file.exception.FileNotFoundException;
import lab.dev.file.exception.FileTypeNotAllowedException;
import lab.dev.file.exception.FileUploadFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${FILE_DIR}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {
        if(multipartFiles == null) {
            multipartFiles = new ArrayList<>();
        }
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw FileNotFoundException.EXCEPTION;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        try{
            multipartFile.transferTo(new File(getFullPath(storeFilename)));
        } catch (IOException e) {
            throw FileUploadFailedException.EXCEPTION;
        }
        return new UploadFile(originalFilename, storeFilename);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extracted(originalFilename);
        // 서버에 저장하는 파일명을 UUID로 생성
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        if (pos == -1) {
            throw FileTypeNotAllowedException.EXCEPTION;
        }
        return originalFilename.substring(pos+1);
    }

    public void deleteFile(String filename) {
        File file = new File(getFullPath(filename));
        if (file.exists()) {
            if (!file.delete()) {
                throw FileDeleteFailedException.EXCEPTION;
            }
        } else {
            throw FileNotFoundException.EXCEPTION;
        }
    }
}
