package lab.dev.admin.controller;

import lab.dev.professor.dto.ProfessorPageDto;
import lab.dev.professor.dto.*;
import lab.dev.professor.service.CareerService;
import lab.dev.professor.service.EducationService;
import lab.dev.professor.service.ProfessorService;
import lab.dev.professor.service.ResearchPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin/professors")
public class ProfAdminController {

    private final ProfessorService professorService;
    private final CareerService careerService;
    private final EducationService educationService;
    private final ResearchPageService researchPageService;

    // Todo: 경력, 학력, 연구 수정&생성 -> html 추가(edit, create)
    // Todo: 교수 정보 수정 -> html 추가(edit)
    // Todo: 총 7개의 html 추가
    // Todo: Professor 관련 테스트 코드 작성(Professor & Career만 작성)

    @GetMapping // 정보, 경력, 학력, 연구 리스트 조회
    public String getProfessorDetail(
            Model model
    ) {
        ProfessorPageDto professorPage = ProfessorPageDto.builder()
                .professor(professorService.getProfessor(1L))
                .careers(careerService.getCareers())
                .educations(educationService.getEducations())
                .researchPages(researchPageService.getResearchPages())
                .build();

        model.addAttribute("professorPage", professorPage);
        return "professor/list";
    }

    // Prof =======================================================================
    @GetMapping("/edit/{professor-id}") // 정보 수정 페이지
    public String editProfessorForm(
            @PathVariable("professor-id") Long professorId,
            Model model
    ) {
        ProfResDto professor = professorService.getProfessor(professorId);
        model.addAttribute("professor", professor);
        return "professor/edit";
    }

    @PostMapping("/edit/{professor-id}") // 정보 수정
    public String editProfessor(
            @PathVariable("professor-id") Long professorId,
            @ModelAttribute ProfReqDto professor
    ) {
        professorService.updateProfessor(professorId, professor);
        return "redirect:/api/admin/professors";
    }

    // Career =====================================================================

    @GetMapping("/career/create") // 경력 생성 페이지
    public String createCareerForm(
            Model model
    ) {
        CareerReqDto career = CareerReqDto.builder()
                .experienceYear("")
                .career("")
                .careerDetailKR("")
                .careerDetailEN("")
                .build();
        model.addAttribute("career", career);
        return "professor/career/create";
    }

    @PostMapping("/career/create") // 경력 생성
    public String createCareer(
            @ModelAttribute CareerReqDto career
    ) {
        careerService.createCareer(career);
        return "redirect:/api/admin/professors";
    }

    @GetMapping("/career/edit/{id}") // 경력 수정 페이지
    public String editCareerForm(
            @PathVariable Long id,
            Model model
    ) {
        CareerResDto career = careerService.getCareer(id);
        model.addAttribute("career", career);
        return "professor/career/edit";
    }

    @PostMapping("/career/update/{id}") // 경력 수정
    public String editCareer(
            @PathVariable Long id,
            @ModelAttribute CareerReqDto career
    ) {
        careerService.updateCareer(id, career);
        return "redirect:/api/admin/professors";
    }

    @GetMapping("/career/delete/{id}") // 경력 삭제
    public String deleteCareer(
            @PathVariable Long id
    ) {
        careerService.deleteCareer(id);
        return "redirect:/api/admin/professors";
    }

    // Education ===================================================================

    @GetMapping("/education/create") // 학력 생성 페이지
    public String createEducationForm(
            Model model
    ) {
        EduReqDto education = EduReqDto.builder()
                .experienceYear("")
                .degreeSummary("")
                .degreeDetailKR("")
                .degreeDetailEN("")
                .build();
        model.addAttribute("education", education);
        return "professor/education/create";
    }

    @PostMapping("/education/create") // 학력 생성
    public String createEducation(
            @ModelAttribute EduReqDto eduReqDto
    ) {
        educationService.createEducation(eduReqDto);
        return "redirect:/api/admin/professors";
    }

    @GetMapping("/education/edit/{id}") // 학력 수정 페이지
    public String editEducationForm(
            @PathVariable Long id,
            Model model
    ) {
        EduResDto education = educationService.getEducation(id);
        model.addAttribute("education", education);
        return "professor/education/edit";
    }

    @PostMapping("/education/update/{id}") // 학력 수정
    public String editEducation(
            @PathVariable Long id,
            @ModelAttribute EduReqDto education
    ) {
        educationService.updateEducation(id, education);
        return "redirect:/api/admin/professors";
    }

    @GetMapping("/education/delete/{id}") // 학력 삭제
    public String deleteEducation(
            @PathVariable Long id
    ) {
        educationService.deleteEducation(id);
        return "redirect:/api/admin/professors";
    }


    // ResearchPage ================================================================
    @GetMapping("/research/create") // 연구 생성 페이지
    public String createResearchForm(
            Model model
    ) {
        ResearReqDto research = ResearReqDto.builder()
                .title("")
                .url("")
                .build();
        model.addAttribute("research", research);
        return "professor/research/create";
    }

    @PostMapping("/research/create") // 연구 생성
    public String createResearch(
            @ModelAttribute ResearReqDto research
    ) {
        researchPageService.createResearchPage(research);
        return "redirect:/api/admin/professors";
    }

    @GetMapping("/research/edit/{id}") // 연구 수정 페이지
    public String editResearchForm(
            @PathVariable Long id,
            Model model
    ) {
        ResearResDto research = researchPageService.getResearchPage(id);
        model.addAttribute("research", research);
        return "professor/research/edit";
    }

    @PostMapping("/research/update/{id}") // 연구 수정
    public String editResearch(
            @PathVariable Long id,
            @ModelAttribute ResearReqDto research
    ) {
        researchPageService.updateResearchPage(id, research);
        return "redirect:/api/admin/professors";
    }

    @GetMapping("/research/delete/{id}") // 연구 삭제
    public String deleteResearch(
            @PathVariable Long id
    ) {
        researchPageService.deleteResearchPage(id);
        return "redirect:/api/admin/professors";
    }
}
