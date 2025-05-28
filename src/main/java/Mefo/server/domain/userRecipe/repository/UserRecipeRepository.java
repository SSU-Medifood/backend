package Mefo.server.domain.userRecipe.repository;

import Mefo.server.domain.userRecipe.entity.UserRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRecipeRepository extends JpaRepository<UserRecipe, Long> {
    List<UserRecipe> findAllByUserId(Long id);
    List<UserRecipe> findAllByUserIdAndStorageId(Long userId, Long storageId);
    Optional<UserRecipe> findByUserIdAndRecipeId(Long userId, Long recipeId);
}
