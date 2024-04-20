package ru.vsu.cs.tp.recipesServerApplication.service;

import com.spoonacular.IngredientsApi;
import com.spoonacular.client.ApiException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.integration.http.SpoonacularClient;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SpoonacularService {

    private final IngredientsApi ingredientsApi;

    public SpoonacularService(SpoonacularClient client) {
        this.ingredientsApi = new IngredientsApi(client.getApiClient());
    }

    public String getIngredientPossibleUnits(int id) {
        BigDecimal amount = new BigDecimal("1.0"); // BigDecimal | The amount of this ingredient.
        String unit = ""; // String | The unit for the given amount.


        StringBuffer buffer = new StringBuffer();

        try {
            List<String> units = ingredientsApi.getIngredientInformation(id, amount, unit).getPossibleUnits();

            for (int i = 0; i < units.size() - 1; i++)
                buffer.append(units.get(i)).append(", ");

            if (!units.isEmpty())
                buffer.append(units.get(units.size() - 1));
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#analyzeRecipe");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        } catch (IllegalArgumentException exception) {
            String response = exception.getMessage();
            String json = response.substring(response.indexOf("{"));

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray possibleUnits = jsonObject.getJSONArray("possibleUnits");

                for (int i = 0; i < possibleUnits.length() - 1; i++)
                    buffer.append(possibleUnits.getString(i)).append(", ");

                if (!possibleUnits.isEmpty())
                    buffer.append(possibleUnits.getString(possibleUnits.length() - 1));
            } catch (JSONException e) {
                throw new JSONException(e);
            }
        }

        return buffer.toString();
    }
}
