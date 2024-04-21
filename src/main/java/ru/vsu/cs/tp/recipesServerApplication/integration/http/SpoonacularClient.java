package ru.vsu.cs.tp.recipesServerApplication.integration.http;

import com.spoonacular.client.ApiClient;
import com.spoonacular.client.Configuration;
import com.spoonacular.client.auth.ApiKeyAuth;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.vsu.cs.tp.recipesServerApplication.configuration.rest.SpoonacularProperties;

@Component
@Data
public class SpoonacularClient {

    private final ApiClient apiClient;

    public SpoonacularClient(SpoonacularProperties properties) {
        this.apiClient = Configuration.getDefaultApiClient();
        this.apiClient.setBasePath(properties.getUrl());

        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) this.apiClient.getAuthentication(properties.getScheme());
        apiKeyAuth.setApiKey(properties.getApiKey());
    }
}
