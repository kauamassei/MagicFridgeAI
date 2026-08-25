package dev.kauamassei.MagicFridgeAI.service;

import dev.kauamassei.MagicFridgeAI.model.FoodItemModel;
import org.springframework.stereotype.Service;
import dev.kauamassei.MagicFridgeAI.repository.FoodItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    //cria
    public FoodItemModel salvar(FoodItemModel foodItemModel) {
        return repository.save(foodItemModel);
    }

    //lista
    public List<FoodItemModel> listar() {
        return repository.findAll();
    }

    //busca id
    public Optional<FoodItemModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    //altera id
    public FoodItemModel alterarPorId(Long id, FoodItemModel foodItem) {

        if (repository.existsById(id)) {
            foodItem.setId(id);
            return repository.save(foodItem);
        }
        return null;
    }

    //delete
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
