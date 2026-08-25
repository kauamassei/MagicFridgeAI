package dev.kauamassei.MagicFridgeAI.controller;

import dev.kauamassei.MagicFridgeAI.model.FoodItemModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.kauamassei.MagicFridgeAI.service.FoodItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    FoodItemService service;

    public FoodItemController(FoodItemService service) {
        this.service = service;
    }

    // GET
    @GetMapping("/listar")
    public ResponseEntity<List<FoodItemModel>> listar() {
        List<FoodItemModel> foods = service.listar();
        return ResponseEntity.ok(foods);
    }
    // POST
    @PostMapping("/criar")
    public ResponseEntity<FoodItemModel> criar(@RequestBody FoodItemModel foodItem) {
        FoodItemModel salvo = service.salvar(foodItem);
        return ResponseEntity.ok(salvo);
    }

    // GET por ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<Optional<FoodItemModel>> buscarPorId(@PathVariable Long id) {
        Optional<FoodItemModel> foodById = service.buscarPorId(id);
        return ResponseEntity.ok(foodById);
    }
    // UPDATE
    @PutMapping("/atualizar/{id}")
    public FoodItemModel atualizar(@PathVariable Long id, @RequestBody FoodItemModel foodItem) {
        return service.alterarPorId(id, foodItem);
    }

    // DELETE
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        Optional<FoodItemModel> foodDeletado = service.buscarPorId(id);
        if (foodDeletado != null) {
            service.deletar(id);
            return ResponseEntity.ok("Comida deletada com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Comida não encontrada");
        }
    }
}
