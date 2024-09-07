package lab.dev.professor.service;

import lab.dev.file.domain.UploadFile;
import lab.dev.file.exception.FileNotFoundException;
import lab.dev.file.service.FileService;
import lab.dev.professor.domain.Career;
import lab.dev.professor.domain.Education;
import lab.dev.professor.domain.Professor;
import lab.dev.professor.domain.ResearchPage;
import lab.dev.professor.dto.*;
import lab.dev.professor.exception.ProfNotFoundException;
import lab.dev.professor.repository.CareerRepository;
import lab.dev.professor.repository.EducationRepository;
import lab.dev.professor.repository.ProfessorRepository;
import lab.dev.professor.repository.ResearchPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public Professor findProfessorByIdOrThrow(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> ProfNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public ProfResDto getProfessor(Long id) {
        Professor professor = findProfessorByIdOrThrow(id);
        return ProfResDto.of(professor);
    }

    @Transactional
    public ProfResDto updateProfessor(
            Long id,
            ProfReqDto profReqDto
    ) {
        Professor updatedProfessor = findProfessorByIdOrThrow(id);

        String existingFilename = getStoredFilename(updatedProfessor);
        fileService.deleteFile(existingFilename);
        UploadFile uploadFile = fileService.storeFile(profReqDto.imageFiles());

        updatedProfessor.update(profReqDto, uploadFile);
        return ProfResDto.of(updatedProfessor);
    }

    private static String getStoredFilename(Professor professor) {
        return professor.getImageFiles().getStoredFilename();
    }
}
