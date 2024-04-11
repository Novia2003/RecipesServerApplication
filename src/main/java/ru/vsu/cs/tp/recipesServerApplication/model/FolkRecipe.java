package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "folk_recipes")
public class FolkRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false)
    private String name;

    @Basic
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User authorById;

    @ManyToOne
    @JoinColumn(name = "meal_type_id", nullable = false)
    private MealType mealTypeById;

    @Basic
    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;

    @Basic
    @Column(name = "is_reviewed_by_admin", nullable = false)
    private Boolean isReviewedByAdmin;

    @Basic
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @OneToMany(mappedBy = "folkRecipeById")
    @ToString.Exclude
    private Collection<Step> stepsByFolkRecipeId;

    @OneToMany(mappedBy = "folkRecipeById")
    @ToString.Exclude
    private Collection<RecipeIngredientMeasurement> recipeIngredientMeasurementsByFolkRecipeId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        FolkRecipe that = (FolkRecipe) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
