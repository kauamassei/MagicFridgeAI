package repository;

import model.FoodItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItemModel, Long> {
}
