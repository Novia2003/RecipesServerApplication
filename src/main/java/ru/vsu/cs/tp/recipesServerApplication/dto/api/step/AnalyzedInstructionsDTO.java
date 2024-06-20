package ru.vsu.cs.tp.recipesServerApplication.dto.api.step;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalyzedInstructionsDTO {
    private List<StepDTO> steps;
}
