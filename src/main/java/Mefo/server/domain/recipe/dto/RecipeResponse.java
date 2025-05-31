package Mefo.server.domain.recipe.dto;

import Mefo.server.domain.ingredient.dto.IngredientResponse;
import Mefo.server.domain.ingredient.entity.Ingredient;
import Mefo.server.domain.instruction.dto.InstructionResponse;
import Mefo.server.domain.instruction.entity.Instruction;
import Mefo.server.domain.recipe.entity.Recipe;
import Mefo.server.domain.recipeImage.entity.RecipeImage;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeResponse {
    private Long recipeId;
    private boolean like;
    private String menu;
    @Column(length = 50)
    private String foodType;
    private int amount;
    private int calories;
    private int carbohydrate;
    private int protein;
    private int fat;
    private int sodium;
    private String imageLarge;
    private List<IngredientResponse> ingredientResponses;
    private List<InstructionResponse> instructionResponses;

    public static RecipeResponse from(Recipe recipe, boolean like, String recipeImage, List<Ingredient> ingredients, List<Instruction> instructions){
        return RecipeResponse.builder()
                .recipeId(recipe.getId())
                .like(like)
                .menu(recipe.getMenu())
                .foodType(recipe.getFoodType())
                .amount(recipe.getAmount())
                .calories(recipe.getCalories())
                .carbohydrate(recipe.getCarbohydrate())
                .protein(recipe.getProtein())
                .fat(recipe.getFat())
                .sodium(recipe.getSodium())
                .imageLarge(recipeImage)
                .ingredientResponses(IngredientResponse.from(ingredients))
                .instructionResponses(InstructionResponse.from(instructions))
                .build();
    }
}
