package Mefo.server.domain.recipe.entity;

import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.recipeImage.entity.RecipeImage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String menu;
    private String calories;
    private Long amount;
//    private List<String> cookingOrder = new ArrayList<>();
    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private RecipeImage recipeImage;

//    @PostLoad  // 또는 @PrePersist
//    private void initCookingOrder() {
//        if (this.cookingOrder == null || this.cookingOrder.isEmpty()) {
//            this.cookingOrder.add("1.재료 썰기");
//            this.cookingOrder.add("2.끓이기");
//        }
//    }
}
