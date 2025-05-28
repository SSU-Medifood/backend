package Mefo.server.domain.storage.controller;

import Mefo.server.domain.recipeImage.entity.RecipeImage;
import Mefo.server.domain.recipeImage.service.RecipeImageService;
import Mefo.server.domain.storage.dto.StorageRequest;
import Mefo.server.domain.storage.dto.StorageResponse;
import Mefo.server.domain.storage.entity.Storage;
import Mefo.server.domain.storage.repository.StorageRepository;
import Mefo.server.domain.storage.service.StorageService;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.domain.recipeImage.dto.RecipeImageResponse;
import Mefo.server.domain.userRecipe.entity.UserRecipe;
import Mefo.server.domain.userRecipe.repository.UserRecipeRepository;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
@Tag(name="Storage")
public class StorageController {
    private final UserService userService;
    private final StorageService storageService;
    private final StorageRepository storageRepository;
    private final UserRecipeRepository userRecipeRepository;
    private final RecipeImageService recipeImageService;

    //보관함 생성하기
    @Transactional
    @PostMapping("/create")
    @Operation(summary = "보관함 생성하기")
    public ApiResponse<StorageResponse> createStorage(Authentication authentication, @RequestBody StorageRequest storageRequest){
        User user = userService.getLoginUser(authentication.getName());
        Storage storage = storageService.createStorage(user, storageRequest);
        return new ApiResponse<>(201, StorageResponse.from(storage));
    }
    //보관함 이름 수정
    @Transactional
    @PatchMapping("/patch/{storageId}")
    @Operation(summary = "보관함 이름 수정")
    public ApiResponse<StorageResponse> patchStorage(Authentication authentication, @PathVariable Long storageId, @RequestBody StorageRequest storageRequest){
        User user = userService.getLoginUser(authentication.getName());
        Storage storage = storageService.patchStorage(user, storageId, storageRequest);
        return new ApiResponse<>(200, StorageResponse.from(storage));
    }

    //보관함 전체 불러오기
    @GetMapping("/get")
    @Operation(summary = "보관함 목록 불러오기")
    public ApiResponse<List<StorageResponse>> getStorage(Authentication authentication){
        User user = userService.getLoginUser(authentication.getName());
        List<Storage> storages = storageRepository.findAllByUserId(user.getId());
        return new ApiResponse<>(200, StorageResponse.from(storages));
    }

    //보관함 삭제하기
    @Transactional
    @DeleteMapping("/delete/{storageId}")
    @Operation(summary = "보관함 삭제하기")
    public ApiResponse<String> deleteStorage(Authentication authentication, @PathVariable Long storageId){
        User user = userService.getLoginUser(authentication.getName());
        storageService.deleteStorage(user, storageId);
        return new ApiResponse<>(204, null);
    }

    //전체 보관함 레시피 호출
    @GetMapping("/getAll")
    @Operation(summary = "전체 보관함 레시피 불러오기")
    public ApiResponse<List<RecipeImageResponse>> getAllUserRecipe(Authentication authentication){
        User user = userService.getLoginUser(authentication.getName());
        List<UserRecipe> userRecipes = userRecipeRepository.findAllByUserId(user.getId());
        LinkedList<RecipeImage> recipeImages = recipeImageService.getRecipeImage(userRecipes);
        return new ApiResponse<>(200, RecipeImageResponse.from(userRecipes, recipeImages));
    }

    //보관함에 있는 레시피 호출
    @GetMapping("/get/{storageId}")
    @Operation(summary = "특정 보관함 레시피 불러오기")
    public ApiResponse<List<RecipeImageResponse>> getUserRecipe(Authentication authentication, @PathVariable Long storageId){
        User user = userService.getLoginUser(authentication.getName());
        List<UserRecipe> userRecipes = userRecipeRepository.findAllByUserIdAndStorageId(user.getId(), storageId);
        LinkedList<RecipeImage> recipeImages = recipeImageService.getRecipeImage(userRecipes);
        return new ApiResponse<>(200, RecipeImageResponse.from(userRecipes, recipeImages));
    }
}
