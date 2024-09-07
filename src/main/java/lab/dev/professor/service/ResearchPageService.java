package lab.dev.professor.service;

import lab.dev.professor.domain.ResearchPage;
import lab.dev.professor.dto.ResearReqDto;
import lab.dev.professor.dto.ResearResDto;
import lab.dev.professor.exception.ResearchPageNotFoundException;
import lab.dev.professor.repository.ResearchPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchPageService {

    private final ResearchPageRepository researchPageRepository;

    @Transactional(readOnly = true)
    public ResearchPage findResearchPageByIdOrThrow(Long id) {
        return researchPageRepository.findById(id)
                .orElseThrow(() -> ResearchPageNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public ResearResDto getResearchPage(Long id) {
        ResearchPage researchPage = findResearchPageByIdOrThrow(id);
        return ResearResDto.of(researchPage);
    }

    @Transactional(readOnly = true)
    public List<ResearResDto> getResearchPages() {
        List<ResearchPage> researchPages = researchPageRepository.findAll();
        return ResearResDto.of(researchPages);
    }

    @Transactional
    public ResearResDto createResearchPage(ResearReqDto resReqDto) {
        ResearchPage savedResearchPage = researchPageRepository.save(resReqDto.toEntity());
        return ResearResDto.of(savedResearchPage);
    }

    @Transactional
    public ResearResDto updateResearchPage(Long id, ResearReqDto resReqDto) {
        ResearchPage updatedResearchPage = findResearchPageByIdOrThrow(id);
        updatedResearchPage.update(resReqDto);
        return ResearResDto.of(updatedResearchPage);
    }

    @Transactional
    public Long deleteResearchPage(Long id) {
        researchPageRepository.deleteById(id);
        return id;
    }

}
