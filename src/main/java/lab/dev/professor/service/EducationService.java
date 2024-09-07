package lab.dev.professor.service;

import lab.dev.professor.domain.Education;
import lab.dev.professor.dto.EduReqDto;
import lab.dev.professor.dto.EduResDto;
import lab.dev.professor.exception.EducationNotFoundException;
import lab.dev.professor.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;

    @Transactional(readOnly = true)
    public Education findEducationByIdOrThrow(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> EducationNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public EduResDto getEducation(Long id) {
        Education education =  findEducationByIdOrThrow(id);
        return EduResDto.of(education);
    }

    @Transactional(readOnly = true)
    public List<EduResDto> getEducations() {
        List<Education> educations = educationRepository.findAll();
        return EduResDto.of(educations);
    }

    @Transactional
    public EduResDto createEducation(EduReqDto eduReqDto) {
        Education savedEducation = educationRepository.save(eduReqDto.toEntity());
        return EduResDto.of(savedEducation);
    }

    @Transactional
    public EduResDto updateEducation(Long id, EduReqDto eduReqDto) {
        Education updatedEducation = findEducationByIdOrThrow(id);
        updatedEducation.update(eduReqDto);
        return EduResDto.of(updatedEducation);
    }

    @Transactional
    public Long deleteEducation(Long id) {
        educationRepository.deleteById(id);
        return id;
    }

}
