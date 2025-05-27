package Mefo.server.domain.storage.entity;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.userRecipe.entity.UserRecipe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Storage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    private String storageName;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRecipe> userRecipes;

    public Storage(User user, String storageName){
        this.user = user;
        this.storageName = storageName;
    }

    public void patchStorageName(String storageName) {
        this.storageName = storageName;
    }
}
