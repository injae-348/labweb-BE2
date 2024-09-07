package lab.dev.professor.controller;

import jakarta.validation.Valid;
import lab.dev.professor.dto.*;
import lab.dev.professor.service.CareerService;
import lab.dev.professor.service.EducationService;
import lab.dev.professor.service.ProfessorService;
import lab.dev.professor.service.ResearchPageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/professor")
public class ProfessorController {

    private final ProfessorService professorService;
    private final CareerService careerService;
    private final ResearchPageService researchPageService;
    private final EducationService educationService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfResDto> getProfessor(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(professorService.getProfessor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfResDto> updateProfessor(
            @PathVariable Long id,
            @Valid ProfReqDto profReqDto
            ) {
        return ResponseEntity.ok(professorService.updateProfessor(id, profReqDto));
    }

    @GetMapping("/{id}/careers")
    public ResponseEntity<List<CareerResDto>> getCareers(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(careerService.getCareers());
    }

    @GetMapping("/{id}/researchPages")
    public ResponseEntity<List<ResearResDto>> getResearchPages(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(researchPageService.getResearchPages());
    }

    @GetMapping("/{id}/educations")
    public ResponseEntity<List<EduResDto>> getEducations(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(educationService.getEducations());
    }

}
