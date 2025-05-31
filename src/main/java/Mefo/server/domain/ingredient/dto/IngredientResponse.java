package Mefo.server.domain.ingredient.dto;

import Mefo.server.domain.ingredient.entity.Ingredient;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class IngredientResponse {
    private String ingredientName;
    @Column(length = 50)
    private String capacity;
    public static List<IngredientResponse> from(List<Ingredient> ingredients){
        List<IngredientResponse> ingredientResponses = new ArrayList<>();
        for(Ingredient ingredient : ingredients){
            IngredientResponse ingredientResponse = IngredientResponse.builder()
                    .ingredientName(ingredient.getIngredientName())
                    .capacity(ingredient.getCapacity())
                    .build();
            ingredientResponses.add(ingredientResponse);
        }
        return ingredientResponses;
    }

}
