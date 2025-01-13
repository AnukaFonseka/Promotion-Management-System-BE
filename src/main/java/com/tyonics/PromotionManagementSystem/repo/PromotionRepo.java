package com.tyonics.PromotionManagementSystem.repo;

import com.tyonics.PromotionManagementSystem.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepo extends JpaRepository<Promotion, Integer> {
}
