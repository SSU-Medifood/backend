package Mefo.server.domain.userRecipe.dto;

import Mefo.server.domain.recipeImage.entity.RecipeImage;
import Mefo.server.domain.recipeImage.repository.RecipeImageRepository;
import Mefo.server.domain.userRecipe.entity.UserRecipe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class UserRecipeResponse {
    private Long recipeId;
    private String name;
    @Schema(example = "https://example.com/image.jpg")
    private String image;

    public static List<UserRecipeResponse> from(List<UserRecipe> userRecipes, LinkedList<RecipeImage> recipeImages){
        List<UserRecipeResponse> userRecipeResponses = new ArrayList<>();
        RecipeImageRepository recipeImageRepository;
        for(UserRecipe userRecipe : userRecipes){
            RecipeImage recipeImage = recipeImages.removeFirst();
            UserRecipeResponse userRecipeResponse = UserRecipeResponse.builder()
                    .recipeId(recipeImage.getId())
                    .name(userRecipe.getRecipe().getMenu())
                    .image(recipeImage.getImage())
                    .build();
            userRecipeResponses.add(userRecipeResponse);
        }
        return userRecipeResponses;
    }
}
