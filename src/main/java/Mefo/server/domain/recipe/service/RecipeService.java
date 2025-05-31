package Mefo.server.domain.recipe.service;

import Mefo.server.domain.ingredient.entity.Ingredient;
import Mefo.server.domain.ingredient.repository.IngredientRepository;
import Mefo.server.domain.instruction.entity.Instruction;
import Mefo.server.domain.instruction.repository.InstructionRepository;
import Mefo.server.domain.recipe.dto.RecipeResponse;
import Mefo.server.domain.recipe.entity.Recipe;
import Mefo.server.domain.recipe.repository.RecipeRepository;
import Mefo.server.domain.recipeImage.entity.RecipeImage;
import Mefo.server.domain.recipeImage.repository.RecipeImageRepository;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.userRecipe.repository.UserRecipeRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeImageRepository recipeImageRepository;
    private final UserRecipeRepository userRecipeRepository;
    private final IngredientRepository ingredientRepository;
    private final InstructionRepository instructionRepository;

    //레시피 상세 내용 불러오기
    public RecipeResponse getRecipe(User user, Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.RECIPE_DOESNT_EXIST));
        RecipeImage recipeImage = recipeImageRepository.findByRecipeId(recipeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.IMAGE_DOESNT_EXIST));
        List<Ingredient> ingredients = ingredientRepository.findAllByRecipeId(recipeId);
        List<Instruction> instructions = instructionRepository.findAllByRecipeId(recipeId);
        RecipeResponse recipeResponse;
        if(userRecipeRepository.findByUserIdAndRecipeId(user.getId(), recipeId).isPresent()){
            recipeResponse = RecipeResponse.from(recipe, true, recipeImage.getImageLarge(), ingredients, instructions);
        }
        else{
            recipeResponse = RecipeResponse.from(recipe, false, recipeImage.getImageLarge(), ingredients, instructions);
        }
        return recipeResponse;
    }
}
