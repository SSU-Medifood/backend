package Mefo.server.domain.userRecipe.entity;

import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.recipe.entity.Recipe;
import Mefo.server.domain.storage.entity.Storage;
import Mefo.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRecipe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storageId")
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    public UserRecipe(User user, Recipe recipe){
        this.storage = null;
        this.user = user;
        this.recipe = recipe;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
