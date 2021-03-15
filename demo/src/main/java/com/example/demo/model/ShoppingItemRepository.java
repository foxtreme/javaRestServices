package com.example.demo.model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    
}
