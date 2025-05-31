package Mefo.server.domain.userRecipe.controller;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.domain.userRecipe.dto.UserRecipeResponse;
import Mefo.server.domain.userRecipe.entity.UserRecipe;
import Mefo.server.domain.userRecipe.service.UserRecipeService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userRecipe")
@RequiredArgsConstructor
@Tag(name="찜 관리")
public class UserRecipeController {
    private final UserService userService;
    private final UserRecipeService userRecipeService;

    //전체 보관함에 레시피 찜하기
    @Transactional
    @PostMapping("/like/{recipeId}")
    @Operation(summary = "전체 보관함에 레시피 찜하기")
    public ApiResponse<UserRecipeResponse> createUserRecipe(Authentication authentication, @PathVariable Long recipeId){
        User user = userService.getLoginUser(authentication.getName());
        UserRecipe userRecipe = userRecipeService.createUserRecipe(user, recipeId);
        return new ApiResponse<>(201, UserRecipeResponse.from(userRecipe));
    }

    //특정 보관함에 레시피 찜하기
    @Transactional
    @PatchMapping("/like/{recipeId}/{storageId}")
    @Operation(summary = "특정 보관함에 레시피 찜하기")
    public ApiResponse<UserRecipeResponse> patchUserRecipe(Authentication authentication, @PathVariable Long recipeId, @PathVariable Long storageId){
        User user = userService.getLoginUser(authentication.getName());
        UserRecipe userRecipe = userRecipeService.patchUserRecipe(user, storageId, recipeId);
        return new ApiResponse<>(200, UserRecipeResponse.from(userRecipe));
    }


    //찜 취소하기
    @Transactional
    @DeleteMapping("/unlike/{recipeId}")
    @Operation(summary = "레시피 찜 취소하기")
    public ApiResponse<String> deleteUserRecipe(Authentication authentication, @PathVariable Long recipeId){
        User user = userService.getLoginUser(authentication.getName());
        userRecipeService.deleteUserRecipe(user, recipeId);
        return new ApiResponse<>(204, null);
    }
}
