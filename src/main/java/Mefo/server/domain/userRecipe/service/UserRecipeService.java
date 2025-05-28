package Mefo.server.domain.userRecipe.service;

import Mefo.server.domain.recipe.entity.Recipe;
import Mefo.server.domain.recipe.repository.RecipeRepository;
import Mefo.server.domain.storage.entity.Storage;
import Mefo.server.domain.storage.repository.StorageRepository;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.userRecipe.entity.UserRecipe;
import Mefo.server.domain.userRecipe.repository.UserRecipeRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserRecipeService {
    private final StorageRepository storageRepository;
    private final RecipeRepository recipeRepository;
    private final UserRecipeRepository userRecipeRepository;

    //전체 보관함에 레시피 찜하기
    @Transactional
    public UserRecipe createUserRecipe(User user, Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.RECIPE_DOESNT_EXIST));
        UserRecipe userRecipe = new UserRecipe(user, recipe);
        userRecipeRepository.save(userRecipe);
        return userRecipe;
    }

    //특정 보관함에 레시피 찜하기
    @Transactional
    public UserRecipe patchUserRecipe(User user, Long storageId, Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.RECIPE_DOESNT_EXIST));
        Storage storage = storageRepository.findByIdAndUserId(storageId, user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.STORAGE_DOESNT_EXIST));
        UserRecipe userRecipe = userRecipeRepository.findByUserIdAndRecipeId(user.getId(), recipeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_RECIPE_DOESNT_EXIST));
        userRecipe.setStorage(storage);
        storage.getUserRecipes().add(userRecipe);
        userRecipeRepository.save(userRecipe);
        return userRecipe;
    }


    //찜 취소하기
    @Transactional
    public void deleteUserRecipe(User user, Long recipeId){
        UserRecipe userRecipe = userRecipeRepository.findByUserIdAndRecipeId(user.getId(), recipeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_RECIPE_DOESNT_EXIST));
        if(userRecipe.getStorage() != null){
            userRecipe.getStorage().getUserRecipes().remove(userRecipe);
        }
        userRecipeRepository.delete(userRecipe);
    }
}
