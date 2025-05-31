package Mefo.server.domain.recipe.controller;

import Mefo.server.domain.recipe.dto.RecipeResponse;
import Mefo.server.domain.recipe.service.RecipeService;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name="레시피")
public class RecipeController {
    private final UserService userService;
    private final RecipeService recipeService;
    //클릭한 recipe 상세 내용 출력하기
    @GetMapping("/{recipeId}")
    @Operation(summary = "레시피 상세 내용 불러오기")
    public ApiResponse<RecipeResponse> getRecipe(Authentication authentication, @PathVariable Long recipeId){
        User user = userService.getLoginUser(authentication.getName());
        RecipeResponse recipeResponse = recipeService.getRecipe(user, recipeId);
        return new ApiResponse<>(200, recipeResponse);
    }

}
