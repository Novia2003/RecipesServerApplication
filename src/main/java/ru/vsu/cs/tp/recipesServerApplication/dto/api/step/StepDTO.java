package ru.vsu.cs.tp.recipesServerApplication.dto.api.step;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepDTO {
    private String step;
}
