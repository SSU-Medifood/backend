package Mefo.server.domain.recipeImage.repository;

import Mefo.server.domain.recipeImage.entity.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {
    Optional<RecipeImage> findByRecipeId(Long id);
}
