package dev.kauamassei.MagicFridgeAI.repository;

import dev.kauamassei.MagicFridgeAI.model.FoodItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItemModel, Long> {
}
