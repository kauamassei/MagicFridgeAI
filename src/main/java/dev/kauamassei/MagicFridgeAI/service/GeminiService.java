package dev.kauamassei.MagicFridgeAI.service;

import dev.kauamassei.MagicFridgeAI.model.FoodItemModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    private final WebClient webClient;

    private final String apiKey = System.getenv("GEMINI_API_KEY");

    public GeminiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItemModel> foodItems) {

        String alimentos = foodItems.stream()
                .map(item -> String.format("%s (%s) - Quantidade: %d, Validade: %s",
                        item.getNome(), item.getCategoria(), item.getQuantidade(), item.getValidade()))
                .collect(Collectors.joining("\n"));

        String prompt = "Faça uma receita com os seguintes itens: " + alimentos;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-goog-api-key", apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {

                    List<Map<String, Object>> candidates =
                            (List<Map<String, Object>>) response.get("candidates");

                    if (candidates != null && !candidates.isEmpty()) {

                        Map<String, Object> content =
                                (Map<String, Object>) candidates.get(0).get("content");

                        List<Map<String, Object>> parts =
                                (List<Map<String, Object>>) content.get("parts");

                        if (parts != null && !parts.isEmpty()) {
                            return parts.get(0).get("text").toString();
                        }
                    }

                    return "Nenhuma receita foi gerada.";
                });
    }
}

