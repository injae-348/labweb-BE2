package lab.dev.professor.dto;

import lab.dev.professor.dto.CareerResDto;
import lab.dev.professor.dto.EduResDto;
import lab.dev.professor.dto.ProfResDto;
import lab.dev.professor.dto.ResearResDto;
import lombok.Builder;

import java.util.List;

@Builder
public record ProfessorPageDto(
        ProfResDto professor,
        List<CareerResDto> careers,
        List<EduResDto> educations,
        List<ResearResDto> researchPages
) {

}
