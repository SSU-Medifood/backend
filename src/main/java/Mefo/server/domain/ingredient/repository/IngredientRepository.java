package Mefo.server.domain.ingredient.repository;

import Mefo.server.domain.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByRecipeId(Long recipeId);
}
