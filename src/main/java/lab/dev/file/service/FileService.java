package lab.dev.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Value("${FILE_DIR}")
    private String fileDir;


}
