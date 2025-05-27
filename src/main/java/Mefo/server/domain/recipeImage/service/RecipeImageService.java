package Mefo.server.domain.recipeImage.service;

import Mefo.server.domain.recipeImage.entity.RecipeImage;
import Mefo.server.domain.recipeImage.repository.RecipeImageRepository;
import Mefo.server.domain.userRecipe.entity.UserRecipe;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeImageService {
    private final RecipeImageRepository recipeImageRepository;

    //userRecipe 받아서 이미지 리스트 뽑아 주기
    public LinkedList<RecipeImage> getRecipeImage(List<UserRecipe> userRecipes){
        LinkedList<RecipeImage> recipeImages = new LinkedList<>();
        for(UserRecipe userRecipe : userRecipes){
            RecipeImage recipeImage = recipeImageRepository.findByRecipeId(userRecipe.getRecipe().getId())
                    .orElseThrow(()->new BusinessException(ErrorCode.IMAGE_DOESNT_EXIST));
            recipeImages.add(recipeImage);
        }
        return recipeImages;
    }
}
