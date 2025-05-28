package Mefo.server.domain.userRecipe.dto;

import Mefo.server.domain.userRecipe.entity.UserRecipe;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRecipeResponse {
    private Long userId;
    private Long storageId;
    private Long recipeId;

    public static UserRecipeResponse from(UserRecipe userRecipe){
        return UserRecipeResponse.builder()
                .userId(userRecipe.getUser().getId())
                .storageId(userRecipe.getStorage().getId())
                .recipeId(userRecipe.getRecipe().getId())
                .build();
    }
}
