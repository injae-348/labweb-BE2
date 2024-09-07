package lab.dev.professor.service;

import lab.dev.professor.domain.Career;
import lab.dev.professor.dto.CareerReqDto;
import lab.dev.professor.dto.CareerResDto;
import lab.dev.professor.exception.CareerNotFoundException;
import lab.dev.professor.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;

    @Transactional(readOnly = true)
    public Career findCareerByIdOrThrow(Long id) {
        return careerRepository.findById(id)
                .orElseThrow(() -> CareerNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public CareerResDto getCareer(Long id) {
        Career career = findCareerByIdOrThrow(id);
        return CareerResDto.of(career);
    }

    @Transactional(readOnly = true)
    public List<CareerResDto> getCareers() {
        List<Career> careers = careerRepository.findAll();
        return CareerResDto.of(careers);
    }

    @Transactional
    public CareerResDto createCareer(CareerReqDto careerReqDto) {
        Career savedCareer = careerRepository.save(careerReqDto.toEntity());
        return CareerResDto.of(savedCareer);
    }

    @Transactional
    public CareerResDto updateCareer(Long id, CareerReqDto careerReqDto) {
        Career updatedCareer = findCareerByIdOrThrow(id);
        updatedCareer.update(careerReqDto);
        return CareerResDto.of(updatedCareer);
    }

    @Transactional
    public Long deleteCareer(Long id) {
        careerRepository.deleteById(id);
        return id;
    }

}
