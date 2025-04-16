package Mefo.server.domain.disease.controller;

import Mefo.server.domain.disease.dto.DiseaseResponse;
import Mefo.server.domain.disease.entity.Disease;
import Mefo.server.domain.disease.repository.DiseaseRepository;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/disease")
@RequiredArgsConstructor
@Tag(name="질병")
public class DiseaseController {
    private final DiseaseRepository diseaseRepository;
    //질병 목록 불러오기
    @GetMapping({"/login/disease/show", "/disease/show"})
    @Operation(summary = "질병 목록 불러오기")
    public ApiResponse<List<DiseaseResponse>> getDisease(){
        List<Disease> diseaseList = diseaseRepository.findAll();
        return new ApiResponse<>(200, DiseaseResponse.from(diseaseList));
    }
}
