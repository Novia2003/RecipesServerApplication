package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.model.Recipe;
import ru.vsu.cs.tp.recipesServerApplication.model.RecipeType;
import ru.vsu.cs.tp.recipesServerApplication.repository.FavoriteRecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.RecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FavoriteRecipeService {

    private final FavoriteRecipeRepository favoriteRecipeRepository;

    private final JwtService jwtService;

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    public Boolean isRecipeFavourite(String jwt, Long recipe_id, RecipeType type) {
        if (jwt == null)
            return false;

        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return false;

        Recipe recipe = recipeRepository.findByRecipeIdAndRecipeType(recipe_id, type);

        if (recipe == null)
            return false;

        return favoriteRecipeRepository.existsByUserIdAndRecipeId(user.get().getId(), recipe.getId());
    }
}
