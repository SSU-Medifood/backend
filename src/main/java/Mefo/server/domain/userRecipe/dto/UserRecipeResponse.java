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
        Long storageId;
        if(userRecipe.getStorage() == null){
            storageId = null;
        }
        else{
            storageId= userRecipe.getStorage().getId();
        }
        return UserRecipeResponse.builder()
                .userId(userRecipe.getUser().getId())
                .recipeId(userRecipe.getRecipe().getId())
                .storageId(storageId)
                .build();
    }
}
