package house.learning.cleanproject.inventoryservice.service;

import house.learning.cleanproject.infrastructures.entities.FoodMenu;
import house.learning.cleanproject.inventoryservice.repositories.FoodMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodMenuService {
    @Autowired
    private FoodMenuRepository repository;

//    public List<FoodMenu> findAll() {
//        return repository.findAll();
//    }
//
//    public FoodMenu save(FoodMenu foodMenu) {
//        return repository.save(foodMenu);
//    }

}
