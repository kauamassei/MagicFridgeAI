package dev.kauamassei.MagicFridgeAI.controller;

import dev.kauamassei.MagicFridgeAI.model.FoodItemModel;
import dev.kauamassei.MagicFridgeAI.service.FoodItemService;
import dev.kauamassei.MagicFridgeAI.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {

    private FoodItemService service;
    private GeminiService geminiService;

    public RecipeController(FoodItemService service, GeminiService geminiService) {
        this.service = service;
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe() {
        List<FoodItemModel> foodItems = service.listar();
        return geminiService.generateRecipe(foodItems)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
