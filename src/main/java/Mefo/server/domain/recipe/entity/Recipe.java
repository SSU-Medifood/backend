package Mefo.server.domain.recipe.entity;

import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.ingredient.entity.Ingredient;
import Mefo.server.domain.instruction.entity.Instruction;
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
    @Column(length = 50)
    private String foodType;
    @Column(length = 50)
    private String cookingType;
    private int amount;
    private int calories;
    private int carbohydrate;
    private int protein;
    private int fat;
    private int sodium;
    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private RecipeImage recipeImage;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instruction> instructions;

}
