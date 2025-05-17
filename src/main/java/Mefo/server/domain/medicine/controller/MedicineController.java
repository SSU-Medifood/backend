package Mefo.server.domain.medicine.controller;

import Mefo.server.domain.medicine.dto.MedicineRequest;
import Mefo.server.domain.medicine.dto.MedicineResponse;
import Mefo.server.domain.medicine.entity.Medicine;
import Mefo.server.domain.medicine.repository.MedicineRepository;
import Mefo.server.domain.medicine.service.MedicineService;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
@Tag(name="Medicine")
public class MedicineController {
    private final UserService userService;
    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;

    //복용약 목록 불러오기
    @GetMapping("/get")
    @Operation(summary = "복용약 정보 불러오기")
    public ApiResponse<List<MedicineResponse>> getMedicine(Authentication authentication){
        User user = userService.getLoginUser(authentication.getName());
        List<Medicine> medicines= medicineRepository.findAllByUserId(user.getId());
        return new ApiResponse<>(200, MedicineResponse.from(medicines));
    }

    //약 정보 입력하기
    @Transactional
    @PostMapping("/create")
    @Operation(summary = "복용약 등록하기")
    public ApiResponse<MedicineResponse> createMedicine(Authentication authentication, @RequestBody MedicineRequest medicineRequest){
        User user = userService.getLoginUser(authentication.getName());
        Medicine medicine = medicineService.createMedicine(user, medicineRequest);
        return new ApiResponse<>(201, MedicineResponse.from(medicine));
    }
    //약 정보 수정하기
    @Transactional
    @PatchMapping("/patch/{mediId}")
    @Operation(summary = "약 정보 수정하기")
    public ApiResponse<MedicineResponse> patchMedicine(Authentication authentication, @PathVariable Long mediId, @RequestBody MedicineRequest medicineRequest){
        User user = userService.getLoginUser(authentication.getName());
        Medicine medicine = medicineService.patchMedicine(user, mediId, medicineRequest);
        return new ApiResponse<>(200, MedicineResponse.from(medicine));
    }
    //복용약 삭제하기
    @Transactional
    @DeleteMapping("/delete/{mediId}")
    @Operation(summary = "복용약 삭제하기")
    public ApiResponse<String> deleteMedicine(Authentication authentication, @PathVariable Long mediId){
        User user = userService.getLoginUser(authentication.getName());
        medicineService.deleteMedicine(user, mediId);
        return new ApiResponse<>(204, null);
    }
}
